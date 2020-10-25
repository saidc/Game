
package game.Renderer;

import game.Build.Component.SpriteRenderer;
import game.Build.GameObject;
import game.Build.Window;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Renderer {
    private final int MAX_BATCH_SIZE = 1000;
   
    private List<RenderBatch> batches;
    private int width, height;
    public Renderer() {
        this.batches = new ArrayList<>();
        
        // ****
        try {
            this.image = ImageIO.read(new File("assets/images/mapTile_136.png"));//C:\Users\said-\Documents\NetBeansProjects\move_image\assets\image\mapTile_136.png
        } catch (IOException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        
    public Image image ;
    public int x=100,y=100;
    public int count= 0 , increase=0 ;
    
    public void render() {
        Canvas canvas = Window.get().canvas;
        
        BufferStrategy bs = canvas.getBufferStrategy();
        
        if( bs == null ){
            canvas.createBufferStrategy(3);
            //bs = this.canvas.getBufferStrategy();
            return;
        }
        
//        if(count++ == 60){
//            count = 0;
//            if((increase+=10) > 50){
//                increase = 0;     
//            }
//            //System.out.println(" increase "+increase);
//        }
        
        Graphics graphics = bs.getDrawGraphics();
        graphics.clearRect(0, 0, Window.get().dimension.width,Window.get().dimension.height);
//        graphics.drawImage(image,x+increase,y+increase, null);
//                
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
