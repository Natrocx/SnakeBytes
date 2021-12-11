// Author: Jonas Lauschke
package de.dhbwmannheim.snakebytes.ECS.Base;

import org.jetbrains.annotations.NotNull;

import java.util.BitSet;
import java.util.HashMap;

public class ComponentManager {

    @SuppressWarnings("rawtypes") // consistent use of generic Type IDs make checks unnecessary
    private static final HashMap<Class, ComponentList> componentLists = new HashMap<>();

    public static void registerComponentList(Class<? extends Component> clazz) {
        var list = new ComponentList<>();
        list.registerCallbacks(ComponentManager::addComponentCallback, ComponentManager::removeComponentCallback);
        componentLists.put(clazz, list);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Component> ComponentList<T> getComponentList(Class<T> clazz) {
        return componentLists.get(clazz);
    }

    @SuppressWarnings("unchecked") // consistent use of generic Type IDs make checks unnecessary
    public static <T extends Component> void addComponent(Entity entity, T component) {
        componentLists.get(component.getClass()).insertComponent(entity, component);
    }

    public static <T extends Component> void addComponentCallback(@NotNull Entity entity, T component) {
        BitSet oldSignature = (BitSet) entity.signature.clone();
        entity.signature.set(getIndex(component.getId()));

        if (!oldSignature.equals(entity.signature)) {
            Engine.registerEntity(entity);
        }
    }

    public static <T extends Component> void removeComponentCallback(Entity entity, T component) {
        entity.signature.clear(component.getId());
        Engine.unregisterEntity(entity);
    }


    @SuppressWarnings("unchecked") // consistent use of generic Type IDs make checks unnecessary
    public static <T extends Component> T getComponent(Entity entity, Class<T> clazz) {
        return (T) componentLists.get(clazz).getComponent(entity);
    }

    static void destroyComponents(Entity entity) {
        for (ComponentList<?> list : componentLists.values()) {
            list.removeComponent(entity);
        }
    }

    public static void clearComponents(Class<? extends Component> clazz) {
        registerComponentList(clazz);
    }

    /**
     * Calculates componentLists index from Components identifying bitflags
     *
     * @param id ID to convert; must be a power of 2 vor a reasonable result. To be taken from the Components static id.
     * @return log_2 of the id
     */
    private static int getIndex(int id) {
        return (int) (Math.log(id) / Math.log(2) + 1e-10);
    }
}
