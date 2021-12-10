package de.dhbwmannheim.snakebytes.ECS.Base;

import de.dhbwmannheim.snakebytes.ECS.*;
import de.dhbwmannheim.snakebytes.ECS.Systems.CollisionSystem;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class Engine {

    private static final List<System> systems = new ArrayList<>();

    public static void registerSystem(System sys) {
        systems.add(sys);
    }
    
    public void update(double deltaTime) throws Exception {
        for (System sys :
                systems) {
            sys.update(deltaTime);
        }
    }

    public static void setup() {
        ComponentManager.registerComponentList(AttackCollisionComponent.class);
        ComponentManager.registerComponentList(BoundingBoxComponent.class);
        ComponentManager.registerComponentList(MotionComponent.class);
        ComponentManager.registerComponentList(PositionComponent.class);
        ComponentManager.registerComponentList(ScreenBorderCollisionComponent.class);

        //registerSystem(new MovementSystem());
        registerSystem(new CollisionSystem());

        setupPlayers();
        setupScreenBorders();
        setupPlatforms();
    }

    private static void setupPlatforms() {
        var ground = new Entity();
        var groundPosition = new PositionComponent(new Vec2<>(0.1, 0.2));
        var groundBoundingBox = new BoundingBoxComponent(new Vec2<>(0.8, 0.1), BoundingBoxComponent.BoxType.Ground);

        registerEntity(ground);
        ComponentManager.addComponent(ground, groundPosition);
        ComponentManager.addComponent(ground, groundBoundingBox);
    }

    private static void setupPlayers() {
        var player1 = new Entity();
        var motionComponent1 = new MotionComponent();
        var positionComponent1 = new PositionComponent(new Vec2<>(0.1, 0.25));
        var boundingBoxComponent1 = new BoundingBoxComponent(new Vec2<>(0.05, 0.1), BoundingBoxComponent.BoxType.Player);

        registerEntity(player1);
        ComponentManager.addComponent(player1, motionComponent1);
        ComponentManager.addComponent(player1, positionComponent1);
        ComponentManager.addComponent(player1, boundingBoxComponent1);

        var player2 = new Entity();
        var motionComponent2 = new MotionComponent();
        var positionComponent2 = new PositionComponent(new Vec2<>(0.9, 0.3));
        var boundingBoxComponent2 = new BoundingBoxComponent(new Vec2<>(0.05, 0.1), BoundingBoxComponent.BoxType.Player);

        registerEntity(player2);
        ComponentManager.addComponent(player2, motionComponent2);
        ComponentManager.addComponent(player2, positionComponent2);
        ComponentManager.addComponent(player2, boundingBoxComponent1);
    }

    private static void setupScreenBorders() {
        var borderLeft = new Entity();
        var borderRight = new Entity();
        var borderTop = new Entity();
        var borderBottom = new Entity();

        registerEntity(borderLeft);
        registerEntity(borderRight);
        registerEntity(borderTop);
        registerEntity(borderBottom);

        var blPosition = new PositionComponent(new Vec2<>(-1.0, -1.0));
        var blBB = new BoundingBoxComponent(new Vec2<>(1.0, 2.0), BoundingBoxComponent.BoxType.Screen);
        var brPosition = new PositionComponent(new Vec2<>(1.0, -1.0));
        var brBB = new BoundingBoxComponent(new Vec2<>(1.0, 2.0), BoundingBoxComponent.BoxType.Screen);
        var btPosition = new PositionComponent(new Vec2<>(0.0, 1.0));
        var btBB = new BoundingBoxComponent(new Vec2<>(1.0, 1.0), BoundingBoxComponent.BoxType.Screen);
        var bbPosition = new PositionComponent(new Vec2<>(0.0, -1.0));
        var bbBB = new BoundingBoxComponent(new Vec2<>(1.0, 1.0), BoundingBoxComponent.BoxType.Screen);

        ComponentManager.addComponent(borderLeft, blPosition);
        ComponentManager.addComponent(borderLeft, blBB);
        ComponentManager.addComponent(borderRight, brPosition);
        ComponentManager.addComponent(borderRight, brBB);
        ComponentManager.addComponent(borderTop, btPosition);
        ComponentManager.addComponent(borderTop, btBB);
        ComponentManager.addComponent(borderBottom, bbPosition);
        ComponentManager.addComponent(borderBottom, bbBB);
    }

    /**
     * May be used to initially register or re-register an Entity with added Components
     *
     * @param entity May or may not be registered already.
     */
    public static void registerEntity(Entity entity) {
        for (System system : systems) {
            BitSet signature = (BitSet) system.getSignature().clone();
            signature.and(entity.signature);

            if (signature.equals(system.getSignature())) {
                system.addEntity(entity);
            }
        }
    }

    /**
     * May be used to remove an Entity from unfitting Systems using the entities Signature
     *
     * @param entity May or may not be registered already.
     */
    public static void unregisterEntity(Entity entity) {
        for (System system : systems) {
            BitSet signature = (BitSet) system.getSignature().clone();
            signature.and(entity.signature);

            if (!signature.equals(system.getSignature())) {
                system.removeEntity(entity);
            }
        }
    }

    public static void run() {

        Instant last = Instant.now();
        // TODO: get cancel condition from input system
        while (true) {
            Instant now = Instant.now();
            /* Systems will be executed in order of registration - see setup for further information */
            for (System sys : systems) {
                update((double) Duration.between(last, now).toNanos() / 1_000_000_000);
            }
            last = now;
        }
    }
}
