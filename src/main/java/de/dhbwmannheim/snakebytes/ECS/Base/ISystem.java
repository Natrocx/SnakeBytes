package de.dhbwmannheim.snakebytes.ECS.Base;

import java.util.BitSet;

/**
 * A System represents a piece of functionality/logic

 * Author:  @Jonas Lauschke
 */
public interface ISystem {
     void update(double deltaTime) throws Exception;

    /*
     * This function specifies which Components an Entity needs to have in order to be processed by the System.
     *
     * @return BitSet representing Component flags corresponding to their ids - see ConversionUtils::indexFromID
     */
     BitSet getSignature();

    void addEntity(Entity e);

    void removeEntity(Entity e);

    void clearEntities();
}
