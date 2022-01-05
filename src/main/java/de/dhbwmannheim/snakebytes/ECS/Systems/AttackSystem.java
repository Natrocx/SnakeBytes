package de.dhbwmannheim.snakebytes.ECS.Systems;

import de.dhbwmannheim.snakebytes.ECS.*;
import de.dhbwmannheim.snakebytes.ECS.Base.*;
import de.dhbwmannheim.snakebytes.ECS.Base.System;
import de.dhbwmannheim.snakebytes.ECS.util.ConversionUtils;

import java.util.BitSet;

/**
 * Author:  @Jonas Lauschke
 *           @Kirolis Eskondis
 *           @Thu Giang Tran
 * This class is the System which handles knockback and collision if an attack occurs
 **/

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

            //if normal attack is registered, then
            if (!attackCollisionComponent.getComponent(entity).specialAttack) {

                //add knockback to player
                playerKnockback.knockback +=0.05;

                //push enemy with new calculated knockback * velocity

            } else {
                //if special attack is registered, then

                //add knockback to player
                playerKnockback.knockback +=0.1;

                //push enemy with new calculated knockback * velocity
                //attackMotion.velocity = new Vec2<>(attackMotion.velocity.x + 0.1 * playerKnockback.knockback, attackMotion.velocity.y + 0.05 * playerKnockback.knockback);

            }
            Entity player2 = null;
            for(Entity player : Engine.getPlayers()){
                if(player == entity){
                    if(player == Engine.getPlayer(0)){
                        player2 = Engine.getPlayer(1);
                    } else{
                        player2 = Engine.getPlayer(0);

                    }
                }
            }
            if(player2 != null){
                PositionComponent player2AttackPos = ComponentManager.getComponentList(PositionComponent.class).getComponent(player2);
                if(player2AttackPos.value.x < attackPosition.value.x){
                    attackMotion.velocity = new Vec2<>(attackMotion.velocity.x + (0.1 * playerKnockback.knockback), attackMotion.velocity.y + (0.05 * playerKnockback.knockback));
                }
                if(player2AttackPos.value.x > attackPosition.value.x){
                    attackMotion.velocity = new Vec2<>(attackMotion.velocity.x - (0.1 * playerKnockback.knockback), attackMotion.velocity.y + (0.05 * playerKnockback.knockback));
                }
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
