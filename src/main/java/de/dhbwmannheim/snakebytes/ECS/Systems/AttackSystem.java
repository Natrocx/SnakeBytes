package de.dhbwmannheim.snakebytes.ECS.Systems;

import de.dhbwmannheim.snakebytes.ECS.AttackCollisionComponent;
import de.dhbwmannheim.snakebytes.ECS.Base.ComponentList;
import de.dhbwmannheim.snakebytes.ECS.Base.Entity;
import de.dhbwmannheim.snakebytes.ECS.Base.System;
import de.dhbwmannheim.snakebytes.ECS.CharacterStateComponent;
import de.dhbwmannheim.snakebytes.ECS.MotionComponent;
import de.dhbwmannheim.snakebytes.ECS.PositionComponent;

import java.util.BitSet;

public class AttackSystem extends System {

    private final ComponentList<PositionComponent> positionComponents;
    private final ComponentList<MotionComponent> motionComponents;
    private final ComponentList<CharacterStateComponent> characterState;


    public AttackSystem(ComponentList<PositionComponent> positionComponents, ComponentList<MotionComponent> motionComponents, ComponentList<CharacterStateComponent> characterState) {
        this.positionComponents = positionComponents;
        this.motionComponents = motionComponents;
        this.characterState = characterState;
    }

    @Override
    public void update(double deltaTime) throws Exception {
        //TODO wo muss arm entity einfügen/erstellen?
        Entity armEntity = new Entity();

        for (Entity entity : entities) {
            MotionComponent attackMotion = motionComponents.getComponent(entity);
            PositionComponent attackPosition = positionComponents.getComponent(entity);
            CharacterStateComponent knockback = characterState.getComponent(entity);
            // AttackCollisionComponent TODO what do i need for attackCollision

            if (characterStateComponent.attacking = true) {
                characterStateComponent.knockback =

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
}
