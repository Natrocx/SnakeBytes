package de.dhbwmannheim.snakebytes.ECS.Base;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * A packed/dense List of Components indexed by Entities
 */
public class ComponentList<T extends Component> {

    private final Map<Entity, T> components = new HashMap<>();

    private Function<Entity, Void> addComponentCallback;
    private Function<Entity, Void> removeComponentCallback;


    public void insertComponent(Entity entity, T component) {
        components.put(entity, component);
        addComponentCallback.apply(entity);
    }

    public T getComponent(Entity entity) {
        return components.get(entity);
    }

    public void removeComponent(Entity entity) {
        components.remove(entity);
        removeComponentCallback.apply(entity);
    }

    void registerCallbacks(Function<Entity, Void> addComponentCallback, Function<Entity, Void> removeComponentCallback) {
        this.addComponentCallback = addComponentCallback;
        this.removeComponentCallback = removeComponentCallback;
    }

}
