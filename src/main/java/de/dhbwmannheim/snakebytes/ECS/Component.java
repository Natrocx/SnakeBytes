package de.dhbwmannheim.snakebytes.ECS;

/**
 * Base Type for the Components. All Components must inherit from this class and override the ID with a distinct value.
 */
public abstract class Component {
    public static int id = -1;
}
