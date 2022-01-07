// Author: Jonas Lauschke
package de.dhbwmannheim.snakebytes.ECS;

import de.dhbwmannheim.snakebytes.ECS.Base.Component;

/**
 * Represents all data for a attack collision
 */
public class AttackCollisionComponent extends Component {
    public static final int id = 0b10000;
    public boolean specialAttack = false;

    public AttackCollisionComponent(boolean specialAttack) {
        this.specialAttack = specialAttack;
    }

    @Override
    public int getId() {
        return id;
    }
}
