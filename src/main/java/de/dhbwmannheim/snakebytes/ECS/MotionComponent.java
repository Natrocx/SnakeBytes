// Author: Jonas Lauschke
package de.dhbwmannheim.snakebytes.ECS;

import de.dhbwmannheim.snakebytes.ECS.Base.Component;

public class MotionComponent extends Component {
    public static final int id = 0b100;

    /// the velocity is specified in units per second
    public Vec2<Double> velocity;

    @Override
    public int getId() {
        return id;
    }
}
