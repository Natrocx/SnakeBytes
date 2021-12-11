package de.dhbwmannheim.snakebytes.ECS.Systems;

import de.dhbwmannheim.snakebytes.ECS.*;
import de.dhbwmannheim.snakebytes.ECS.Base.*;
import de.dhbwmannheim.snakebytes.ECS.Base.System;

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
    private final ComponentList<BoundingBoxComponent> boundingBoxComponentComponentList;


    public AttackSystem(ComponentList<PositionComponent> positionComponents, ComponentList<MotionComponent> motionComponents, ComponentList<CharacterStateComponent> characterState, ComponentList<AttackCollisionComponent> attackCollisionComponent, ComponentList<BoundingBoxComponent> boundingBoxComponentComponentList) {
        this.positionComponents = positionComponents;
        this.motionComponents = motionComponents;
        this.characterState = characterState;
        this.attackCollisionComponent = attackCollisionComponent;
        this.boundingBoxComponentComponentList = boundingBoxComponentComponentList;
    }

    @Override
    public void update(double deltaTime) throws Exception {


        for (Entity entity : entities) {
            if(entity==Engine.getPlayer(0)){
                MotionComponent attackMotion = motionComponents.getComponent(Engine.getPlayer(1));
            }else if(entity==Engine.getPlayer(1)){
                MotionComponent attackMotion = motionComponents.getComponent(Engine.getPlayer(0);
            }

            var playerDirection = characterState.getComponent(entity).lookingDirection;
            var playerposition = positionComponents.getComponent(entity).value;
            var playerKnockback = characterState.getComponent(entity).knockback;

            //TODO what do i need for attackCollision
            AttackCollisionComponent attackCollision = attackCollisionComponent.getComponent(entity);

            //if normal attack is registered, then add knockback to enemy
            if (characterState.getComponent(entity).attacking = true) {
                Entity armEntity = new Entity();
                addEntity(armEntity);
                var boundindgBoxComponent = new BoundingBoxComponent(new Vec2<>(0.02,0.005), BoundingBoxComponent.BoxType.Attack);
                ComponentManager.addComponent(armEntity,boundindgBoxComponent);

                switch(playerDirection){
                    case 4:
                        var positionLeftComponent = new PositionComponent(new Vec2<>(playerposition.x-0.02, playerposition.y+0.07));
                        ComponentManager.addComponent(armEntity,positionLeftComponent);
                        break;
                    case 5:
                        var positionRightComponent = new PositionComponent(new Vec2<>(playerposition.x+0.07, playerposition.y+0.07));
                        ComponentManager.addComponent(armEntity,positionRightComponent);
                        break;
                    default:
                        break;
                }


                playerKnockback = +0.5;
                //TODO how to determine players position, so they fly off in right direction
                attackMotion.velocity = attackCollision.overlap;
            }

            if (characterState.getComponent(entity).specialAttacking == true){
                Entity spcAttack = new Entity();


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
