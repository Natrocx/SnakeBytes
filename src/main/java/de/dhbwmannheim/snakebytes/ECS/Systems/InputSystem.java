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
        //!!!! es gilt noch zu überprüfen, ob keyEvent unter KeySettings überhaupt entsprechend gesetzt wurde
        String code = keyEvent.getCode().toString();


    }

    @Override
    public void update(double deltaTime) {


        for (Entity entity : entities) {
            //motioncomponent velocity bearbeiten
            MotionComponent motionComponent = motion.getComponent(entity);
            CharacterStateComponent characterStateComponent = characterState.getComponent(entity);
            if (multiJump(characterStateComponent.jumping)==false){ //if no double jump is active
                motionComponent.velocity.y = 0.001; //jump
            }
            if (characterStateComponent.attackCooldown==0){ //if no attack cooldown
                //if (input[-1]==...)
                motionComponent.velocity.x = 0.001;
                motionComponent.velocity.y = 0.001;
            }
            if(characterStateComponent.knockback.x==0 && characterStateComponent.knockback.y==0){ //if no knockback

            }



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
        if (player == "player1"){
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
