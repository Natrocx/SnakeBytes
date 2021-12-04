//code by Eric Stefan

package de.dhbwmannheim.snakebytes.ECS;

import de.dhbwmannheim.snakebytes.ECS.Base.Component;

public class CharacterStateComponent extends Component {
    public static int id = 0b1000;

    public Vec2<Double> knockback; //remaining velocity of knockback
    public double attackCooldown; //remaining time of attack cooldown
    public int lives; //remaining lives
    public String playerName;
    public boolean[] jumping = new boolean[2]; //because only double jumping is allowed


}
