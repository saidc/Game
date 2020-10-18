
package components;

import Jade.Component;
import Util.Transform;
import org.joml.Vector2f;
import org.joml.Vector4f;
import renderer.Texture;

public class SpriteRender extends Component{

    private Vector4f color;
    private Sprite sprite;
    
    private Transform lastTransform;
    private boolean isDirty = false;
    
    public SpriteRender(Vector4f color) {
        this.color = color;
        this.sprite = new Sprite(null);
    }

    public SpriteRender(Sprite sprite) {
        this.sprite = sprite;
        this.color = new Vector4f(1, 1, 1, 1);
    }

    @Override
    public void start() {
        this.lastTransform = gameObject.getTransformCopy();
    }

    @Override
    public void update(float dt) {
        if (!this.lastTransform.equals(this.gameObject.getTransform())) {
            this.gameObject.getTransform().copy(this.lastTransform);
            isDirty = true;
        }
    }

    public Vector4f getColor() {
        return this.color;
    }

    public Texture getTexture() {
        return sprite.getTexture();
    }

    public Vector2f[] getTexCoords() {
        return sprite.getTexCoords();
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
        this.isDirty = true;
    }

    public void setColor(Vector4f color) {
        if (!this.color.equals(color)) {
            this.isDirty = true;
            this.color.set(color);
        }
    }

    public boolean isDirty() {
        return this.isDirty;
    }

    public void setClean() {
        this.isDirty = false;
    }
}
