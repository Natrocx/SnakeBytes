// Author: Jonas Lauschke
package de.dhbwmannheim.snakebytes.ECS.Base;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public abstract class System implements ISystem {
    protected final List<Entity> entities = new ArrayList<>();
    protected BitSet signature;

    @Override
    public abstract void update(double deltaTime) throws Exception;

    /**
     * This function specifies which Components an Entity needs to have in order to be processed by the System.
     *
     * @return BitSet representing Component flags
     */
    @Override
    public abstract BitSet getSignature();

    @Override
    public void addEntity(Entity e) {
        entities.add(e);
    }

    @Override
    public void removeEntity(Entity e) {
        entities.remove(e);
    }
}
