package de.dhbwmannheim.snakebytes;

import de.dhbwmannheim.snakebytes.ECS.Base.Engine;

public class EngineLoop extends Thread {
    @Override
    public void run(){
        Engine.setup();
        try {
            Engine.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
