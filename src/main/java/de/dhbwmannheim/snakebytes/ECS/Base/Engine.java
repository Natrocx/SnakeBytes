package de.dhbwmannheim.snakebytes.ECS.Base;

import java.util.ArrayList;
import java.util.List;

public class Engine {

    private final List<System> systems = new ArrayList<>();

    public void registerSystem(System sys) {
        systems.add(sys);
    }
    
    public void update(double deltaTime) throws Exception {
        for (System sys :
                systems) {
            sys.update(deltaTime);
        }
    }

}
