
package game.Build.Component;

import org.joml.Vector2f;
import game.Renderer.Texture;

import java.util.ArrayList;
import java.util.List;

public class Spritesheet {
    
    private Texture texture;
    private List<Sprite> sprites;

    public Spritesheet(Texture texture, int spriteWidth, int spriteHeight, int numSpritesWidth, int numSpritesHeight, int spacing) {
        this.sprites = new ArrayList<>();

        this.texture = texture;
        int currentX = 0;
        int currentY = 0;
        
        //int Nsh = (int) texture.getHeight() / spriteHeight;
        //int Nsw = (int) texture.getWidth()  / spriteWidth ;
        //System.out.println("Nsh: "+ Nsh + " ,Nsw: "+Nsw);
        int count = 0;
        for (int i = 0; i < numSpritesHeight; i++) {
            //currentY = (i*spriteHeight)+spacing;
            
            if(i==0){
                currentY = (i*spriteHeight);
            }else{
                currentY = (i*(spriteHeight+spacing));
            }
            
            for (int j = 0; j < numSpritesWidth; j++) {
                //currentX = (j*spriteWidth)+spacing;
                
                if(j==0){
                    currentX = (j*spriteWidth);
                }else{
                    currentX = (j*(spriteWidth+spacing));
                }
                
                float topY    = currentY;
                float rightX  = currentX + spriteWidth;
                float leftX   = currentX;
                float bottomY = currentY + spriteHeight;
                Vector2f[] texCoords = {
                    new Vector2f(rightX, topY),     // UpRightCorner        
                    new Vector2f(rightX, bottomY),  // BottomRightCorner    
                    new Vector2f(leftX, bottomY),   // BottomLeftCorner     
                    new Vector2f(leftX, topY)       // upLeftCorner         
                };
                Sprite sprite = new Sprite();
                sprite.SetAsSubImage();
                sprite.setTexture(this.texture);
                sprite.setTexCoords(texCoords);
                //System.out.println("upLeftCorner_X: "+texCoords[3].x+" ,upLeftCorner_Y: "+texCoords[3].y );
                sprite.setDimension(spriteWidth,spriteHeight);
                this.sprites.add(sprite);
                count++;
            }
        }
        System.out.println("count: "+count);
    }

    public Sprite getSprite(int index) {
        return this.sprites.get(index);
    }
}