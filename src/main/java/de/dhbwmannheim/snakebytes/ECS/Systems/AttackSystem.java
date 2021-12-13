package de.dhbwmannheim.snakebytes.ECS.Systems;

import de.dhbwmannheim.snakebytes.ECS.*;
import de.dhbwmannheim.snakebytes.ECS.Base.*;
import de.dhbwmannheim.snakebytes.ECS.Base.System;
import de.dhbwmannheim.snakebytes.ECS.util.ConversionUtils;

import java.util.BitSet;

/**
 * Author: @Jonas Lauschke
 *         @Kirolis Eskondis
 *         @Thu Giang Tran
 * This class is the System which handles knockback and collision if an attack occurs
 */

public class AttackSystem extends System {

    private final ComponentList<PositionComponent> positionComponents;
    private final ComponentList<MotionComponent> motionComponents;
    private final ComponentList<CharacterStateComponent> characterState;
    private final ComponentList<AttackCollisionComponent> attackCollisionComponent;


    public AttackSystem() {
        signature = new BitSet();
        signature.set(ConversionUtils.indexFromID(PositionComponent.id));
        signature.set(ConversionUtils.indexFromID(MotionComponent.id));
        signature.set(ConversionUtils.indexFromID(CharacterStateComponent.id));
        signature.set(ConversionUtils.indexFromID(AttackCollisionComponent.id));

       positionComponents = ComponentManager.getComponentList(PositionComponent.class);
       motionComponents = ComponentManager.getComponentList(MotionComponent.class);
       characterState = ComponentManager.getComponentList(CharacterStateComponent.class);
       attackCollisionComponent = ComponentManager.getComponentList(AttackCollisionComponent.class);
    }

    @Override
    public void update(double deltaTime) throws Exception {


        for (Entity entity : entities) {

            MotionComponent attackMotion = motionComponents.getComponent(entity);
            PositionComponent attackPosition = positionComponents.getComponent(entity);
            CharacterStateComponent playerKnockback = characterState.getComponent(entity);

            AttackCollisionComponent attackCollision = attackCollisionComponent.getComponent(entity);

            //if normal attack is registered, then
            if (characterState.getComponent(entity).attacking == true) {

                //add knockback to player
                playerKnockback.knockback = +0.5;

                //push enemy with new calculated knockback * velocity
                attackMotion.velocity = new Vec2<>(attackPosition.value.x+0.1 * playerKnockback.knockback, attackPosition.value.y+0.05 * playerKnockback.knockback);



            } else {
                //if special attack is registered, then
                if (characterState.getComponent(entity).specialAttacking == true){

                    //add knockback to player
                    playerKnockback.knockback = +0.75;

                    //push enemy with new calculated knockback * velocity
                    attackMotion.velocity = new Vec2<>(attackPosition.value.x+0.1 * playerKnockback.knockback, attackPosition.value.y+0.05 * playerKnockback.knockback);

                }
            }

        }

    }

    @Override
    public BitSet getSignature() {
        return signature;
    }
}
