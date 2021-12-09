//code by Eric Stefan

package de.dhbwmannheim.snakebytes.ECS.Systems;

import de.dhbwmannheim.snakebytes.ECS.Base.ComponentList;
import de.dhbwmannheim.snakebytes.ECS.Base.ComponentManager;
import de.dhbwmannheim.snakebytes.ECS.Base.Entity;
import de.dhbwmannheim.snakebytes.ECS.Base.System;
import de.dhbwmannheim.snakebytes.ECS.BoundingBoxComponent;
import de.dhbwmannheim.snakebytes.ECS.CharacterStateComponent;
import de.dhbwmannheim.snakebytes.ECS.MotionComponent;
import de.dhbwmannheim.snakebytes.ECS.PositionComponent;
import javafx.scene.input.KeyEvent;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Hashtable;
import java.util.List;

public class InputSystem extends System {

    ComponentList<MotionComponent> motion;
    ComponentList<CharacterStateComponent> characterState;
    List<Entity> entities;
    ArrayList<String> pressedKeys;

    //Saving the KeySettings of player 1 into a HashTable, so that: <keyboard key as String, connected action as String>
    static Hashtable<String,String> player1KeySettings;
    static {
        try {
            player1KeySettings = new Hashtable<>(getKeySettings("player1"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    //Saving the KeySettings of player 2 into a HashTable, so that: <keyboard key as String, connected action as String>
    static Hashtable<String,String> player2KeySettings;
    static {
        try {
            player2KeySettings = new Hashtable<>(getKeySettings("player2"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void keyPressed(KeyEvent keyEvent){

        String code = keyEvent.getCode().toString();
        pressedKeys.add(code);

    }

    @Override
    public void update(double deltaTime) {


        for (Entity entity : entities) {

            MotionComponent motionComponent = motion.getComponent(entity);
            CharacterStateComponent characterStateComponent = characterState.getComponent(entity);

            //iterate over all pressed keys, that did not got processed (pressedKeys only contains non processed keys)
            for (String key : pressedKeys){

                String temp=new String();
                if (player1KeySettings.containsKey(key))
                    temp=player1KeySettings.get(key);
                if (player2KeySettings.containsKey(key))
                    temp=player2KeySettings.get(key);

                if (temp!=null) {
                    switch (temp) {
                        case "right":
                            motionComponent.velocity.x = 0.005;
                            break;
                        case "left":
                            motionComponent.velocity.x = -0.005;
                            break;
                        case "jump":
                            if (multiJump(characterStateComponent.jumping) == false) { //if no double jump is active
                                motionComponent.velocity.y = 0.01; //jump
                                if (characterStateComponent.jumping[0] != true) {
                                    characterStateComponent.jumping[0] = true;
                                } else {
                                    characterStateComponent.jumping[1] = true;
                                }
                            }
                            break;
                        case "attack":
                            if (characterStateComponent.attackCooldown == 0) { //if no attack cooldown
                                characterStateComponent.attacking=true;
                            }
                            break;
                        case "specialAttack":
                            if (characterStateComponent.specialAttackCooldown == 0) { //if no special attack cooldown
                                characterStateComponent.specialAttacking=true;
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

    @Override
    public BitSet getSignature() {
        return null;
    }

    //function to read the JSON-file which saves the key settings of player1 and player2
    private static Hashtable<String,String> getKeySettings(String player) throws IOException, ParseException {
        Hashtable<String,String> playerKeyValues = new Hashtable<>();

        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader("src/main/java/de/dhbwmannheim/snakebytes/ECS/Systems/keySettings.json");
        Object obj = jsonParser.parse(reader);
        JSONArray arr = (JSONArray) obj;

        int help;
        if (player.equals("player1")){
            help=0;
        }else{
            help=1;
        }
        JSONObject obj2 = (JSONObject)  ((JSONObject) arr.get(help)).get(player);
        for (int i=0;i<obj2.size();i++){
            String temp = obj2.keySet().stream().toList().get(i).toString();
            playerKeyValues.put(obj2.get(temp).toString(),temp);
        }

        return playerKeyValues;
    }

    //if true is returned the player is currently multi-jumping (respectively double jumping)
    private boolean multiJump(boolean[] array){
        if(array[1] && array[2]){
            return false;
        }
        return true;
    }



}
