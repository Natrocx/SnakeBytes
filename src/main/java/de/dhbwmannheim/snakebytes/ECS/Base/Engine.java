package de.dhbwmannheim.snakebytes.ECS.Base;

import de.dhbwmannheim.snakebytes.ECS.*;
import de.dhbwmannheim.snakebytes.ECS.Systems.*;
import de.dhbwmannheim.snakebytes.GUI.CharacterMenu;
import de.dhbwmannheim.snakebytes.Sounds.SoundManager;
import javafx.scene.input.KeyEvent;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.function.Consumer;

/**
 * Authors: @Jonas Lauschke, @Kirolis Eskondis, @Thu Giang Tran
 * This class implements the shared parts of the Engine and facilitates System execution
 **/

public class Engine {

    public static final PositionComponent POSITION_COMPONENT_1 = new PositionComponent(new Vec2<>(0.2222, 0.3167));
    public static final PositionComponent POSITION_COMPONENT_2 = new PositionComponent(new Vec2<>(0.7407, 0.3167));
    private static final List<ISystem> systems = new ArrayList<>();
    private static final List<ISystem> clearOnReset = new ArrayList<>();
    private static final Entity[] players = new Entity[2];
    public static ArrayList<Entity> attackList = new ArrayList<>();
    private static Victory finish = Victory.None;
    public static boolean paused = false;
    private static InputSystem inputSystem;
    private static SoundManager soundManager = new SoundManager();


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
        ComponentManager.registerComponentList(AttackStateComponent.class);

        var cleanupSystem = new CleanupSystem();
        clearOnReset.add(cleanupSystem);
        registerSystem(cleanupSystem);
        registerSystem(new MotionSystem());
        registerSystem(new CollisionSystem());
        var knockoutSystem = new KnockoutSystem();
        clearOnReset.add(knockoutSystem);
        registerSystem(knockoutSystem);
        var attackSystem = new AttackSystem();
        clearOnReset.add(attackSystem);
        registerSystem(attackSystem);

        inputSystem = new InputSystem();
        registerSystem(inputSystem);

        setupPlayers();
        setupScreenBorders();
        setupPlatforms();
    }

    public static Consumer<KeyEvent> getKeyPressedCallback() {
        if (inputSystem != null) {
            return inputSystem::keyPressed;
        }
        return null;
    }

    private static void setupPlatforms() {
        var ground = new Entity();
        var groundPosition = new PositionComponent(new Vec2<>(0.225, 0.01));
        var groundBoundingBox = new BoundingBoxComponent(new Vec2<>(0.565, 0.305), BoundingBoxComponent.BoxType.Ground);

        var platformLeft = new Entity();
        var platformLeftPosition = new PositionComponent(new Vec2<>(0.28, 0.46));
        var platformLeftBoundingBox = new BoundingBoxComponent(new Vec2<>(0.085, 0.033), BoundingBoxComponent.BoxType.HighPlatform);

        var platformRight = new Entity();
        var platformRightPosition = new PositionComponent(new Vec2<>(0.65, 0.46));
        var platformRightBoundingBox = new BoundingBoxComponent(new Vec2<>(0.085, 0.033), BoundingBoxComponent.BoxType.HighPlatform);

        var platformMiddle = new Entity();
        var platformMiddlePosition = new PositionComponent(new Vec2<>(0.47, 0.638));
        var platformMiddleBoundingBox = new BoundingBoxComponent(new Vec2<>(0.085, 0.033), BoundingBoxComponent.BoxType.HighPlatform);

        registerEntity(ground);
        ComponentManager.addComponent(ground, groundPosition);
        ComponentManager.addComponent(ground, groundBoundingBox);

        registerEntity(platformLeft);
        ComponentManager.addComponent(platformLeft, platformLeftPosition);
        ComponentManager.addComponent(platformLeft, platformLeftBoundingBox);

        registerEntity(platformRight);
        ComponentManager.addComponent(platformRight, platformRightPosition);
        ComponentManager.addComponent(platformRight, platformRightBoundingBox);

        registerEntity(platformMiddle);
        ComponentManager.addComponent(platformMiddle, platformMiddlePosition);
        ComponentManager.addComponent(platformMiddle, platformMiddleBoundingBox);
    }

    private static void setupPlayers() {
        var player1 = new Entity();
        var motionComponent1 = new MotionComponent();
        motionComponent1.maxTimeToDecay = 0.2;
        var boundingBoxComponent1 = new BoundingBoxComponent(new Vec2<>(0.05, 0.1), BoundingBoxComponent.BoxType.Player);
        var gravityComponent1 = new GravityComponent(0.1);
        var positionComponent1 = new PositionComponent(new Vec2<>(0.2222, 0.16));
        var characterStateComponent1 = new CharacterStateComponent(1, 1);

        registerEntity(player1);
        players[0] = player1;
        ComponentManager.addComponent(player1, motionComponent1);
        ComponentManager.addComponent(player1, positionComponent1);
        ComponentManager.addComponent(player1, boundingBoxComponent1);
        ComponentManager.addComponent(player1, gravityComponent1);
        ComponentManager.addComponent(player1, characterStateComponent1);

        var player2 = new Entity();
        var motionComponent2 = new MotionComponent();
        motionComponent2.maxTimeToDecay = 0.2;
        var boundingBoxComponent2 = new BoundingBoxComponent(new Vec2<>(0.05, 0.1), BoundingBoxComponent.BoxType.Player);
        var gravityComponent2 = new GravityComponent(0.1);
        var positionComponent2 = new PositionComponent(new Vec2<>(0.7407, 0.16));
        var characterStateComponent2 = new CharacterStateComponent(0, 0);

        registerEntity(player2);
        players[1] = player2;
        ComponentManager.addComponent(player2, motionComponent2);
        ComponentManager.addComponent(player2, positionComponent2);
        ComponentManager.addComponent(player2, boundingBoxComponent2);
        ComponentManager.addComponent(player2, gravityComponent2);
        ComponentManager.addComponent(player2, characterStateComponent2);
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

        ComponentManager.destroyComponents(entity);
        for (ISystem system : systems) {
            system.removeEntity(entity);
        }

    }

    public static void reset() {
        java.lang.System.out.println("reset");
        ComponentManager.addComponent(players[0], POSITION_COMPONENT_1.copy());
        ComponentManager.addComponent(players[1], POSITION_COMPONENT_2.copy());

        //noinspection ForLoopReplaceableByForEach - will cause comodification errors
        for (int i = 0; i < attackList.size(); i++) {
            destroyAttack(attackList.get(i));
        }

        ComponentManager.clearComponents(AttackCollisionComponent.class);
        ComponentManager.clearComponents(ScreenBorderCollisionComponent.class);
        ComponentManager.clearComponents(AttackStateComponent.class);

        for (ISystem system : clearOnReset) {
            system.clearEntities();
        }

        for (CharacterStateComponent component : ComponentManager.getComponentList(CharacterStateComponent.class).getAllComponents()) {
            component.jumping = new boolean[]{false, false};
            component.attackCooldown = 0;
            component.specialAttackCooldown = 0;
            component.attacking = false;
            component.specialAttacking = false;
        }

        for (Entity entity : players) {
            ComponentManager.getComponent(entity, MotionComponent.class).velocity = new Vec2<>(0.0, 0.0);
        }
    }

    /**
     * Call this when one players lives becomes negative
     *
     * @param playersKnockedOut Array of players that lost in the current tick of the engine
     */
    public static void finish(Entity[] playersKnockedOut) {
        CharacterMenu.render = false;

        soundManager.playMatchOver();
        CharacterMenu.render = false;


        for (Entity entity : playersKnockedOut) {
            if (entity == players[0] && finish == Victory.None) {
                finish = Victory.PlayerOne;
            } else if (entity == players[1] && finish == Victory.None) {
                finish = Victory.PlayerTwo;
            } else if (entity == players[0] || entity == players[1]) {
                finish = Victory.Draw;
            }
        }
    }

    /**
     * Submit finish request to stop the engine on next iteration (without information on winning/losing players)
     */
    public static void finish() {
        var characterStateComponent1 = ComponentManager.getComponent(players[0], CharacterStateComponent.class);
        var characterStateComponent2 = ComponentManager.getComponent(players[1], CharacterStateComponent.class);

        CharacterMenu.render = false;

        var win = characterStateComponent1.lives - characterStateComponent2.lives;
        if (win == 0) {
            Engine.finish(players);
        } else if (win > 0) {
            Engine.finish(new Entity[]{players[0]});
        } else {
            Engine.finish(new Entity[]{players[1]});
        }
    }


    public static void togglePause() {
        paused = !paused;
    }

    public static Victory run() throws Exception {

        Instant last = Instant.now();
        while (finish == Victory.None) {
            if (paused) {
                Thread.sleep(16);
                last = Instant.now();
            } else {
                Instant now = Instant.now();
                /* Systems will be executed in order of registration - see setup function for further information */
                update((double) Duration.between(last, now).toNanos() / 1_000_000_000);
                last = now;
            }
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
        Draw,
        None
    }
}
