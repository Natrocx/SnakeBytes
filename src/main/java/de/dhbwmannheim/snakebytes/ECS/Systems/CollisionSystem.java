package de.dhbwmannheim.snakebytes.ECS.Systems;

import de.dhbwmannheim.snakebytes.ECS.Base.Component;
import de.dhbwmannheim.snakebytes.ECS.Base.ComponentList;
import de.dhbwmannheim.snakebytes.ECS.Base.Entity;
import de.dhbwmannheim.snakebytes.ECS.Base.System;
import de.dhbwmannheim.snakebytes.ECS.*;
import org.jetbrains.annotations.NotNull;

import java.util.BitSet;
import java.util.List;

public class CollisionSystem extends System {

    // Input components:
    private ComponentList<BoundingBoxComponent> boundingBoxComponents;
    private ComponentList<PositionComponent> positionComponents;

    // In-Out components:
    private ComponentList<MotionComponent> motionComponents;

    /// System Output
    private ComponentList<AttackCollisionComponent> attackCollisionComponents;
    private ComponentList<ScreenBorderCollisionComponent> screenBorderCollisionComponents;

    private List<Entity> entities;

    @Override
    public void update(double deltaTime) {
        // TODO: maybe skip unnecessary computations?
        for (Entity e1 : entities) {
            var e1BB = boundingBoxComponents.getComponent(e1);
            var e1Pos = positionComponents.getComponent(e1);

            // We are using a naive algorithm, that checks collisions with all Entities in the Game
            // All collisions are computed twice, however this does not matter, as they are only stored once
            for (Entity e2 : entities) {
                // skip tests with self
                if (e1.id == e2.id) {
                    continue;
                }

                var e2BB = boundingBoxComponents.getComponent(e2);
                var e2Pos = positionComponents.getComponent(e2);

                /* This condition checks for axis-oriented rectangle collisions by checking:
                    1. is the sum of x1 and width1 (RIGHT lower point of the rectangle) right of the LEFT lower point of the second rectangle?
                    2. is the LEFT lower point of the rectangle left of the RIGHT lower point of the second rectangle?
                    if both of these are true, then there is an overlap on the X-axis
                    3. is the left UPPER point of the rectangle higher than the left LOWER point of the second rectangle?
                    4. is the left LOWER point of the rectangle lower than the left UPPER point of the second rectangle?
                    if both of these are true then there is an overlap on the Y-axis
                    and thus if all of these are true, the rectangles overlap/collide
                 */
                if (e1Pos.value.x + e1BB.width > e2Pos.value.x &&
                        e1Pos.value.x < e2Pos.value.x + e2BB.width &&
                        e1Pos.value.y + e1BB.height > e2Pos.value.y &&
                        e1Pos.value.y < e2Pos.value.y + e2BB.height) {
                    switch (e1BB.boxType) {
                        case Ground, HighPlatform, Attack, Screen -> {
                            continue;
                        }
                        case Player -> playerCollisions(e1, e2, e1BB.boxType);
                    }
                }
            }
        }
    }

    @Override
    public BitSet getSignature() {
        return null;
    }

    /// Handle collisions in case e1 is a player. Position corrections will be made inline.
    private void playerCollisions(Entity e1, Entity e2, BoundingBoxComponent.@NotNull BoxType e2BoxType) {
        var e1BB = boundingBoxComponents.getComponent(e1);
        var e1Pos = positionComponents.getComponent(e1);

        var e2BB = boundingBoxComponents.getComponent(e2);
        var e2Pos = positionComponents.getComponent(e2);

        // Determine in which direction and how much to correct (if necessary); the values will be added onto the naively
        // determined position to determine the physically correct position.
        // Always push e1 in the direction which would result in shorter movement
        var x_overlap = e1Pos.value.x < e2Pos.value.x ? e2Pos.value.x - e1Pos.value.x : e1Pos.value.x - e2Pos.value.x;
        var y_overlap = e1Pos.value.y < e2Pos.value.y ? e2Pos.value.y - e1Pos.value.y : e1Pos.value.y - e2Pos.value.y;


        switch (e2BoxType) {

            // The HighPlatform-branch is supposed to move the player on top of the platform if they jump from below
            case HighPlatform:
                // move player up if they are jumping
                if (motionComponents.getComponent(e1).velocity.y > 0) {
                    x_overlap = 0;
                    y_overlap = e2Pos.value.y + e2BB.height - e1Pos.value.y;

                }
                // Otherwise, fall through to Ground for accurate Movement correction

            // The Ground-branch is supposed to be a physically accurate movement correction
            case Ground: {
                e1Pos.value.x += x_overlap;
                e2Pos.value.y += y_overlap;
                /*
                movementCorrectionComponents.insertComponent(e1, new MovementCorrectionComponent(
                        new Vec2<Double>(x_overlap, y_overlap)));
                 */
                break;
            }

            // The player-branch does basically exactly the same as Ground but distributed to both objects of the collision
            case Player: {
                // for player 1:
                e1Pos.value.x +=   0.5 * x_overlap;
                e1Pos.value.y +=   0.5 * y_overlap;

                // for player 2 (correct into other direction):
                e2Pos.value.x += - 0.5 * x_overlap;
                e2Pos.value.y += - 0.5 * y_overlap;

                break;
            }

            // there are no corrections to be made inline so the system will simply emit the corresponding component
            case Attack:
                attackCollisionComponents.insertComponent(e1, new AttackCollisionComponent( new Vec2<Double>(
                        x_overlap, y_overlap
                )));
                break;

            case Screen:
                screenBorderCollisionComponents.insertComponent(e1, new ScreenBorderCollisionComponent());
                break;
        }
    }

}

