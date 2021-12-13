// Author: Jonas Lauschke
package de.dhbwmannheim.snakebytes.ECS.Base;

import java.util.BitSet;

public class Entity {
    private static int numEntities = 0;
    public final int id;
    public BitSet signature;

    public Entity() {
        id = numEntities++; // assign a rolling ID and increment

        java.lang.System.out.println("Created Entity: " + id);
        signature = new BitSet(64); // 64 Components should be a reasonable assumption and fits into natively supported data types
    }

    @Override
    public int hashCode() {
        return id;
    }
}