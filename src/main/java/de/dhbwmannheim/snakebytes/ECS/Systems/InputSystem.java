//code by Eric Stefan, Jonas Lauschke

package de.dhbwmannheim.snakebytes.ECS.Systems;

import de.dhbwmannheim.snakebytes.ECS.*;
import de.dhbwmannheim.snakebytes.ECS.Base.System;
import de.dhbwmannheim.snakebytes.ECS.Base.*;
import de.dhbwmannheim.snakebytes.ECS.util.ConversionUtils;
import de.dhbwmannheim.snakebytes.JsonHandler;
import de.dhbwmannheim.snakebytes.Sounds.SoundManager;
import javafx.scene.input.KeyEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Author: @Eric Stefan
 * This class is handling the inputs of all players (respectively the key presses)
 * and pre-filters if an action should be performed and then performs it.
 * Important: The collision management (e.g. if a player can walk) is not part of this System,
 * but the position still gets updated.
 */

public class InputSystem extends System {

    //Saving the KeySettings of player 1 into a HashTable, so that: <keyboard key as String, connected action as String>
    static Hashtable<String, String> player1KeySettings;
    //Saving the KeySettings of player 2 into a HashTable, so that: <keyboard key as String, connected action as String>
    static Hashtable<String, String> player2KeySettings;

    static {
        try {
            player1KeySettings = new Hashtable<>(JsonHandler.fromJson("player1", JsonHandler.KeyOfHashMap.KEYBOARD_KEY));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    static {
        try {
            player2KeySettings = new Hashtable<>(JsonHandler.fromJson("player2", JsonHandler.KeyOfHashMap.KEYBOARD_KEY));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    ComponentList<MotionComponent> motion;
    ComponentList<CharacterStateComponent> characterState;
    ComponentList<BoundingBoxComponent> boundingBox;
    ComponentList<PositionComponent> position;
    List<String> pressedKeys = new ArrayList<>();
    SoundManager soundManager = new SoundManager();

    public InputSystem() {
        signature = new BitSet();

        signature.set(ConversionUtils.indexFromID(CharacterStateComponent.id));
        signature.set(ConversionUtils.indexFromID(MotionComponent.id));
        signature.set(ConversionUtils.indexFromID(BoundingBoxComponent.id));
        signature.set(ConversionUtils.indexFromID(PositionComponent.id));

        motion = ComponentManager.getComponentList(MotionComponent.class);
        characterState = ComponentManager.getComponentList(CharacterStateComponent.class);
        boundingBox = ComponentManager.getComponentList(BoundingBoxComponent.class);
        position = ComponentManager.getComponentList(PositionComponent.class);


    }

    //create Attack Entitys
    //param attackType (0->normal, 1->special attack 1, 2->special attack 2)
    //to remember: the coordinate system begins from the bottom left, as well as each position in PositionComponent
    private static void setupAttack(int attackType, int direction, Vec2<Double> playerPosition, Double boundingBoxX, Double boundingBoxY, Entity entity) {

        Vec2<Double> temp = new Vec2<>();
        int attackStateIndex = 0;

        var attack = new Entity();
        //if attack should go to the right the hitbox of the player should be added
        if (direction == 1) {
            temp.x = playerPosition.x + boundingBoxX+0.01;
        }
        //add two third of the height of the player
        temp.y = playerPosition.y + (2.0 / 3.0 * boundingBoxY);

        if (attackType == 0) {
            if (direction == 0) {
                temp.x = playerPosition.x - 0.01;
            }
            var attackPosition = new PositionComponent(new Vec2<>(temp.x, temp.y));
            //defining width and height of the attack hitbox
            var attackBoundingBox = new BoundingBoxComponent(new Vec2<>(0.001, 0.01), BoundingBoxComponent.BoxType.Attack);
            var attackState = new AttackStateComponent(direction, entity.id, 0.25);

            Engine.registerEntity(attack);
            ComponentManager.addComponent(attack, attackPosition);
            ComponentManager.addComponent(attack, attackBoundingBox);
            ComponentManager.addComponent(attack, attackState);

        } else if (attackType == 1) {
            Double helpX = 0.0;
            if (direction == 0) {
                temp.x = playerPosition.x;
                helpX = -0.1;
                attackStateIndex = 0;
            } else if (direction == 1) {
                helpX = 0.1;
                attackStateIndex = 1;
            }
            //start position of motion
            var attackPosition = new PositionComponent(new Vec2<>(temp.x, temp.y));
            //defining width and height of the attack hitbox
            var attackBoundingBox = new BoundingBoxComponent(new Vec2<>(0.1, 0.01), BoundingBoxComponent.BoxType.Attack);
            var attackMotion = new MotionComponent(new Vec2<>(helpX, 0.0));
            var attackState = new AttackStateComponent(attackStateIndex, entity.id);

            Engine.registerEntity(attack);
            ComponentManager.addComponent(attack, attackPosition);
            ComponentManager.addComponent(attack, attackBoundingBox);
            ComponentManager.addComponent(attack, attackMotion);
            ComponentManager.addComponent(attack, attackState);

        } else if (attackType == 2) {
            Double hilf = 0.0;
            if (direction == 0) {
                temp.x = playerPosition.x-0.03;
                hilf = -0.4;
                attackStateIndex = 2;
            } else {
                temp.x = playerPosition.x+0.03;
                hilf = 0.4;
                attackStateIndex = 3;
            }
            //start position of motion
            var attackPosition = new PositionComponent(new Vec2<>(temp.x, temp.y));
            //defining width and height of the attack hitbox
            var attackBoundingBox = new BoundingBoxComponent(new Vec2<>(0.1, 0.1), BoundingBoxComponent.BoxType.Attack);

            var attackMotion = new MotionComponent(new Vec2<>(hilf, 0.2));
            var attackGravity = new GravityComponent(1.0);
            var attackState = new AttackStateComponent(attackStateIndex);

            Engine.registerEntity(attack);
            ComponentManager.addComponent(attack, attackPosition);
            ComponentManager.addComponent(attack, attackBoundingBox);
            ComponentManager.addComponent(attack, attackMotion);
            ComponentManager.addComponent(attack, attackGravity);
            ComponentManager.addComponent(attack, attackState);
        }
    }



    //saving all recent key presses since the last time the following update() function were executed
    public void keyPressed(KeyEvent keyEvent) {
        String code = keyEvent.getCode().toString();
        pressedKeys.add(code);
    }

    public void translateKeyInput(String temp, Entity entity, double deltaTime, int i) throws Exception {

        MotionComponent motionComponent = motion.getComponent(entity);
        CharacterStateComponent characterStateComponent = characterState.getComponent(entity);
        BoundingBoxComponent boundingBoxComponent = boundingBox.getComponent(entity);
        PositionComponent positionComponent = position.getComponent(entity);

        //reduce attack cooldowns
        if (characterStateComponent != null) {
            if(characterStateComponent.attackCooldown > 0){
                double val = characterStateComponent.attackCooldown;
                val = val - 0.2;
                val = val *100;
                val = Math.round(val);
                val = val / 100;
                characterStateComponent.attackCooldown = val;
            }
            if(characterStateComponent.specialAttackCooldown > 0){
                double val = characterStateComponent.specialAttackCooldown;
                val = val - 0.2;
                val = val *100;
                val = Math.round(val);
                val = val / 100;
                characterStateComponent.specialAttackCooldown = val;
            }


            if(characterStateComponent.state == 2 || characterStateComponent.state == 4){
                characterStateComponent.state = 0;
            }
            if(characterStateComponent.state == 3 || characterStateComponent.state == 5){
                characterStateComponent.state = 1;
            }
        }

        if (!temp.equals("")) {
            Double width = boundingBoxComponent.size.x;
            Double height = boundingBoxComponent.size.y;
            Vec2<Double> pos = positionComponent.value;
            //mapping the action which should be executed towards the pressed key
            switch (temp) {
                case "right":
                    motionComponent.velocity.x = 0.05;
                    characterStateComponent.state = 1;
                    characterStateComponent.lookingDirection = 1;
                    characterStateComponent.specialAttacking = false;
                    characterStateComponent.attacking = false;
                    break;
                case "left":
                    motionComponent.velocity.x = -0.05;
                    characterStateComponent.state = 0;
                    characterStateComponent.lookingDirection = 0;
                    characterStateComponent.specialAttacking = false;
                    characterStateComponent.attacking = false;
                    break;
                case "jump":
                    //if no double jump is active
                    if (multiJump(characterStateComponent.jumping) == false) {
                        motionComponent.velocity.y = 0.15; //jump
                        characterStateComponent.specialAttacking = false;
                        characterStateComponent.attacking = false;
                        soundManager.playJumpSound();
                        //since the player jumps this has to be saved in the boolean Array jumping[]
                        if (characterStateComponent.jumping[0] != true) {
                            characterStateComponent.jumping[0] = true;
                        } else {
                            characterStateComponent.jumping[1] = true;
                        }
                    }
                    break;
                case "attack":
                    //if there is no attack cooldown -> attack and set attackCooldown
                    if (characterStateComponent.attackCooldown == 0) {
                        characterStateComponent.attacking = true;
                        characterStateComponent.attackCooldown = 1.0;
                        motionComponent.velocity.x = 0.0;
                        soundManager.playPunchSound();
                        if (characterStateComponent.lookingDirection == 0) {
                            setupAttack(0, 0, pos, width, height, entity);
                            characterStateComponent.state = 2;
                        } else {
                            setupAttack(0, 1, pos, width, height, entity);
                            characterStateComponent.state = 3;
                        }
                    }
                    break;
                case "specialAttack":
                    //if there is no special attack cooldown -> attack and set attackCooldown
                    if (characterStateComponent.specialAttackCooldown == 0) {
                        characterStateComponent.specialAttacking = true;
                        characterStateComponent.specialAttackCooldown = 2.0;
                        if (characterStateComponent.lookingDirection == 0) {
                            setupAttack(1, 0, pos, width, height, entity);
                            characterStateComponent.state = 4;
                        } else {
                            setupAttack(1, 1, pos, width, height, entity);
                            characterStateComponent.state = 5;
                        }

                        if (i == 0) {
                            soundManager.playSpAttack1();
                        } else {
                            soundManager.playSpAttack2();
                        }
                        break;
/*
                        //for each player play the specific sound of the special attack
                        //and set the specific specialAttackCooldown
                        if (player1KeySettings.containsKey(key)) {
                            characterStateComponent.specialAttackCooldown = 2.0;
                            if (characterStateComponent.lookingDirection == 0) {
                                setupAttack(1, 0, pos, width, height);
                                characterStateComponent.state = 4;
                            } else {
                                setupAttack(1, 1, pos, width, height);
                                characterStateComponent.state = 5;
                            }
                            //soundManager.playSpAttack1();
                        } else {
                            characterStateComponent.specialAttackCooldown = 2.5;
                            if (characterStateComponent.lookingDirection == 0) {
                                setupAttack(2, 0, pos, width, height);
                                characterStateComponent.state = 4;
                            } else {
                                setupAttack(2, 1, pos, width, height);
                                characterStateComponent.state = 5;
                            }
                            //soundManager.playSpAttack2();
                        }
                    }
                    break;*/
                    }
            }
        }
    }

    @Override
    public void update(double deltaTime) throws Exception {

            //iterate over all pressed keys, that did not were processed (since pressedKeys only contains not processed keys)
            for (int j = 0; j < pressedKeys.size(); j++) {
                var key = pressedKeys.get(j);

                String temp = "";
                //if the key has an action for player1 or player2, this action gets saved into String "temp"
                if (player1KeySettings.containsKey(key)){
                    int i = 0;
                    var entity = entities.get(i);
                    temp = player1KeySettings.get(key);
                    translateKeyInput(temp,entity, deltaTime, i);
                }

                if (player2KeySettings.containsKey(key)){
                    int i = 1;
                    var entity = entities.get(i);
                    temp = player2KeySettings.get(key);
                    translateKeyInput(temp,entity, deltaTime, i);

                //if an action should be executed

                    }
                //removes the key
                pressedKeys.remove(key);

            }

    }

    @Override
    public BitSet getSignature() {
        return signature;
    }

    //if true is returned the player is currently multi-jumping (respectively double jumping)
    private boolean multiJump(boolean[] array) {
        if (array[0] && array[1]) {
            return true;
        }
        return false;
    }
}
