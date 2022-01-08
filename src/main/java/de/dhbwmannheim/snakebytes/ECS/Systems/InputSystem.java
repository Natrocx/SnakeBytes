package de.dhbwmannheim.snakebytes.ECS.Systems;

import de.dhbwmannheim.snakebytes.ECS.*;
import de.dhbwmannheim.snakebytes.ECS.Base.System;
import de.dhbwmannheim.snakebytes.ECS.Base.*;
import de.dhbwmannheim.snakebytes.ECS.util.ConversionUtils;
import de.dhbwmannheim.snakebytes.GUI.JsonHandler;
import de.dhbwmannheim.snakebytes.Sounds.SoundManager;
import javafx.scene.input.KeyEvent;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Hashtable;
import java.util.List;

import de.dhbwmannheim.snakebytes.ECS.BoundingBoxComponent;

import java.io.IOException;

/**
 * Authors: @Eric Stefan
 *         @Kirolis Eskondis
 * This class is handling the inputs of all players (respectively the key presses)
 * and pre-filters if an action should be performed and then performs it.
 * Important: The collision management (e.g. if a player can walk) is not part of this System,
 * but the position still gets updated.
 */

public class InputSystem extends System {

    static Hashtable<String, String> player1KeySettings;
    static Hashtable<String, String> player2KeySettings;

    //Saving the KeySettings of player 1 into a HashTable, so that: <keyboard key as String, connected action as String>
    static {
        try {
            player1KeySettings = new Hashtable<>(JsonHandler.fromJson("player1", JsonHandler.KeyOfHashMap.KEYBOARD_KEY));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
    //Saving the KeySettings of player 2 into a HashTable, so that: <keyboard key as String, connected action as String>
    static {
        try {
            player2KeySettings = new Hashtable<>(JsonHandler.fromJson("player2", JsonHandler.KeyOfHashMap.KEYBOARD_KEY));
        } catch (IOException | ParseException e) {
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

    //create attack entitys and register them in the Engine
    //attackType parameter: (0->normal attack, 1->special attack player1, 2->special attack player12)
    //direction parameter: (0 -> left, 1 -> right) as the standing direction of the player
    //to remember: the coordinate system begins from the bottom left, as well as each position in PositionComponent,
    //so that the start point of an attack entity when looking to the right have to be added by the hitbox width of the player
    private static void setupAttack(int attackType, int direction, Vec2<Double> playerPosition, Double boundingBoxX, Double boundingBoxY, Entity entity) {

        Vec2<Double> temp = new Vec2<>();
        Vec2<Double> spcTemp = new Vec2<>();
        int attackStateIndex = 0;

        var attack = new Entity();
        //if attack should go to the right the hitbox of the player should be added
        if (direction == 1) {
            temp.x = playerPosition.x + boundingBoxX + 0.01;
        }
        //add two third of the height of the player
        temp.y = playerPosition.y + (2.0 / 3.0 * boundingBoxY);
        spcTemp.y = playerPosition.y - 0.04;

        if (attackType == 0) {
            if (direction == 0) {
                temp.x = playerPosition.x - 0.01;
            }
            var attackPosition = new PositionComponent(new Vec2<>(temp.x, temp.y));
            //defining width and height of the attack hitbox
            var attackBoundingBox = new BoundingBoxComponent(new Vec2<>(0.001, 0.01), BoundingBoxComponent.BoxType.Attack);
            var attackState = new AttackStateComponent(direction, entity.id, 0.1);

            Engine.registerEntity(attack);
            ComponentManager.addComponent(attack, attackPosition);
            ComponentManager.addComponent(attack, attackBoundingBox);
            ComponentManager.addComponent(attack, attackState);

        } else if (attackType == 1) {
            double helpX = 0.0;
            if (direction == 0) {
                temp.x = playerPosition.x -  0.1;
                helpX = -0.2;
            } else if (direction == 1) {
                helpX = 0.2;
                attackStateIndex = 1;
            }
            var attackPosition = new PositionComponent(new Vec2<>(temp.x, spcTemp.y));
            //defining width and height of the attack hitbox
            var attackBoundingBox = new BoundingBoxComponent(new Vec2<>(0.1, 0.05), BoundingBoxComponent.BoxType.SpecialAttack);
            var attackMotion = new MotionComponent(new Vec2<>(helpX, 0.0),4);
            var attackState = new AttackStateComponent(attackStateIndex, entity.id);

            Engine.registerEntity(attack);
            ComponentManager.addComponent(attack, attackPosition);
            ComponentManager.addComponent(attack, attackBoundingBox);
            ComponentManager.addComponent(attack, attackMotion);
            ComponentManager.addComponent(attack, attackState);
            Engine.attackList.add(attack);

        } else if (attackType == 2) {
            double hilf;
            if (direction == 0) {
                temp.x = playerPosition.x - 0.075;
                hilf = -0.4;
                attackStateIndex = 2;
            } else {
                hilf = 0.4;
                attackStateIndex = 3;
            }
            var attackPosition = new PositionComponent(new Vec2<>(temp.x, spcTemp.y));
            //defining width and height of the attack hitbox
            var attackBoundingBox = new BoundingBoxComponent(new Vec2<>(0.075, 0.075), BoundingBoxComponent.BoxType.SpecialAttack);
            var attackMotion = new MotionComponent(new Vec2<>(hilf, 0.2), 4);
            var attackGravity = new GravityComponent(1.0);
            var attackState = new AttackStateComponent(attackStateIndex);

            Engine.registerEntity(attack);
            ComponentManager.addComponent(attack, attackPosition);
            ComponentManager.addComponent(attack, attackBoundingBox);
            ComponentManager.addComponent(attack, attackMotion);
            ComponentManager.addComponent(attack, attackGravity);
            ComponentManager.addComponent(attack, attackState);
            Engine.attackList.add(attack);
        }
    }

    //saving all pressed keys, since the last time the update() function were executed
    public void keyPressed(KeyEvent keyEvent) {
        String code = keyEvent.getCode().toString();
        pressedKeys.add(code);
    }

    //translate the key value "temp" for its player entity to an action
    public void translateKeyInput(String temp, Entity entity, int i) {

        MotionComponent motionComponent = motion.getComponent(entity);
        CharacterStateComponent characterStateComponent = characterState.getComponent(entity);
        BoundingBoxComponent boundingBoxComponent = boundingBox.getComponent(entity);
        PositionComponent positionComponent = position.getComponent(entity);

        //reduce attack and special attack cooldowns and reset hit-state
        if (characterStateComponent != null) {
            if (characterStateComponent.attackCooldown > 0) {
                double val = characterStateComponent.attackCooldown;
                val = val - 0.2;
                val = val * 100;
                val = Math.round(val);
                val = val / 100;
                characterStateComponent.attackCooldown = val;
            }
            if (characterStateComponent.specialAttackCooldown > 0) {
                double val = characterStateComponent.specialAttackCooldown;
                val = val - 0.2;
                val = val * 100;
                val = Math.round(val);
                val = val / 100;
                characterStateComponent.specialAttackCooldown = val;
            }
            characterStateComponent.hitState = false;
        }

        //(to remember: state values: 0=left, 1=right, 2=attackLeft, 3=attackRight, 4=specialAttackLeft, 5=specialAttackRight)
        if (!temp.equals("")) {
            assert characterStateComponent != null;
            characterStateComponent.specialAttacking = false;
            characterStateComponent.attacking = false;

            double width = boundingBoxComponent.size.x;
            double height = boundingBoxComponent.size.y;
            Vec2<Double> pos = positionComponent.value;

            //if the character attacked in a direction in his former move, he now just looks into this direction
            if (characterStateComponent.state == 2 || characterStateComponent.state == 4) {
                characterStateComponent.state = 0;
            }else if (characterStateComponent.state == 3 || characterStateComponent.state == 5) {
                characterStateComponent.state = 1;
            }

            //mapping the action which should be executed towards the pressed key and change component values where needed
            switch (temp) {
                case "right":
                    motionComponent.velocity.x = 0.05;
                    motionComponent.timeToDecay = motionComponent.maxTimeToDecay;
                    characterStateComponent.state = 1;
                    characterStateComponent.lookingDirection = 1;
                    break;
                case "left":
                    motionComponent.velocity.x = -0.05;
                    motionComponent.timeToDecay = motionComponent.maxTimeToDecay;
                    characterStateComponent.state = 0;
                    characterStateComponent.lookingDirection = 0;
                    break;
                case "jump":
                    //if no double jump is active
                    if (!multiJump(characterStateComponent.jumping)) {
                        motionComponent.velocity.y = 0.15; //jump
                        soundManager.playJumpSound();
                        //since the player jumps this has to be saved in the boolean Array jumping[]
                        if (!characterStateComponent.jumping[0]) {
                            characterStateComponent.jumping[0] = true;
                        } else {
                            characterStateComponent.jumping[1] = true;
                        }
                    }
                    break;
                case "attack":
                    //if there is no attack cooldown -> attack and set "attackCooldown"
                    if (characterStateComponent.attackCooldown == 0) {
                        characterStateComponent.attacking = true;
                        characterStateComponent.attackCooldown = 1.0;
                        motionComponent.velocity.x = 0.0;
                        soundManager.playPunchSound();
                        //Attack in direction the character is looking
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
                    //if there is no special attack cooldown -> attack and set "specialAttackCooldown"
                    if (characterStateComponent.specialAttackCooldown == 0) {
                        characterStateComponent.specialAttacking = true;
                        characterStateComponent.specialAttackCooldown = 5.0;
                        //if character looking to the left, setup attacks flying to the left depending on which character uses attack
                        if (characterStateComponent.lookingDirection == 0) {
                            if(entity == entities.get(0)) {
                                setupAttack(1, 0, pos, width, height, entity);
                            } else {
                                setupAttack(2, 0, pos, width, height, entity);
                            }
                            //spc attack looking left Image
                            characterStateComponent.state = 4;
                        } else {
                            //If character looking to the right
                            if (entity == entities.get(0)) {
                                setupAttack(1, 1, pos, width, height, entity);
                            } else {
                                setupAttack(2, 1, pos, width, height, entity);
                            }
                            //spc Attack looking right Image
                            characterStateComponent.state = 5;
                        }

                        //play the specific special attack sound of the player
                        if (i == 0) {
                            soundManager.playSpAttack1();
                        } else {
                            soundManager.playSpAttack2();
                        }
                        break;
                    }
            }
        }
    }

    @Override
    public void update(double deltaTime) throws Exception {

        //iterate over all pressed keys, that did not were processed (since pressedKeys only contains not processed keys)
        for (int j = 0; j < pressedKeys.size(); j++) {
            var key = pressedKeys.get(j);
            String temp;

            //if the key has an action for player1 or player2, this action gets saved into the String "temp"
            //and the related player entity of the pressed key (player1 entity (with index 0) or the player2 entity (with index 1))
            //is saved in Entity "entity" and "translateKeyInput()" is called to process the action related to the key-press
            if (player1KeySettings.containsKey(key)) {
                int i = 0;
                var entity = entities.get(i);
                temp = player1KeySettings.get(key);
                translateKeyInput(temp, entity, i);
            }
            if (player2KeySettings.containsKey(key)) {
                int i = 1;
                var entity = entities.get(i);
                temp = player2KeySettings.get(key);
                translateKeyInput(temp, entity, i);
            }
            //removes the key
            pressedKeys.remove(key);

        }

    }

    @Override
    public BitSet getSignature() {
        return signature;
    }

    //true is returned, when the player is currently multi-jumping (respectively double jumping)
    private boolean multiJump(boolean[] array) {
        return array[0] && array[1];
    }
}
