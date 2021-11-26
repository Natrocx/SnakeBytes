package de.dhbwmannheim.snakebytes.ECS;

import de.dhbwmannheim.snakebytes.ECS.Base.Component;

public class MotionComponent extends Component {
    public static int id = 0b100;

    /// the velocity is specified in units per second
    Vec2<Double> velocity;
}
