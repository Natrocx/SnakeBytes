package de.dhbwmannheim.snakebytes.ECS.Systems;

import de.dhbwmannheim.snakebytes.ECS.Base.ComponentList;
import de.dhbwmannheim.snakebytes.ECS.Base.Entity;
import de.dhbwmannheim.snakebytes.ECS.Base.System;
import de.dhbwmannheim.snakebytes.ECS.BoundingBoxComponent;
import de.dhbwmannheim.snakebytes.ECS.PositionComponent;

import java.util.BitSet;
import java.util.List;

public class CollisionSystem extends System {

    ComponentList<BoundingBoxComponent> boundingBoxes;
    ComponentList<PositionComponent> positions;
    
    List<Entity> entities;

    @Override
    public void update(double deltaTime) {
        for (Entity e1 : entities) {
            var e1_bb = boundingBoxes.getComponent(e1);
            var e1_pos = positions.getComponent(e1);

            // We are using a naive algorithm, that checks collisions with all Entities in the Game
            // All collisions are computed twice, however this does not matter, as they are only stored once
            for (Entity e2 : entities) {
                // skip tests with self
                if (e1.id == e2.id) {
                    continue;
                }

                var e2_bb = boundingBoxes.getComponent(e2);
                var e2_pos = positions.getComponent(e2);

                /* This condition checks for axis-oriented rectangle collisions by checking:
                    1. is the sum of x1 and width1 (RIGHT lower point of the rectangle) right of the LEFT lower point of the second rectangle?
                    2. is the LEFT lower point of the rectangle left of the RIGHT lower point of the second rectangle?
                    if both of these are true, then there is an overlap on the X-axis
                    3. is the left UPPER point of the rectangle higher than the left LOWER point of the second rectangle?
                    4. is the left LOWER point of the rectangle lower than the left UPPER point of the second rectangle?
                    if both of these are true then there is an overlap on the Y-axis
                    and thus if all of these are true, the rectangles overlap/collide
                 */
                if(e1_pos.value.x + e1_bb.width > e2_pos.value.x  &&
                   e1_pos.value.x < e2_pos.value.x + e2_bb.width  &&
                   e1_pos.value.y + e1_bb.height > e2_pos.value.y &&
                   e1_pos.value.y < e2_pos.value.y + e2_bb.height) {

                }
            }


        }
    }

    @Override
    public BitSet getSignature() {
        return null;
    }
}
