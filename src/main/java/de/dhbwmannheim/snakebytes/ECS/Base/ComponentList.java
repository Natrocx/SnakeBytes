// Author: Jonas Lauschke
package de.dhbwmannheim.snakebytes.ECS.Base;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * A packed/dense List of Components indexed by Entities
 */
public class ComponentList<T extends Component> {

    private final Map<Entity, T> components = new HashMap<>();

    private BiConsumer<Entity, Component> addComponentCallback;
    private BiConsumer<Entity, Component> removeComponentCallback;


    public void insertComponent(Entity entity, T component) {
        components.put(entity, component);
        addComponentCallback.accept(entity, component);
    }

    public T getComponent(Entity entity) {
        return components.get(entity);
    }

    public void removeComponent(Entity entity) {
        removeComponentCallback.accept(entity, components.get(entity));
        components.remove(entity);
    }

    void registerCallbacks(BiConsumer<Entity, Component> addComponentCallback, BiConsumer<Entity, Component> removeComponentCallback) {
        this.addComponentCallback = addComponentCallback;
        this.removeComponentCallback = removeComponentCallback;
    }

}
