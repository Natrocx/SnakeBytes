package de.dhbwmannheim.snakebytes.ECS.Base;

import java.util.BitSet;

public class Entity {
    public final int id;
    public BitSet signature;
    private static int numEntities = 0;

    Entity() {
        id = numEntities++; // assign a rolling ID and increment

        signature = new BitSet(64); // 64 Components should be a reasonable assumption and fits into natively supported data types
    }

    @Override
    public int hashCode() {
        return id;
    }
}