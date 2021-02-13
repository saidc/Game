package game.Build.Component;

import game.Renderer.Texture;
import java.awt.Dimension;
import java.awt.Image;
import org.joml.Vector2f;


public class Sprite {
    
    private Texture texture = null;
    private boolean HasSubImage = false;
    private Vector2f[] texCoords = {
                new Vector2f(1, 1), // UpperRightCorner     
                new Vector2f(1, 0), // BottomRightCorner     
                new Vector2f(0, 0), // BottomLeftCorner  
                new Vector2f(0, 1)  // upperLeftCorner      
    };
    
    private Dimension spriteDimension;
    
    public Texture getTexture() {
        return this.texture;
    }
    public Image getImage() {
        if(HasSubImage){
            return this.texture.getSubImage((int)this.texCoords[3].x, (int)this.texCoords[3].y, this.spriteDimension.width, this.spriteDimension.height);
        }else{
            return this.texture.getImage();
        }
    }
    public Vector2f[] getTexCoords() {
        return this.texCoords;
    }
    public void SetAsSubImage(){
        this.HasSubImage = true;
    }
    public void SetAsSingleImage(){
        this.HasSubImage = false;
    }
    public void setTexture(Texture tex) {
        this.texture = tex;
    }

    public void setTexCoords(Vector2f[] texCoords) {
        this.texCoords = texCoords;
    }

    public void setDimension(int spriteWidth, int spriteHeight) {
        this.spriteDimension = new Dimension(spriteWidth,spriteHeight);
    }
    public Dimension getDimension(){
        return this.spriteDimension;
    }
}
