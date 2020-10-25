
package game.Build.Component;

import game.Build.Transform;
import game.Renderer.Texture;
import java.awt.Dimension;
import java.awt.Image;
import org.joml.Vector2f;
import org.joml.Vector4f;


public class SpriteRenderer extends Component {

    private Vector4f color = new Vector4f(1, 1, 1, 1);
    private Sprite sprite = new Sprite();
    private String text = null;
    private transient Transform lastTransform;
    private transient boolean isDirty = true;

    @Override
    public void start() {
        this.lastTransform = gameObject.transform.copy();
    }

    @Override
    public void update(float dt) {
        if (!this.lastTransform.equals(this.gameObject.transform)) {
            this.gameObject.transform.copy(this.lastTransform);
            isDirty = true;
        }
    }
    public String getText(){
        return this.text;
    }
    public void setText(String text){
        this.text = text;
    }
    public Vector4f getColor() {
        return this.color;
    }

    public Texture getTexture() {
        return sprite.getTexture();
    }
    public Image getImage() {
        return sprite.getImage();
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
    public Dimension getDimension(){
        return this.sprite.getDimension();
    }
}
