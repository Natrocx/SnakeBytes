// Author: Jonas Lauschke
package de.dhbwmannheim.snakebytes.ECS;

import de.dhbwmannheim.snakebytes.ECS.Base.Component;

public class ScreenBorderCollisionComponent extends Component {
    public static final int id = 0b100000;

    @Override
    public int getId() {
        return id;
    }
}