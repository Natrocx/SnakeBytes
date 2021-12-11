// Author: Jonas Lauschke
package de.dhbwmannheim.snakebytes.ECS.Systems;

import de.dhbwmannheim.snakebytes.ECS.Base.ComponentList;
import de.dhbwmannheim.snakebytes.ECS.Base.ComponentManager;
import de.dhbwmannheim.snakebytes.ECS.Base.Entity;
import de.dhbwmannheim.snakebytes.ECS.Base.System;
import de.dhbwmannheim.snakebytes.ECS.MotionComponent;
import de.dhbwmannheim.snakebytes.ECS.PositionComponent;
import de.dhbwmannheim.snakebytes.ECS.util.ConversionUtils;

import java.util.BitSet;

public class MotionSystem extends System {

    private final ComponentList<PositionComponent> positionComponents;
    private final ComponentList<MotionComponent> motionComponents;

    private final double GRAVITY = 0.2;

    @Override
    public void update(double deltaTime) {
        for(Entity entity : entities) {
            var position = positionComponents.getComponent(entity);
            var motion = motionComponents.getComponent(entity);

            position.value.x += deltaTime * motion.velocity.x;
            position.value.y += deltaTime * motion.velocity.y + deltaTime * GRAVITY;
        }
    }

    public MotionSystem() {
        signature = new BitSet();
        signature.set(ConversionUtils.indexFromID(PositionComponent.id));
        signature.set(ConversionUtils.indexFromID(MotionComponent.id));

        positionComponents = ComponentManager.getComponentList(PositionComponent.class);
        motionComponents = ComponentManager.getComponentList(MotionComponent.class);
    }

    @Override
    public BitSet getSignature() {
        return signature;
    }
}