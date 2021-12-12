package de.dhbwmannheim.snakebytes.ECS.util;

public class ConversionUtils {

    public static int indexFromID(int id) {
        return (int) (Math.log(id) / Math.log(2) + 1e-10);
    }
}
