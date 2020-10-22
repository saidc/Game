
package game.Scene;

import game.Build.Component.Sprite;
import game.Build.Component.Spritesheet;
import game.Build.Component.SpriteRenderer;
import game.Build.GameObject;
import game.Build.Transform;
import game.Utils.AssetPool;
import org.joml.Vector2f;
import org.joml.Vector4f;


public class Home_Scene extends Scene{
    private Spritesheet sprites;
    
    @Override
    public void init() {
        loadResources(); 
        
        //this.camera = new Camera(new Vector2f(-250, 0));
        
        
        this.sprites = AssetPool.getSpritesheet("assets/images/spritesheet.png");
        
         //obj1 = this.gameObjects.get(0);
        
        GameObject obj1 = new GameObject("Object 1", new Transform(new Vector2f(100, 100), new Vector2f(40, 40)) , -1);
        SpriteRenderer obj1SpriteRenderer = new SpriteRenderer();
        obj1SpriteRenderer.setSprite(sprites.getSprite(24));    // sprite
        obj1.addComponent(obj1SpriteRenderer);
        this.addGameObjectToScene(obj1);
        
        GameObject obj2 = new GameObject("Object 2", new Transform(new Vector2f(250, 100), new Vector2f(100, 100)),0);
        Sprite obj2sprite = new Sprite();
        obj2sprite.setTexture(AssetPool.getTexture("assets/images/blendImage2.png")); // image 2
        SpriteRenderer obj2SpriteRender = new SpriteRenderer();
        obj2SpriteRender.setSprite(obj2sprite);
        obj2.addComponent(obj2SpriteRender);
        this.addGameObjectToScene(obj2);
        
        GameObject obj3 = new GameObject("Object 3", new Transform(new Vector2f(400, 100), new Vector2f(100, 100)),-2);
        
        SpriteRenderer obj3SpriteRender = new SpriteRenderer();
        obj3SpriteRender.setColor(new Vector4f(1.0f,1.0f,0.0f,1.0f));
        obj3.addComponent(obj3SpriteRender); // just a color 
        this.addGameObjectToScene(obj3);
        
        
        
    }
    
    @Override
    public void update(float dt) {
        /*
        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }
        */
        this.renderer.render();
    }
    
     private void loadResources() {
        
        // TODO: FIX TEXTURE SAVE SYSTEM TO USE PATH INSTEAD OF ID
        AssetPool.addSpritesheet("assets/images/spritesheet.png",
                new Spritesheet(AssetPool.getTexture("assets/images/spritesheet.png"),
                        16, 16, 26, 0));
        AssetPool.getTexture("assets/images/blendImage2.png");
    }
}
