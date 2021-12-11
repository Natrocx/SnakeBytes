package de.dhbwmannheim.snakebytes.ECS.Systems;

import de.dhbwmannheim.snakebytes.ECS.*;
import de.dhbwmannheim.snakebytes.ECS.Base.ComponentList;
import de.dhbwmannheim.snakebytes.ECS.Base.Entity;
import de.dhbwmannheim.snakebytes.ECS.Base.System;

import java.util.BitSet;

/**
 * Author: @Jonas Lauschke
 *         @Thu Giang Tran
 * This class is the System which handles knockback and collision if an attack occurs
 */

public class AttackSystem extends System {

    private final ComponentList<PositionComponent> positionComponents;
    private final ComponentList<MotionComponent> motionComponents;
    private final ComponentList<CharacterStateComponent> characterState;
    private final ComponentList<AttackCollisionComponent> attackCollisionComponent;


    public AttackSystem(ComponentList<PositionComponent> positionComponents, ComponentList<MotionComponent> motionComponents, ComponentList<CharacterStateComponent> characterState, ComponentList<AttackCollisionComponent> attackCollisionComponent) {
        this.positionComponents = positionComponents;
        this.motionComponents = motionComponents;
        this.characterState = characterState;
        this.attackCollisionComponent = attackCollisionComponent;
    }

    @Override
    public void update(double deltaTime) throws Exception {

        for (Entity entity : entities) {
            MotionComponent attackMotion = motionComponents.getComponent(entity);
            PositionComponent attackPosition = positionComponents.getComponent(entity);
            CharacterStateComponent attackKnockback = characterState.getComponent(entity);
            //TODO what do i need for attackCollision
            AttackCollisionComponent attackCollision = attackCollisionComponent.getComponent(entity);

            //if normal attack is registered, then add knockback to enemy
            if (characterState.attacking = true) {
                //TODO wo muss arm entity einfügen/erstellen?
                Entity armEntity = new Entity();
                attackKnockback.knockback = +0.5;
                //TODO how to determine players position, so they fly off in right direction
                attackMotion.velocity = attackCollision.overlap;
            }

            if (characterState.specialAttacking = true){
                attackKnockback.knockback = +0.75;
                attackMotion.velocity = attackCollision.overlap;

            }

        }

        /*kncokbackfactor
         * velocitty anpassen
         * attackcomponent
         * position der plyer bestimmen
         * */

        @Override
        public BitSet getSignature () {
            return null;
        }

    }

    @Override
    public BitSet getSignature() {
        return null;
    }
}
