//code by Eric Stefan

package de.dhbwmannheim.snakebytes.ECS.Systems;

import de.dhbwmannheim.snakebytes.ECS.Base.ComponentList;
import de.dhbwmannheim.snakebytes.ECS.Base.ComponentManager;
import de.dhbwmannheim.snakebytes.ECS.Base.Entity;
import de.dhbwmannheim.snakebytes.ECS.Base.System;
import de.dhbwmannheim.snakebytes.ECS.BoundingBoxComponent;
import de.dhbwmannheim.snakebytes.ECS.PositionComponent;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class InputSystem extends System {


    List<Entity> entities;

    //the following code is based upon: https://gamedevelopment.tutsplus.com/tutorials/introduction-to-javafx-for-game-development--cms-23835
    ArrayList<String> input = new ArrayList<String>();
    public void keyPressed(KeyEvent keyEvent){
        //es gilt noch zu überprüfen, ob keyEvent unter KeySettings überhaupt entsprechend gesetzt wurde
        String code = keyEvent.getCode().toString();
        if (attackCooldown()==false || multiJump()==false || knockback()==false){
            input.add(code);

        }
    }

    @Override
    public void update(double deltaTime) {
        for (Entity entity : entities) {
            //motioncomponent velocity bearbeiten
            //charakterstatecomponent
        }
    }

    @Override
    public BitSet getSignature() {
        return null;
    }




    public boolean attackCooldown(){
        return true;
    }

    public boolean multiJump(){
        return true;
    }

    public boolean knockback(){
        return true;
    }



}
