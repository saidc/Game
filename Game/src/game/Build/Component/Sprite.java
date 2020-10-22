package game.Build.Component;

import game.Renderer.Texture;
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
    private int spriteWidth, spriteHeight;
    
    public Texture getTexture() {
        return this.texture;
    }
    public Image getImage() {
        if(HasSubImage){
            return this.texture.getSubImage((int)this.texCoords[3].x, (int)this.texCoords[3].y, this.spriteWidth, this.spriteHeight);
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

    void setDimension(int spriteWidth, int spriteHeight) {
        this.spriteWidth  = spriteWidth  ; 
        this.spriteHeight = spriteHeight ;
    }
}
