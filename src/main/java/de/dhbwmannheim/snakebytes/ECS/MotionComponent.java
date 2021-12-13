// Author: Jonas Lauschke
package de.dhbwmannheim.snakebytes.ECS;

import de.dhbwmannheim.snakebytes.ECS.Base.Component;

public class MotionComponent extends Component {
    public static final int id = 0b100;

    /// the velocity is specified in units per second
    public Vec2<Double> velocity;

    public MotionComponent(Vec2<Double> tVec2) {
        velocity = tVec2;
    }

    public MotionComponent() {
        velocity = new Vec2<>(0.0, 0.0);
    }

    @Override
    public int getId() {
        return id;
    }
}
