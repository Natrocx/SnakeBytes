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
    public int lookingDirection; //0=left, 1=right
    public int state; //for FrameHandler: 0=left, 1=right, 2=attackLeft, 3=attackRight, 4=specialAttackLeft, 5=specialAttackRight


    @Override
    public int getId() {
        return id;
    }
}
