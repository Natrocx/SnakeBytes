package de.dhbwmannheim.snakebytes.ECS.util;

/**
 * Utility class which provides static helper methods for unit conversion
 * Author:  @Jonas Lauschke
 **/

public class ConversionUtils {

    /**
     * Returns the index to a given Components ID
     *
     * @return log_2(id)
     */
    public static int indexFromID(int id) {
        return (int) (Math.log(id) / Math.log(2) + 1e-10 /* tolerance */ );
    }
}
