
package game.Renderer;

import game.Build.Component.SpriteRenderer;
import game.Build.GameObject;
import game.Build.Window;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Renderer {
    private final int MAX_BATCH_SIZE = 1000;
   
    private List<RenderBatch> batches;
    private int width, height;
    public Renderer() {
        this.batches = new ArrayList<>();
        
        
    }
    
    public void add(GameObject go) {
        SpriteRenderer spr = go.getComponent(SpriteRenderer.class);
        if (spr != null) {
            add(spr);
        }
    }

    private void add(SpriteRenderer sprite) {
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
    }
        
    public void render() {
        Canvas canvas = Window.get().canvas;
        
        BufferStrategy bs = canvas.getBufferStrategy();
        
        if( bs == null ){
            canvas.createBufferStrategy(3);
            //bs = this.canvas.getBufferStrategy();
            return;
        }
        
        Graphics graphics = bs.getDrawGraphics();
        graphics.clearRect(0, 0, Window.get().dimension.width,Window.get().dimension.height);
        
        for (int i = 0; i < batches.size(); i++) {
            batches.get(i).render(graphics);
        }
        
        bs.show();
        graphics.dispose();
    }
    
    public void setDimension(int width, int height){
        this.width = width;
        this.height = height;
    }
    
}
