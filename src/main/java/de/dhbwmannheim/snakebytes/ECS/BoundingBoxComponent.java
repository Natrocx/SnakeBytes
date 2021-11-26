package de.dhbwmannheim.snakebytes.ECS;

import de.dhbwmannheim.snakebytes.ECS.Base.Component;

public class BoundingBoxComponent extends Component {
    public static int id = 0b1;

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
