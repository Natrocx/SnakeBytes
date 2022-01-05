package de.dhbwmannheim.snakebytes.ECS.Systems;

import de.dhbwmannheim.snakebytes.ECS.Base.System;
import de.dhbwmannheim.snakebytes.ECS.Base.*;
import de.dhbwmannheim.snakebytes.ECS.CharacterStateComponent;
import de.dhbwmannheim.snakebytes.ECS.ScreenBorderCollisionComponent;
import de.dhbwmannheim.snakebytes.ECS.util.ConversionUtils;
import de.dhbwmannheim.snakebytes.GUI.GameOverlay;

import java.util.BitSet;


/*
 * Author: @Jonas Lauschke
 * This System processes ScreenBorderCollisionComponents of players to determine wins and reset the Engine
 */
public class KnockoutSystem extends System {

     ComponentList<ScreenBorderCollisionComponent> screenBorderCollisionComponents;
    ComponentList<CharacterStateComponent> characterStateComponentComponents;

    public KnockoutSystem() {
        signature = new BitSet();

        signature.set(ConversionUtils.indexFromID(ScreenBorderCollisionComponent.id));
        signature.set(ConversionUtils.indexFromID(CharacterStateComponent.id));

        characterStateComponentComponents = ComponentManager.getComponentList(CharacterStateComponent.class);
        screenBorderCollisionComponents = ComponentManager.getComponentList(ScreenBorderCollisionComponent.class);
    }

    @Override
    public void update(double deltaTime) {
        Entity[] playersLost = new Entity[2];
        int lossCount = 0;
        boolean finish = false;
        boolean reset = false;
        //noinspection ForLoopReplaceableByForEach - will result in the JVM complaining
        for (int i = 0; i < entities.size(); i++) {
            var entity = entities.get(i);
            reset = true;
            // get current character state to determine actions taken
            var characterState = characterStateComponentComponents.getComponent(entity);
            if(characterState != null) {
                characterState.lives--;
                if(entity == Engine.getPlayer(0)){
                    GameOverlay.scP2++;
                }
                if(entity == Engine.getPlayer(1)){
                    GameOverlay.scP1++;
                }
                if (characterState.lives == 0) {
                    finish = true;
                    playersLost[lossCount] = entity;
                    lossCount++;
                }
            }

            screenBorderCollisionComponents.removeComponent(entity);
        }

        if (finish)
            Engine.finish(playersLost);
        else if (reset)
            Engine.reset();
    }

    @Override
    public BitSet getSignature() {
        return signature;
    }
}
