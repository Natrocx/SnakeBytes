//code by Eric Stefan

package de.dhbwmannheim.snakebytes.ECS;

import de.dhbwmannheim.snakebytes.ECS.Base.Component;

/**
 * Author: @Eric Stefan
 * This Component contains many important states of a player
 */

public class CharacterStateComponent extends Component {
    public static final int id = 0b1000000;

    public double knockback; //remaining velocity of knockback
    public double attackCooldown; //remaining time of attack cooldown
    public double specialAttackCooldown; //remaining time of special attack cooldown
    public int lives; //remaining lives
    public String playerName;
    public boolean[] jumping; //because only double jumping is allowed
    public boolean attacking;
    public boolean specialAttacking;


    @Override
    public int getId() {
        return id;
    }
}
