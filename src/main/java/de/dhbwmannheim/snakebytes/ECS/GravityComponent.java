package de.dhbwmannheim.snakebytes.ECS;

import de.dhbwmannheim.snakebytes.ECS.Base.Component;

public class GravityComponent extends Component {

    public final int id = 0b10000000;

    public double value;

    public GravityComponent(double value) {
        this.value = value;
    }

    @Override
    public int getId() {
        return 0;
    }
}
