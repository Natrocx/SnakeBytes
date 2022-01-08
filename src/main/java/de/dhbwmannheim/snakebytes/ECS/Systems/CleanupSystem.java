package de.dhbwmannheim.snakebytes.ECS.Systems;

import de.dhbwmannheim.snakebytes.ECS.AttackStateComponent;
import de.dhbwmannheim.snakebytes.ECS.Base.*;
import de.dhbwmannheim.snakebytes.ECS.Base.System;
import de.dhbwmannheim.snakebytes.ECS.util.ConversionUtils;

import java.util.BitSet;

/**
 * This class serves to delete objects (currently only attacks) which decay after a certain time.
 * Author:  @Jonas Lauschke
 **/


public class CleanupSystem extends System {

    private final ComponentList<AttackStateComponent> attackStateComponents;
    @Override
    public void update(double deltaTime) throws Exception {
        for (int i = 0; i < entities.size(); i++ ) {
            var entity = entities.get(i);

            var attackStateComponent = attackStateComponents.getComponent(entity);
            attackStateComponent.TTL -= deltaTime;
            if (attackStateComponent.TTL < 0) {
                java.lang.System.out.println("Destroying attacK: " + entity);
                Engine.destroyAttack(entity);
            }
        }
    }

    @Override
    public BitSet getSignature() {
        return signature;
    }

    public CleanupSystem() {
        signature = new BitSet();
        signature.set(ConversionUtils.indexFromID(AttackStateComponent.id));

        attackStateComponents = ComponentManager.getComponentList(AttackStateComponent.class);
    }
}
