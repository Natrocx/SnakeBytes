// Author: Jonas Lauschke
package de.dhbwmannheim.snakebytes.ECS.Base;

import de.dhbwmannheim.snakebytes.ECS.*;
import de.dhbwmannheim.snakebytes.ECS.Systems.CollisionSystem;
import de.dhbwmannheim.snakebytes.ECS.Systems.InputSystem;
import de.dhbwmannheim.snakebytes.ECS.Systems.KnockoutSystem;
import de.dhbwmannheim.snakebytes.ECS.Systems.MotionSystem;
import de.dhbwmannheim.snakebytes.GUI.CharacterMenu;
import de.dhbwmannheim.snakebytes.Render.FrameHandler;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.function.Consumer;

public class Engine {

    public static ArrayList<Entity> attackList = new ArrayList<>();

    public static final PositionComponent POSITION_COMPONENT_1 = new PositionComponent(new Vec2<>(0.2222, 0.6833));
    public static final PositionComponent POSITION_COMPONENT_2 = new PositionComponent(new Vec2<>(0.7407, 0.6833));
    private static final List<ISystem> systems = new ArrayList<>();
    private static final Entity[] players = new Entity[2];
    private static Victory finish = null;
    private static InputSystem inputSystem;

    public static void registerSystem(System sys) {
        systems.add(sys);
    }

    public static void update(double deltaTime) throws Exception {
        for (ISystem sys :
                systems) {
            sys.update(deltaTime);
        }
    }

    public static void setup() {
        // WARNING: Please do not rearrange order of registration without considering the semantic impact
        ComponentManager.registerComponentList(AttackCollisionComponent.class);
        ComponentManager.registerComponentList(BoundingBoxComponent.class);
        ComponentManager.registerComponentList(MotionComponent.class);
        ComponentManager.registerComponentList(PositionComponent.class);
        ComponentManager.registerComponentList(ScreenBorderCollisionComponent.class);
        ComponentManager.registerComponentList(CharacterStateComponent.class);
        ComponentManager.registerComponentList(GravityComponent.class);

        registerSystem(new MotionSystem());
        registerSystem(new CollisionSystem());
        registerSystem(new KnockoutSystem());
        inputSystem = new InputSystem();
        registerSystem(inputSystem);

        setupPlayers();
        setupScreenBorders();
        setupPlatforms();
    }

    public static Consumer<KeyEvent> getKeyPressedCallback() {
        return inputSystem::keyPressed;
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
        boolean[] doublefalse = {false,false};
        var player1 = new Entity();
        var motionComponent1 = new MotionComponent();
        var boundingBoxComponent1 = new BoundingBoxComponent(new Vec2<>(0.05, 0.1), BoundingBoxComponent.BoxType.Player);
        var gravityComponent1 = new GravityComponent(0.1);
        var characterStateComponent1 = new CharacterStateComponent(1,3,5, CharacterMenu.rounds, new boolean[]{false, false},false,false,1,1);

        registerEntity(player1);
        players[0] = player1;
        ComponentManager.addComponent(player1, motionComponent1);
        ComponentManager.addComponent(player1, POSITION_COMPONENT_1.copy());
        ComponentManager.addComponent(player1, boundingBoxComponent1);
        ComponentManager.addComponent(player1, gravityComponent1);
        ComponentManager.addComponent(player1,characterStateComponent1);

        var player2 = new Entity();
        var motionComponent2 = new MotionComponent();
        var boundingBoxComponent2 = new BoundingBoxComponent(new Vec2<>(0.05, 0.1), BoundingBoxComponent.BoxType.Player);
        var gravityComponent2 = new GravityComponent(0.1);
        var characterStateComponent2 = new CharacterStateComponent(1,0,5, CharacterMenu.rounds,new boolean[]{false, false},false,false,0,0);

        registerEntity(player2);
        players[1] = player2;
        ComponentManager.addComponent(player2, motionComponent2);
        ComponentManager.addComponent(player2, POSITION_COMPONENT_2.copy());
        ComponentManager.addComponent(player2, boundingBoxComponent2);
        ComponentManager.addComponent(player2, gravityComponent2);
        ComponentManager.addComponent(player2,characterStateComponent2);
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
        for (ISystem system : systems) {
            BitSet signature = (BitSet) system.getSignature().clone();
            signature.and(entity.signature);

            if (signature.equals(system.getSignature())) {
                system.addEntity(entity);
            }
        }
    }

    public static void registerAttack(Entity entity) {
        attackList.add(entity);
        registerEntity(entity);

    }

    public static void destroyAttack(Entity entity) {
        attackList.remove(entity);
        destroyEntity(entity);
    }

    /**
     * May be used to remove an Entity from unfitting Systems using the entities Signature
     *
     * @param entity May or may not be registered already.
     */
    public static void unregisterEntity(Entity entity) {
        for (ISystem system : systems) {
            BitSet signature = (BitSet) system.getSignature().clone();
            signature.and(entity.signature);

            if (!signature.equals(system.getSignature())) {
                system.removeEntity(entity);
            }
        }
    }

    public static void destroyEntity(Entity entity) {
        for (ISystem system : systems) {
            system.removeEntity(entity);
        }

        ComponentManager.destroyComponents(entity);
    }

    public static void reset() {
        ComponentManager.addComponent(players[0], POSITION_COMPONENT_1.copy());
        ComponentManager.addComponent(players[1], POSITION_COMPONENT_2.copy());

        ComponentManager.clearComponents(AttackCollisionComponent.class);
        ComponentManager.clearComponents(ScreenBorderCollisionComponent.class);
    }

    /**
     * Call this when one players lives becomes negative
     *
     * @param playersKnockedOut Array of players that lost in the current tick of the engine
     */
    public static void finish(Entity[] playersKnockedOut) {
        for (Entity entity : playersKnockedOut) {
            if (entity == players[0] && finish == null) {
                finish = Victory.PlayerOne;
            } else if (entity == players[1] && finish == null) {
                finish = Victory.PlayerTwo;
            } else if (entity == players[0] || entity == players[1]) {
                finish = Victory.Draw;
            }
        }
    }

    public static Victory run(FrameHandler frameHandler, Stage primaryStage) throws Exception {

        Instant last = Instant.now();
        // TODO: get cancel condition from input system
        while (finish == null) {
            Instant now = Instant.now();
            /* Systems will be executed in order of registration - see setup for further information */
                update((double) Duration.between(last, now).toNanos() / 1_000_000_000);
            last = now;
            frameHandler.update(primaryStage);
        }
        return finish;
    }

    public static Entity getPlayer(int number) {
        return players[number];
    }

    public static Entity[] getPlayers() {
        return players;
    }

    public enum Victory {
        PlayerOne,
        PlayerTwo,
        Draw
    }
}
