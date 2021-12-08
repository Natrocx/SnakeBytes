package de.dhbwmannheim.snakebytes.ECS.Base;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * A packed/dense List of Components indexed by Entities
 */
public class ComponentList<T extends Component> {
    private List<T> components;
    private Map<Entity, Integer> componentListIndices;

    private Function<Entity, Void> addComponentCallback;
    private Function<Entity, Void> removeComponentCallback;

    private int getEntitiesComponentIndex(Entity entity) {
        return componentListIndices.get(entity);
    }

    public void insertComponent(Entity entity, T component) {
        var index = getEntitiesComponentIndex(entity);
        addComponentCallback.apply(entity);
    }

    public T getComponent(Entity entity) {
        return components.get(getEntitiesComponentIndex(entity));
    }

    public void removeComponent(Entity entity) {
        components.remove(getEntitiesComponentIndex(entity));
        componentListIndices.remove(entity);
        removeComponentCallback.apply(entity);
    }

    void registerCallbacks(Function<Entity, Void> addComponentCallback, Function<Entity, Void> removeComponentCallback) {
        this.addComponentCallback = addComponentCallback;
        this.removeComponentCallback = removeComponentCallback;
    }

}
