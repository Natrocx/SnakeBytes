//code by Eric Stefan, Jonas Lauschke

package de.dhbwmannheim.snakebytes.ECS.Systems;

import de.dhbwmannheim.snakebytes.ECS.Base.*;
import de.dhbwmannheim.snakebytes.ECS.Base.System;
import de.dhbwmannheim.snakebytes.ECS.CharacterStateComponent;
import de.dhbwmannheim.snakebytes.ECS.MotionComponent;
import de.dhbwmannheim.snakebytes.ECS.util.ConversionUtils;
import de.dhbwmannheim.snakebytes.Sounds.SoundManager;
import javafx.scene.input.KeyEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Hashtable;
import java.util.List;

/**
 * Author: @Eric Stefan
 * This class is handling the inputs of all players (respectively the key presses)
 * and pre-filters if an action should be performed and then performs it.
 * Important: The collision management (e.g. if a player can walk) is not part of this System,
 * but the positionComponent still gets updated.
 */

public class InputSystem extends System {

    //Saving the KeySettings of player 1 into a HashTable, so that: <keyboard key as String, connected action as String>
    static Hashtable<String, String> player1KeySettings;
    //Saving the KeySettings of player 2 into a HashTable, so that: <keyboard key as String, connected action as String>
    static Hashtable<String, String> player2KeySettings;

    static {
        try {
            player1KeySettings = new Hashtable<>(getKeySettings("player1"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    static {
        try {
            player2KeySettings = new Hashtable<>(getKeySettings("player2"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    ComponentList<MotionComponent> motion;
    ComponentList<CharacterStateComponent> characterState;
    List<Entity> entities;
    ArrayList<String> pressedKeys;
    SoundManager soundManager;

    //function to read the JSON-file which saves the key settings of player1 and player2
    private static Hashtable<String, String> getKeySettings(String player) throws IOException, ParseException {
        Hashtable<String, String> playerKeyValues = new Hashtable<>();

        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader("src/main/java/de/dhbwmannheim/snakebytes/ECS/Systems/keySettings.json");
        Object obj = jsonParser.parse(reader);
        JSONArray arr = (JSONArray) obj;

        int help;
        if (player.equals("player1")) {
            help = 0;
        } else {
            help = 1;
        }
        JSONObject obj2 = (JSONObject) ((JSONObject) arr.get(help)).get(player);
        for (int i = 0; i < obj2.size(); i++) {
            String temp = obj2.keySet().stream().toList().get(i).toString();
            //key= keyboard key; and value= action to execute
            playerKeyValues.put(obj2.get(temp).toString(), temp);
        }

        return playerKeyValues;
    }

    //saving all recent key presses since the last time the following update() function were executed
    public void keyPressed(KeyEvent keyEvent) {
        String code = keyEvent.getCode().toString();
        pressedKeys.add(code);
    }

    @Override
    public void update(double deltaTime) throws Exception {

        for (Entity entity : entities) {

            MotionComponent motionComponent = motion.getComponent(entity);
            CharacterStateComponent characterStateComponent = characterState.getComponent(entity);

            //reduce attack cooldowns
            characterStateComponent.attackCooldown = characterStateComponent.attackCooldown - deltaTime;
            characterStateComponent.specialAttackCooldown = characterStateComponent.specialAttackCooldown - deltaTime;

            //iterate over all pressed keys, that did not were processed (since pressedKeys only contains not processed keys)
            for (String key : pressedKeys) {

                String temp = "";
                //if the key has an action for player1 or player2, this action gets saved into String "temp"
                if (player1KeySettings.containsKey(key))
                    temp = player1KeySettings.get(key);
                if (player2KeySettings.containsKey(key))
                    temp = player2KeySettings.get(key);

                //if an action should be executed
                if (!temp.equals("")) {
                    //mapping the action which should be executed towards the pressed key
                    switch (temp) {
                        case "right":
                            motionComponent.velocity.x = 0.005;
                            break;
                        case "left":
                            motionComponent.velocity.x = -0.005;
                            break;
                        case "jump":
                            //if no double jump is active
                            if (multiJump(characterStateComponent.jumping) == false) {
                                motionComponent.velocity.y = 0.01; //jump
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
                                soundManager.playPunchSound();
                            }
                            break;
                        case "specialAttack":
                            //if ther is no special attack cooldown -> attack and set attackCooldown
                            if (characterStateComponent.specialAttackCooldown == 0) {
                                characterStateComponent.specialAttacking = true;

                                //for each player play the specific sound of the special attack
                                //and set the specific specialAttackCooldown
                                if (player1KeySettings.containsKey(key)) {
                                    characterStateComponent.specialAttackCooldown = 2.0;
                                    soundManager.playSpAttack1();
                                } else {
                                    characterStateComponent.specialAttackCooldown = 2.5;
                                    soundManager.playSpAttack2();
                                }
                            }
                            break;
                    }
                }
                //removes the key
                pressedKeys.remove(key);

            }

            //Prüfung auf Knockback ist denke ich nicht notwendig, denn man kann sich ja auch in der Luft noch bewegen, attackieren, ...:
//            if(characterStateComponent.knockback.x==0 && characterStateComponent.knockback.y==0){ //if no knockback
//
//            }


        }

    }

    public InputSystem() {
        signature = new BitSet();

        signature.set(ConversionUtils.indexFromID(CharacterStateComponent.id));
        signature.set(ConversionUtils.indexFromID(MotionComponent.id));

        motion = ComponentManager.getComponentList(MotionComponent.class);
        characterState = ComponentManager.getComponentList(CharacterStateComponent.class);
    }

    @Override
    public BitSet getSignature() {
        return signature;
    }

    //if true is returned the player is currently multi-jumping (respectively double jumping)
    private boolean multiJump(boolean[] array) {
        return !array[1] || !array[2];
    }


}
