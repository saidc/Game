
package Jade.Scenes;

import Jade.Camera;
import Jade.Scene;
import Jade.EventListener.keysListener;
import Jade.GameObject;
import Util.AssetPool;
import Util.Transform;
import components.Sprite;
import components.SpriteRender;
import components.Spritesheet;


import org.joml.Vector2f;
import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.*;
        
public class LevelEditoScene extends Scene {
    
    
    public LevelEditoScene() {

    }

    @Override
    public void init() {
        loadResources();
        this.camera = new Camera(new Vector2f(-250, 0));
        
        
        Spritesheet sprites = AssetPool.getSpritesheet("src/main/java/assets/images/tilemap.png");
        
        GameObject obj1 = new GameObject("Object 1", new Transform(new Vector2f(100, 100), new Vector2f(100, 100)));
        obj1.addComponent(new SpriteRender(new Sprite(AssetPool.getTexture("src/main/java/assets/images/testImage.png")))); // image 1
        this.addGameObjectToScene(obj1);

        GameObject obj2 = new GameObject("Object 2", new Transform(new Vector2f(250, 100), new Vector2f(100, 100)));
        obj2.addComponent(new SpriteRender(new Sprite(AssetPool.getTexture("src/main/java/assets/images/testImage2.png")))); // image 2
        this.addGameObjectToScene(obj2);
        
        GameObject obj3 = new GameObject("Object 3", new Transform(new Vector2f(400, 100), new Vector2f(100, 100)));
        obj3.addComponent(new SpriteRender(new Vector4f(1.0f,0.0f,0.0f,1.0f))); // just a color 
        this.addGameObjectToScene(obj3);
        
            
        GameObject obj4 = new GameObject("Object 4", new Transform(new Vector2f(550, 100), new Vector2f(100, 100)));
        obj4.addComponent(new SpriteRender(sprites.getSprite(24))); // sprite 
        this.addGameObjectToScene(obj4);
            
        loadResources();
    }

    private void loadResources() {
        AssetPool.getShader("src/main/java/assets/shader/default.glsl");
        AssetPool.addSpritesheet("src/main/java/assets/images/tilemap.png",
                new Spritesheet(AssetPool.getTexture("src/main/java/assets/images/tilemap.png"),
                        16, 16, 486, 1));
    }
    
    @Override
    public void update(float dt) {
        // Events
        if (keysListener.isKeyPress(GLFW_KEY_RIGHT)) {
            camera.addPosition(new Vector2f( 100f * dt,0.0f));
            //this.gameObjects.get(1).move(new Vector2f( 1000f * dt,0.0f));
            //System.out.println("right");
        } else if (keysListener.isKeyPress(GLFW_KEY_LEFT)) {
            camera.addPosition(new Vector2f(-100f * dt,0.0f));
            //this.gameObjects.get(1).move(new Vector2f(-100f * dt,0.0f));
        }
        if (keysListener.isKeyPress(GLFW_KEY_UP)) {
            camera.addPosition(new Vector2f(0.0f, 100f * dt));
            //this.gameObjects.get(1).move(new Vector2f(0.0f,  100f * dt));
        } else if (keysListener.isKeyPress(GLFW_KEY_DOWN)) {
            camera.addPosition(new Vector2f(0.0f,-100f * dt));
            //this.gameObjects.get(1).move(new Vector2f(0.0f, -100f * dt));
        }
        
        // update gameObjects information
        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }
        
        // draw images to the window
        this.renderer.render();
    }
}
