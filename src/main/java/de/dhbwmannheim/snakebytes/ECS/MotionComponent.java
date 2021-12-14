// Author: Jonas Lauschke
package de.dhbwmannheim.snakebytes.ECS;

import de.dhbwmannheim.snakebytes.ECS.Base.Component;

import java.time.Instant;

public class MotionComponent extends Component {
    public static final int id = 0b100;

    /// the velocity is specified in units per second
    public Vec2<Double> velocity;
    public double timeToDecay;
    public double maxTimeToDecay = -1;

    public MotionComponent(Vec2<Double> tVec2, double ttd) {
        velocity = tVec2;
        timeToDecay = ttd;
    }

    public MotionComponent(Vec2<Double> tVec2) {
        velocity = tVec2;
    }

    public MotionComponent() {
        velocity = new Vec2<>(0.0, 0.0);
        timeToDecay = -1;
    }

    @Override
    public int getId() {
        return id;
    }
}
