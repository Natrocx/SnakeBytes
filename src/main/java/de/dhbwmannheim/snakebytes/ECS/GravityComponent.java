package de.dhbwmannheim.snakebytes.ECS;

import de.dhbwmannheim.snakebytes.ECS.Base.Component;

//      Author: Jonas Lauschke

/**
 * Gravity will be applied to the motion of an entity to create a parabola.
 *
 * It may be treated/considered as the gradient of the derivative of the resulting movement.
 */
public class GravityComponent extends Component {

    public final int id = 0b10000000;

    public double value;

    public GravityComponent(double value) {
        this.value = value;
    }

    @Override
    public int getId() {
        return id;
    }
}
