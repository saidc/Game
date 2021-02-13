
package game.Scene;

import game.Build.Component.Sprite;
import game.Build.Component.SpriteRenderer;
import game.Build.Component.Spritesheet;
import game.Build.GameObject;
import game.Build.Transform;
import game.Utils.AssetPool;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import org.joml.Vector2i;
import org.joml.Vector4f;
import org.joml.Vector4i;


public class Temporal extends Scene{
    private Spritesheet sprites;
    private GameObject obj1;
    private void loadResources() {
        
        // TODO: FIX TEXTURE SAVE SYSTEM TO USE PATH INSTEAD OF ID
        AssetPool.addSpritesheet("assets/images/spritesheet.png",
                new Spritesheet(AssetPool.getTexture("assets/images/spritesheet.png"),
                        16, 16, 27,18, 1));
        AssetPool.getTexture("assets/images/blendImage2.png");
    }
    
    @Override
    public void update(long time) {
        for (GameObject go : this.getGameObjectList()) {
            go.update(time);
        }
        
        this.renderer.render();
    }
    
    @Override
    public void init() {
        loadResources();
        //this.camera = new Camera(new Vector2i(-250, 0));
        
        
        this.sprites = AssetPool.getSpritesheet("assets/images/spritesheet.png");
        
         //obj1 = this.gameObjects.get(0);
        
        this.obj1 = new GameObject("Object 1", new Transform(new Vector2i(100, 100), new Vector2i(40, 40)) , -1);
        SpriteRenderer obj1SpriteRenderer = new SpriteRenderer();
        obj1SpriteRenderer.setSprite(sprites.getSprite(24));    // sprite
        obj1.addComponent(obj1SpriteRenderer);
        this.addGameObjectToScene(obj1);
        
        GameObject obj2 = new GameObject("Object 2", new Transform(new Vector2i(250, 100), new Vector2i(100, 100)),0);
        Sprite obj2sprite = new Sprite();
        obj2sprite.setTexture(AssetPool.getTexture("assets/images/blendImage2.png")); // image 2
        obj2sprite.SetAsSingleImage();
        SpriteRenderer obj2SpriteRender = new SpriteRenderer();
        obj2SpriteRender.setSprite(obj2sprite);
        obj2.addComponent(obj2SpriteRender);
        this.addGameObjectToScene(obj2);
        
        GameObject obj3 = new GameObject("Object 3", new Transform(new Vector2i(400, 100), new Vector2i(10, 10)),-2);
        SpriteRenderer obj3SpriteRender = new SpriteRenderer();
        obj3SpriteRender.setColor(new Vector4i(1,1,0,1));
        obj3.addComponent(obj3SpriteRender); // just a color 
        this.addGameObjectToScene(obj3);
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        System.out.println("size:  "+this.getGameObjectList().size());
        System.out.println("X:"+this.getGameObjectList().get(0).transform.position.x+" , Y: "+this.getGameObjectList().get(0).transform.position.y);
        this.obj1.transform.position.x = me.getX();
        this.obj1.transform.position.y = me.getY();
        System.out.println("X:"+this.getGameObjectList().get(0).transform.position.x+" , Y: "+this.getGameObjectList().get(0).transform.position.y);
        System.out.println("mouseClicked");
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
        
    }
    
}
