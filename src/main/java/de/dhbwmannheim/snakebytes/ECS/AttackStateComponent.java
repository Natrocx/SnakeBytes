package de.dhbwmannheim.snakebytes.ECS;

import de.dhbwmannheim.snakebytes.ECS.Base.Component;

public class AttackStateComponent extends Component {
    public static int id = 0b100000000;
    public int state; //0=Pointer links 1= Pointer rechts 2= Buch links 3= buch rechts
    public int emittingEntity;
    public double TTL = 10; // time to live: default - 10

    public AttackStateComponent(int state, int emittingEntity, double TTL) {
        this.state = state;
        this.emittingEntity = emittingEntity;
        this.TTL = TTL;
    }

    public AttackStateComponent(int state, int emittingEntity) {
        this.state = state;
        this.emittingEntity = emittingEntity;
    }

    public AttackStateComponent(int state) {
        this.state = state;
    }

    @Override
    public int getId() {
        return id;
    }
}
