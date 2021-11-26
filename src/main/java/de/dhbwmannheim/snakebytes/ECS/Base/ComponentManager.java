package de.dhbwmannheim.snakebytes.ECS.Base;

import java.util.ArrayList;
import java.util.List;

public class ComponentManager {

    @SuppressWarnings("rawtypes") // consistent use of generic Type IDs make checks unnecessary
    private final List<ComponentList> componentLists = new ArrayList<>();

    public <T extends Component> void registerComponent() {
        componentLists.add(T.id, new ComponentList<T>());
    }

    @SuppressWarnings("unchecked") // consistent use of generic Type IDs make checks unnecessary
    public <T extends Component> void addComponent(Entity entity, T component) {
        componentLists.get(T.id).insertComponent(entity, component);
    }

    public <T extends Component> void removeComponent(Entity entity){
        componentLists.get(T.id).removeComponent(entity);
    }


    @SuppressWarnings("unchecked") // consistent use of generic Type IDs make checks unnecessary
    public <T extends Component> T getComponent(Entity entity) {
        return (T) componentLists.get(T.id).getComponent(entity);
    }
}
