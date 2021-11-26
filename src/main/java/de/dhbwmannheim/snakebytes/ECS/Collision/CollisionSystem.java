package de.dhbwmannheim.snakebytes.ECS.Collision;

import de.dhbwmannheim.snakebytes.ECS.ComponentList;
import de.dhbwmannheim.snakebytes.ECS.Entity;
import de.dhbwmannheim.snakebytes.ECS.System;

import java.util.BitSet;
import java.util.List;

public class CollisionSystem extends System {

    ComponentList<BoundingBoxComponent> boundingBoxes;
    ComponentList<PositionComponent> positions;
    
    List<Entity> entities;

    @Override
    public void update(double deltaTime) {
        for (Entity entity : entities) {
            boundingBoxes.getComponent(entity);

            for (Entity e2 : entities) {
                // skip if entity is the same
                if (entity == e2) {
                    continue;
                }
            }
            /*
             if (rect1.x < rect2.x + rect2.w &&
        rect1.x + rect1.w > rect2.x &&
        rect1.y < rect2.y + rect2.h &&
        rect1.h + rect1.y > rect2.y) {
             */

            // Collision found:

        }
    }

    @Override
    public BitSet getSignature() {
        return null;
    }
}
