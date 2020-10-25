
package game.Build;

import org.joml.Vector2i;

public class Transform {
    public Vector2i position;
    public Vector2i scale;

    public Transform() {
        init(new Vector2i(), new Vector2i());
    }

    public Transform(Vector2i position) {
        init(position, new Vector2i());
    }

    public Transform(Vector2i position, Vector2i scale) {
        init(position, scale);
    }

    public void init(Vector2i position, Vector2i scale) {
        this.position = position;
        this.scale = scale;
    }

    public Transform copy() {
        return new Transform(new Vector2i(this.position), new Vector2i(this.scale));
    }

    public void copy(Transform to) {
        to.position.set(this.position);
        to.scale.set(this.scale);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Transform)) return false;

        Transform t = (Transform)o;
        return t.position.equals(this.position) && t.scale.equals(this.scale);
    }
    public Vector2i getPosition(){
        return this.position;
    }
    public Vector2i getScale(){
        return this.scale;
    }
    public void add(Vector2i add){
        this.position.x += add.x;
        this.position.y += add.y;
    }
}
