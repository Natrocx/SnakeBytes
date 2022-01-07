package de.dhbwmannheim.snakebytes.ECS;

import de.dhbwmannheim.snakebytes.ECS.Base.Component;

/**
 * Author:  @Jonas Lauschke
 **/

/**
 * Represents a logical bounding-/hitbox for an Entity (i. e. the size in axis-oriented directions)
 */
public class BoundingBoxComponent extends Component {
    public static final int id = 0b1;

    public Vec2<Double> size;
    public BoxType boxType;

    public BoundingBoxComponent(Vec2<Double> size, BoxType boxType) {
        this.size = size;
        this.boxType = boxType;
    }

    @Override
    public int getId() {
        return id;
    }

    public enum BoxType {
        Ground,
        HighPlatform,
        Player,
        Attack,
        SpecialAttack,
        Screen
    }
}
