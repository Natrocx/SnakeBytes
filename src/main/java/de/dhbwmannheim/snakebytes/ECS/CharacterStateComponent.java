//code by Eric Stefan

package de.dhbwmannheim.snakebytes.ECS;

import de.dhbwmannheim.snakebytes.ECS.Base.Component;

public class CharacterStateComponent extends Component {
    public static int id = 0b1000;

    Vec2<Double> knockback; //remaining velocity of knockback
    double attackCooldown; //remaining time of attack cooldown
    int lives; //remaining lives
    String playerName;

}
