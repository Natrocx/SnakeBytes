package de.dhbwmannheim.snakebytes;

import de.dhbwmannheim.snakebytes.ECS.Base.Engine;

/**
 * The Engine needs to be run on a seperate Thread from the JavaFX classes which is facilitated using the EngineLoop.
 */
public class EngineLoop extends Thread {
    @Override
    public void run(){
        try {
            Engine.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
