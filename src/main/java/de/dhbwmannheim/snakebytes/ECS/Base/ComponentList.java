package de.dhbwmannheim.snakebytes.ECS.Base;

import java.util.List;
import java.util.Map;

/**
 * A packed/dense List of Components indexed by Entities
 */
public class ComponentList<T extends Component> {
    private List<T> components;
    private Map<Entity, Integer> componentListIndices;

    private int getEntitiesComponentIndex(Entity entity) {
        return componentListIndices.get(entity);
    }

    public void insertComponent(Entity entity, T component) {
        var index = getEntitiesComponentIndex(entity);
    }

    public T getComponent(Entity entity) {
        return components.get(getEntitiesComponentIndex(entity));
    }

    public void removeComponent(Entity entity) {
        components.remove(getEntitiesComponentIndex(entity));
        componentListIndices.remove(entity);
    }

}
