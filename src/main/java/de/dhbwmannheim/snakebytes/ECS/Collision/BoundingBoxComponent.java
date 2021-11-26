package de.dhbwmannheim.snakebytes.ECS.Collision;

import de.dhbwmannheim.snakebytes.ECS.Component;
import de.dhbwmannheim.snakebytes.ECS.Vec2;

public class BoundingBoxComponent extends Component {
    public static int id = 0b0;

    public double height;
    public double width;
    public BoxType boxType;

    public enum BoxType {
        Ground,
        HighPlatform,
        Player,
        Attack,
        Screen
    }
}
