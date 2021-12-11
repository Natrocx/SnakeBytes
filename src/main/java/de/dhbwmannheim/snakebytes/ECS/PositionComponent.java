package de.dhbwmannheim.snakebytes.ECS;

import de.dhbwmannheim.snakebytes.ECS.Base.Component;
import de.dhbwmannheim.snakebytes.ECS.Vec2;

public class PositionComponent extends Component {
    public static final int id = 0b10;

    public Vec2<Double> value;
}
