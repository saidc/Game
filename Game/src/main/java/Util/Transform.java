
package Util;

import org.joml.Vector2f;

public class Transform {
    private Vector2f position;
    private Vector2f scale;

    public Transform() {
        init(new Vector2f(), new Vector2f());
    }

    public Transform(Vector2f position) {
        init(position, new Vector2f());
    }

    public Transform(Vector2f position, Vector2f scale) {
        init(position, scale);
    }

    public void init(Vector2f position, Vector2f scale) {
        this.position = position;
        this.scale = scale;
    }
    
    public Vector2f getPosition(){
        return this.position;
    }
    public Vector2f getScale(){
        return this.scale;
    }
    public void add(Vector2f add){
        this.position.x += add.x;
        this.position.y += add.y;
    }
}
