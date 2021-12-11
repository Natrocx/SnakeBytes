// Author: Jonas Lauschke
package de.dhbwmannheim.snakebytes.ECS;

import de.dhbwmannheim.snakebytes.ECS.Base.Component;

public class PositionComponent extends Component {
    public static final int id = 0b10;
    public Vec2<Double> value;

    public PositionComponent(Vec2<Double> value) {
        this.value = value;
    }

    @Override
    public int getId() {
        return id;
    }

    public PositionComponent copy() {
        return new PositionComponent(new Vec2<>(value.x, value.y));
    }
}
