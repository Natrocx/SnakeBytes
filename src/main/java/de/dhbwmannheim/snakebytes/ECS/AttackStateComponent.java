package de.dhbwmannheim.snakebytes.ECS;

import de.dhbwmannheim.snakebytes.ECS.Base.Component;

public class AttackStateComponent extends Component {
    public int id = 0b100000000;
    public int state; //0=Pointer links 1= Pointer rechts 2= Buch links 3= buch rechts

    public AttackStateComponent(int id, int state) {
        this.id = id;
        this.state = state;
    }

    @Override
    public int getId() {
        return id;
    }
}
