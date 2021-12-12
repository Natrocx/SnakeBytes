//code by Eric Stefan

package de.dhbwmannheim.snakebytes.ECS;

import de.dhbwmannheim.snakebytes.ECS.Base.Component;

/**
 * Author: @Eric Stefan
 * This Component contains many important states of a player
 */

public class CharacterStateComponent extends Component {
    public static final int id = 0b1000000;

    public Vec2<Double> knockback; //remaining velocity of knockback
    public double attackCooldown; //remaining time of attack cooldown
    public double specialAttackCooldown; //remaining time of special attack cooldown
    public int lives; //remaining lives
    public boolean[] jumping; //because only double jumping is allowed
    public boolean attacking;
    public boolean specialAttacking;
    public int lookingDirection; //0=left, 1=right
    public int state; //for FrameHandler: 0=left, 1=right, 2=attackLeft, 3=attackRight, 4=specialAttackLeft, 5=specialAttackRight

    public CharacterStateComponent(Vec2<Double> knockback, double attackCooldown, double specialAttackCooldown, int lives, boolean[] jumping, boolean attacking, boolean specialAttacking, int lookingDirection, int state) {
        this.knockback = knockback;
        this.attackCooldown = attackCooldown;
        this.specialAttackCooldown = specialAttackCooldown;
        this.lives = lives;
        this.jumping = jumping;
        this.attacking = attacking;
        this.specialAttacking = specialAttacking;
        this.lookingDirection = lookingDirection;
        this.state = state;
    }

    @Override
    public int getId() {
        return id;
    }
}
