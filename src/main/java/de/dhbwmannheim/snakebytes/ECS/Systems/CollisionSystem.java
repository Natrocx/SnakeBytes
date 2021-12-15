// Author: Jonas Lauschke
package de.dhbwmannheim.snakebytes.ECS.Systems;

import de.dhbwmannheim.snakebytes.ECS.*;
import de.dhbwmannheim.snakebytes.ECS.Base.System;
import de.dhbwmannheim.snakebytes.ECS.Base.*;
import de.dhbwmannheim.snakebytes.ECS.util.ConversionUtils;

import java.util.BitSet;


public class CollisionSystem extends System {

    // Input components:
    private final ComponentList<BoundingBoxComponent> boundingBoxComponents;
    private final ComponentList<PositionComponent> positionComponents;

    // In-Out components:
    private final ComponentList<MotionComponent> motionComponents;

    /// System Output
    private final ComponentList<AttackCollisionComponent> attackCollisionComponents;
    private final ComponentList<ScreenBorderCollisionComponent> screenBorderCollisionComponents;
    private final ComponentList<CharacterStateComponent> characterStateComponents;

    public CollisionSystem() {
        signature = new BitSet();

        signature.set(ConversionUtils.indexFromID(PositionComponent.id));
        signature.set(ConversionUtils.indexFromID(BoundingBoxComponent.id));

        this.positionComponents = ComponentManager.getComponentList(PositionComponent.class);
        this.boundingBoxComponents = ComponentManager.getComponentList(BoundingBoxComponent.class);
        this.motionComponents = ComponentManager.getComponentList(MotionComponent.class);
        this.attackCollisionComponents = ComponentManager.getComponentList(AttackCollisionComponent.class);
        this.screenBorderCollisionComponents = ComponentManager.getComponentList(ScreenBorderCollisionComponent.class);
        this.characterStateComponents = ComponentManager.getComponentList(CharacterStateComponent.class);
    }

    @Override
    public void update(double deltaTime) {
        for (int i = 0; i < entities.size(); i++) {
            var e1 = entities.get(i);

            var e1BB = boundingBoxComponents.getComponent(e1);
            var e1Pos = positionComponents.getComponent(e1);

            // We are using a naive algorithm, that checks collisions with all Entities in the Game
            for (int j = i + 1; j < entities.size(); j++) {
                var e2 = entities.get(j);

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
                if (e1Pos.value.x + e1BB.size.x > e2Pos.value.x &&
                        e1Pos.value.x < e2Pos.value.x + e2BB.size.x &&
                        e1Pos.value.y + e1BB.size.y > e2Pos.value.y &&
                        e1Pos.value.y < e2Pos.value.y + e2BB.size.y) {
                    switch (e1BB.boxType) {
                        case Ground, HighPlatform, Screen -> {
                        }
                        case Player -> playerCollisions(e1, e2, deltaTime);
                        case Attack -> attackCollisions(e1, e2, deltaTime);
                        case SpecialAttack -> specialAttackCollisions(e1, e2, deltaTime);
                    }
                }
            }
        }
    }

    private void specialAttackCollisions(Entity e1, Entity e2, double deltaTime) {
        var e2BB = boundingBoxComponents.getComponent(e2);

        switch (e2BB.boxType) {
            case Ground, SpecialAttack, Attack, HighPlatform -> { /* do nothing */ }
            case Player -> playerCollisions(e2, e1, deltaTime); // already implemented in player collisions
            case Screen -> {
                Engine.destroyAttack(e1);
            }
        }

    }

    private void attackCollisions(Entity e1, Entity e2, double deltaTime) {
        var e2BB = boundingBoxComponents.getComponent(e2);

        switch (e2BB.boxType) {
            case Ground, Attack, HighPlatform, Screen, SpecialAttack -> { /* do nothing */ }
            case Player -> playerCollisions(e2, e1, deltaTime); // already implemented in player collisions
        }

    }

    @Override
    public BitSet getSignature() {
        return signature;
    }

    /// Handle collisions in case e1 is a player. Position corrections will be made inline.
    private void playerCollisions(Entity e1, Entity e2, double deltaTime) {
        var e1Pos = positionComponents.getComponent(e1);
        var e1BB = boundingBoxComponents.getComponent(e1);

        var e2BB = boundingBoxComponents.getComponent(e2);
        var e2Pos = positionComponents.getComponent(e2);

        // Determine in which direction and how much to correct (if necessary); the values will be added onto the
        // naively determined position to determine the physically correct position.
        // Always push e1 in the direction which would result in shorter movement
        var x_overlap = e1Pos.value.x > e2Pos.value.x ? e1Pos.value.x - (e2Pos.value.x + e2BB.size.x) : (e1Pos.value.x + e1BB.size.x) - e2Pos.value.x;
        var y_overlap = e1Pos.value.y > e2Pos.value.y ? e1Pos.value.y - (e2Pos.value.y + e2BB.size.y) : (e1Pos.value.y + e1BB.size.y) - e2Pos.value.y;


        switch (e2BB.boxType) {

            // The HighPlatform-branch is supposed to move the player on top of the platform if they jump from below
            case HighPlatform:
                // move player up if they are jumping
                if (motionComponents.getComponent(e1).velocity.y > 0) {
                    x_overlap = 0;
                    y_overlap = e2Pos.value.y + e2BB.size.y - e1Pos.value.y;

                }
                // Otherwise, fall through to Ground for accurate Movement correction

                // The Ground-branch is supposed to be a physically accurate movement correction
            case Ground: {
                e1Pos.value.y = e1Pos.value.y + y_overlap;

                motionComponents.getComponent(e1).velocity.y = 0.0;

                characterStateComponents.getComponent(e1).jumping[0] = false;
                characterStateComponents.getComponent(e1).jumping[1] = false;

                break;
            }

            // The player-branch does basically exactly the same as Ground but distributed to both objects of the collision
            case Player: {
                // for player 1:
                e1Pos.value.x += -0.5 * x_overlap;
                //e1Pos.value.y += -0.5 * y_overlap;

                // for player 2 (correct into other direction):
                e2Pos.value.x += 0.5 * x_overlap;
                //e2Pos.value.y += 0.5 * y_overlap;

                motionComponents.getComponent(e1).velocity.y = 0.0;
                motionComponents.getComponent(e1).velocity.x = 0.0;

                motionComponents.getComponent(e2).velocity.y = 0.0;
                motionComponents.getComponent(e2).velocity.x = 0.0;

                break;
            }

            // there are no corrections to be made inline so the system will simply emit the corresponding component
            case SpecialAttack:
                attackCollisionComponents.insertComponent(e1, new AttackCollisionComponent(true));
                break;
            case Attack:
                attackCollisionComponents.insertComponent(e1, new AttackCollisionComponent(false));
                Engine.destroyAttack(e2);
                break;

            case Screen:
                screenBorderCollisionComponents.insertComponent(e1, new ScreenBorderCollisionComponent());
                Engine.destroyAttack(e2);
                break;
        }
    }

}

