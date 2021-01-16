
package game.Renderer;

import game.Build.Component.SpriteRenderer;

import java.util.List;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import org.joml.Vector4i;

public class RenderBatch implements java.lang.Comparable<RenderBatch>{
    
    private final int VERTEX_SIZE = 9;

    private SpriteRenderer[] sprites;
    private int numSprites;
    private boolean hasRoom;
    
    private int[] texSlots = {0, 1, 2, 3, 4, 5, 6, 7};

    private List<Texture> textures;
    
    private int vaoID, vboID;
    private int maxBatchSize;
    private int zIndex;
    
    public RenderBatch(int maxBatchSize, int zIndex) {
        this.zIndex = zIndex;
        this.sprites = new SpriteRenderer[maxBatchSize];
        this.maxBatchSize = maxBatchSize;

        this.numSprites = 0;
        this.hasRoom = true;
        this.textures = new ArrayList<>();
        
    }

    public void start() {
        
    }

    public void addSprite(SpriteRenderer spr) {
        // Get index and add renderObject
        int index = this.numSprites;
        this.sprites[index] = spr;
        this.numSprites++;

        if (spr.getTexture() != null) {
            if (!textures.contains(spr.getTexture())) {
                textures.add(spr.getTexture());
            }
        }
        
        if (numSprites >= this.maxBatchSize) {
            this.hasRoom = false;
        }
    }
    
    public void render(Graphics graphics) {
        
        for (int i=0; i < numSprites; i++) {
            SpriteRenderer sprite = sprites[i];
            //if (sprite.isDirty()) {
                sprite.setClean();
                if(sprite.getText() != null){
                    Vector4i color = null;
                    if( sprite.getColor() != null){
                        color =  sprite.getColor();
                        graphics.setColor(new Color(color.x,color.y,color.z,color.w));
                    }else{
                        graphics.setColor(Color.BLACK);
                    }
                    //Font myFont = new Font ("Courier New", 1, 17);
                    if(sprite.getFont() != null){
                        graphics.setFont(sprite.getFont());
                    }else{
                        Font myFont = new Font ("TimesRoman", 1, 18);
                        graphics.setFont(myFont);
                    }
                    graphics.drawString(sprite.getText(), sprite.gameObject.getPosition().x, sprite.gameObject.getPosition().y);
                }else if(sprite.getline() != null){
                        int r = sprite.getColor().x;
                        int g = sprite.getColor().y;
                        int b = sprite.getColor().z;
                        int a = sprite.getColor().w;
                        graphics.setColor(new Color(r,g,b,a));
                        Vector4i Targetline = sprite.getline();
                        graphics.drawLine(Targetline.x,Targetline.y,Targetline.z,Targetline.w);
                }else{
                    if (sprite.getTexture() != null) {
                        graphics.drawImage(sprite.getImage(), sprite.gameObject.getPosition().x, sprite.gameObject.getPosition().y, null);
                    }else{
                        int r = sprite.getColor().x;
                        int g = sprite.getColor().y;
                        int b = sprite.getColor().z;
                        int a = sprite.getColor().w;
                        if(sprite.outline != null){
                            graphics.setColor( new Color(sprite.outline.x,sprite.outline.y,sprite.outline.z,sprite.outline.w) );
                            graphics.fillRect( sprite.gameObject.getPosition().x-2,sprite.gameObject.getPosition().y-2, sprite.gameObject.getScale().x + 4, sprite.gameObject.getScale().y + 4);
                        }else{
                            if(SpriteRenderer.getLinesBetweenSquares()){
                                graphics.setColor( Color.RED );
                                graphics.fillRect( sprite.gameObject.getPosition().x-1,sprite.gameObject.getPosition().y-1, sprite.gameObject.getScale().x + 2, sprite.gameObject.getScale().y + 2);
                            }
                        }
                        graphics.setColor(new Color(r,g,b,a));
                        graphics.fillRect( sprite.gameObject.getPosition().x,sprite.gameObject.getPosition().y, sprite.gameObject.getScale().x , sprite.gameObject.getScale().y);
                    }
                }
            //}
        }
    }
    
    public boolean hasRoom() {
        return this.hasRoom;
    }

    public boolean hasTextureRoom() {
        return this.textures.size() < 8;
    }

    public boolean hasTexture(Texture tex) {
        return this.textures.contains(tex);
    }
    
     public int zIndex() {
        return this.zIndex;
    }

    @Override
    public int compareTo(RenderBatch o) {
        return Integer.compare(this.zIndex, o.zIndex());
    }

}