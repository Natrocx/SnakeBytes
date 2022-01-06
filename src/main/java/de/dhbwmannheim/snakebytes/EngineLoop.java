package de.dhbwmannheim.snakebytes;

import de.dhbwmannheim.snakebytes.ECS.Base.Engine;

/**
 * Author:  @Kirolis Eskondis
 **/

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
