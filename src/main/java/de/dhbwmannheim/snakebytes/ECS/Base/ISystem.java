package de.dhbwmannheim.snakebytes.ECS.Base;

import java.util.BitSet;

public interface ISystem {
     void update(double deltaTime) throws Exception;

    /**
     * This function specifies which Components an Entity needs to have in order to be processed by the System.
     *
     * @return BitSet representing Component flags
     */
     BitSet getSignature();

    void addEntity(Entity e);

    void removeEntity(Entity e);

    void clearEntities();
}
