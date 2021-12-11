// Author: Jonas Lauschke
package de.dhbwmannheim.snakebytes.ECS;

import de.dhbwmannheim.snakebytes.ECS.Base.Component;
import de.dhbwmannheim.snakebytes.ECS.Base.Entity;

public class AttackCollisionComponent extends Component {
    public static final int id = 0b10000;
    public Vec2<Double> overlap;
    public
    //TODO wo muss arm entity einfügen/erstellen?


    public AttackCollisionComponent(Vec2<Double> overlap) {
        this.overlap = overlap;
    }



    @Override
    public int getId() {
        return id;
    }
}
