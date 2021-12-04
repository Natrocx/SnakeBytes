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

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class InputSystem extends System {

    ComponentList<MotionComponent> motion;
    ComponentList<CharacterStateComponent> characterState;

    List<Entity> entities;

    //the following code is based upon: https://gamedevelopment.tutsplus.com/tutorials/introduction-to-javafx-for-game-development--cms-23835
    ArrayList<String> input = new ArrayList<String>();
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
                if (input[-1]==...)
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



    private boolean multiJump(boolean[] array){
        if(array[1] && array[2]){
            return false;
        }
        return true;
    }



}
