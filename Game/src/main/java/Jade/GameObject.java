
package Jade;

import Util.Transform;
import java.util.ArrayList;
import java.util.List;
import org.joml.Vector2f;

public class GameObject {
    private String name;
    private List<Component> components;
    public Transform transform;
    private int zIndex;

    public GameObject(String name) {
        this.name = name;
        this.components = new ArrayList<>();
        this.transform = new Transform();
        this.zIndex = 0;
    }

    public GameObject(String name, Transform transform, int zIndex) {
        this.name = name;
        this.zIndex = zIndex;
        this.components = new ArrayList<>();
        this.transform = transform;
    }

    public <T extends Component> T getComponent(Class<T> componentClass) {
        for (Component c : components) {
            if (componentClass.isAssignableFrom(c.getClass())) {
                try {
                    return componentClass.cast(c);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    assert false : "Error: Casting component.";
                }
            }
        }

        return null;
    }

    public <T extends Component> void removeComponent(Class<T> componentClass) {
        for (int i=0; i < components.size(); i++) {
            Component c = components.get(i);
            if (componentClass.isAssignableFrom(c.getClass())) {
                components.remove(i);
                return;
            }
        }
    }

    public void addComponent(Component c) {
        c.gameObject = this;
        this.components.add(c);
    }

    public void update(float dt) {
        for (int i=0; i < components.size(); i++) {
            components.get(i).update(dt);
        }
    }

    public void start() {
        for (int i=0; i < components.size(); i++) {
            components.get(i).start();
        }
    }

    public int zIndex() {
        return this.zIndex;
    }
    
    public Vector2f getPosition(){
        return this.transform.getPosition();
    }
    public Vector2f getScale(){
        return this.transform.getScale();
    }
    public void move(Vector2f movement){
        this.transform.add(movement);
    }
    public Transform getTransformCopy(){
        return this.transform.copy();
    }
    public Transform getTransform(){
        return this.transform;
    }
    
}
