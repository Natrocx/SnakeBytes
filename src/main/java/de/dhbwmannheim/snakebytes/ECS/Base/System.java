package de.dhbwmannheim.snakebytes.ECS.Base;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.List;

/**
 * Author:  @Jonas Lauschke
 **/

public abstract class System implements ISystem {
    protected final List<Entity> entities = new ArrayList<>();
    protected HashSet<Entity> entitiesPresent = new HashSet<>();
    protected BitSet signature;

    @Override
    public abstract void update(double deltaTime) throws Exception;

    /*
     * This function specifies which Components an Entity needs to have in order to be processed by the System.
     *
     * @return BitSet representing Component flags
     */
    @Override
    public abstract BitSet getSignature();

    @Override
    public void addEntity(Entity e) {
        if (!entitiesPresent.contains(e)) {
            entities.add(e);
            entitiesPresent.add(e);
        }
    }

    @Override
    public void removeEntity(Entity e) {
        if(entitiesPresent.contains(e)) {
            entities.remove(e);
            entitiesPresent.remove(e);
        }
    }

    public void clearEntities() {
        entities.clear();
    }
}
