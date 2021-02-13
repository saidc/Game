
package game.Build;

import game.Build.Component.Component;
import game.Build.Component.SpriteRenderer;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.joml.Vector2i;

public class GameObject {
    
    private String name;
    private List<Component> components;
    public Transform transform;
    private int zIndex;
    private Consumer<Dimension> ClickListener = null;
    
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
        
        this.components.add(c);
        c.gameObject = this;
    }
    
    public void update(long time) {
        
        for (int i=0; i < components.size(); i++) {
            components.get(i).update(time);
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
    public Vector2i getPosition(){
        return this.transform.getPosition();
    }
    public Vector2i getScale(){
        return this.transform.getScale();
    }
    public void move(Vector2i movement){
        this.transform.add(movement);
    }
    public Transform getTransformCopy(){
        return this.transform.copy();
    }
    public Transform getTransform(){
        return this.transform;
    }
    public String getname(){
        return this.name;
    }
    public boolean isClicked(Vector2i ClickedPosition) {
        boolean isclicked = false;
        SpriteRenderer spr = this.getComponent(SpriteRenderer.class);
        Dimension dimension = null;
        if(spr.getTexture() != null){
            dimension = spr.getDimension();
        }else{
            dimension = new Dimension(this.transform.scale.x,this.transform.scale.y);
        }
        Vector2i pos = this.transform.getPosition();

        if(     (ClickedPosition.x > pos.x && ClickedPosition.y > pos.y) &&
                (ClickedPosition.x < pos.x + dimension.width && ClickedPosition.y < pos.y + dimension.height)
          ){
            isclicked = true;
            if(ClickListener != null){
                this.ClickListener.accept(new Dimension(ClickedPosition.x-pos.x, ClickedPosition.y - pos.y));
            }

        } 
        return isclicked;
    }
    
    public void addClickListener(Consumer<Dimension> ClickListener){
        this.ClickListener = ClickListener;
    }
}
