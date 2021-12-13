// Author: Jonas Lauschke
package de.dhbwmannheim.snakebytes.ECS.Systems;

import de.dhbwmannheim.snakebytes.ECS.Base.System;
import de.dhbwmannheim.snakebytes.ECS.Base.*;
import de.dhbwmannheim.snakebytes.ECS.CharacterStateComponent;
import de.dhbwmannheim.snakebytes.ECS.ScreenBorderCollisionComponent;
import de.dhbwmannheim.snakebytes.ECS.util.ConversionUtils;

import java.util.BitSet;


/**
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
        boolean reset = false;
        for (Entity entity : entities) {
            reset = true;

            // get current character state to determine actions taken
            var characterState = characterStateComponentComponents.getComponent(entity);
            if(characterState != null) {
                characterState.lives--;
                if (characterState.lives < 0) {
                    playersLost[lossCount] = entity;
                    lossCount++;
                }
            }

            screenBorderCollisionComponents.removeComponent(entity);
        }

        if (lossCount > 0)
            Engine.finish(playersLost);
        else if (reset)
            Engine.reset();
    }

    @Override
    public BitSet getSignature() {
        return signature;
    }
}
