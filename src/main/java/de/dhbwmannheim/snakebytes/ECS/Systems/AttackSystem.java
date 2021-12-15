package de.dhbwmannheim.snakebytes.ECS.Systems;

import de.dhbwmannheim.snakebytes.ECS.*;
import de.dhbwmannheim.snakebytes.ECS.Base.ComponentList;
import de.dhbwmannheim.snakebytes.ECS.Base.ComponentManager;
import de.dhbwmannheim.snakebytes.ECS.Base.Entity;
import de.dhbwmannheim.snakebytes.ECS.Base.System;
import de.dhbwmannheim.snakebytes.ECS.util.ConversionUtils;
import de.dhbwmannheim.snakebytes.GUI.CharacterMenu;

import java.util.BitSet;

/**
 * Author: @Jonas Lauschke
 *
 * @Kirolis Eskondis
 * @Thu Giang Tran
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


        for (int i = 0; i < entities.size(); i++) {
            var entity = entities.get(i);

            MotionComponent attackMotion = motionComponents.getComponent(entity);
            PositionComponent attackPosition = positionComponents.getComponent(entity);
            CharacterStateComponent playerKnockback = characterState.getComponent(entity);
            CharacterStateComponent lookingDirection = characterState.getComponent(entity);

            //if normal attack is registered, then
            if (!attackCollisionComponent.getComponent(entity).specialAttack) {

                //add knockback to player
                playerKnockback.knockback +=0.05;

                //push enemy with new calculated knockback * velocity

            } else {
                //if special attack is registered, then

                //add knockback to player
                playerKnockback.knockback +=0.075;

                //push enemy with new calculated knockback * velocity
                //attackMotion.velocity = new Vec2<>(attackMotion.velocity.x + 0.1 * playerKnockback.knockback, attackMotion.velocity.y + 0.05 * playerKnockback.knockback);

            }
            if(lookingDirection.lookingDirection == 0){
                attackMotion.velocity = new Vec2<>(attackMotion.velocity.x + (0.1 * playerKnockback.knockback), attackMotion.velocity.y + (0.05 * playerKnockback.knockback));
            }
            if(lookingDirection.lookingDirection == 1){
                attackMotion.velocity = new Vec2<>(attackMotion.velocity.x - (0.1 * playerKnockback.knockback), attackMotion.velocity.y + (0.05 * playerKnockback.knockback));
            }
            java.lang.System.out.println(entities.size());
            attackMotion.timeToDecay = 1;

            attackCollisionComponent.removeComponent(entity);
        }

    }


    @Override
    public BitSet getSignature() {
        return signature;
    }
}
