package de.dhbwmannheim.snakebytes.ECS.Base;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public abstract class System {
    protected final List<Entity> entities = new ArrayList<>();

    public abstract void update(double deltaTime) throws Exception;

    /**
     * This function specifies which Components an Entity needs to have in order to be processed by the System.
     *
     * @return BitSet representing Component flags
     */
    public abstract BitSet getSignature();

    public void addEntity(Entity e) {
        entities.add(e);
    }

    public void removeEntity(Entity e) {
        entities.remove(e);
    }
}
