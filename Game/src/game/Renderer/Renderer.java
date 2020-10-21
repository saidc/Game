
package game.Renderer;

import game.Build.Component.SpriteRenderer;
import game.Build.GameObject;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Renderer {
    private final int MAX_BATCH_SIZE = 1000;
    private Canvas canvas;
    //private List<RenderBatch> batches;
    private int width, height;
    public Renderer() {
        //this.batches = new ArrayList<>();
    }

    public void add(GameObject go) {
        SpriteRenderer spr = go.getComponent(SpriteRenderer.class);
        if (spr != null) {
            add(spr);
        }
    }

    private void add(SpriteRenderer sprite) {
        /*
        boolean added = false;
        for (RenderBatch batch : batches) {
            if (batch.hasRoom() && batch.zIndex() == sprite.gameObject.zIndex()) {
                Texture tex = sprite.getTexture();
                if (tex == null || (batch.hasTexture(tex) || batch.hasTextureRoom())) {
                    batch.addSprite(sprite);
                    added = true;
                    break;
                }
            }
        }

        if (!added) {
            RenderBatch newBatch = new RenderBatch(MAX_BATCH_SIZE, sprite.gameObject.zIndex());
            newBatch.start();
            batches.add(newBatch);
            newBatch.addSprite(sprite);
            Collections.sort(batches);
        }
        */
    }

    public void render() {
        BufferStrategy bs = this.canvas.getBufferStrategy();
        if( bs == null ){
            this.canvas.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.yellow);
            //g.clearRect(0, 0, width, height);
        g.fillRect(100,100,500,500);
        
        /*
        for (RenderBatch batch : batches) {
        batch.render();
        }
         */
        bs.show();
        g.dispose();
    }

    public void setCanvas (Canvas canvas) {
        this.canvas = canvas;
    }
    
    public void setDimension(int width, int height){
        this.width = width;
        this.height = height;
    }
    
}
