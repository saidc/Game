
package game.Renderer;

import game.Build.Component.SpriteRenderer;

import java.util.List;
import java.util.ArrayList;
import org.joml.Vector2f;
import org.joml.Vector4f;
import game.Renderer.Texture;
import java.awt.Color;
import java.awt.Graphics;
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

        // Add properties to local vertices array
        loadVertexProperties(index);

        if (numSprites >= this.maxBatchSize) {
            this.hasRoom = false;
        }
    }

    public void render(Graphics graphics) {
        boolean rebufferData = false;
        for (int i=0; i < numSprites; i++) {
            SpriteRenderer sprite = sprites[i];
            if (sprite.isDirty()) {
                //loadVertexProperties(i);
                sprite.setClean();
                rebufferData = true;
                if (sprite.getTexture() != null) {
                    graphics.drawImage(sprite.getTexture().getTexture(), sprite.getTexture().getWidth(), sprite.getTexture().getHeight(), null);
                }else{
                    float r = sprite.getColor().x;
                    float g = sprite.getColor().y;
                    float b = sprite.getColor().z;
                    float a = sprite.getColor().w;
                    graphics.setColor(new Color(r,g,b,a));
                    graphics.drawRect((int) sprite.gameObject.getPosition().x, (int)sprite.gameObject.getPosition().x, (int) sprite.gameObject.getScale().x , (int) sprite.gameObject.getScale().y);
                }
            }
        }
        // draw in to the Canvas
        if (rebufferData) {
            
        }
        /*
        for (int i=0; i < textures.size(); i++) {
            textures.get(i).bind();
        }
        
        for (int i=0; i < textures.size(); i++) {
            textures.get(i).unbind();
        }
        */
    }
    
    private void loadVertexProperties(int index) {
        SpriteRenderer sprite = this.sprites[index];

        

        Vector4f color = sprite.getColor();
        Vector2f[] texCoords = sprite.getTexCoords();
        
        int texId = 0;
        if (sprite.getTexture() != null) {
            for (int i = 0; i < textures.size(); i++) {
                if (textures.get(i) == sprite.getTexture()) {
                    texId = i + 1;
                    break;
                }
            }
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