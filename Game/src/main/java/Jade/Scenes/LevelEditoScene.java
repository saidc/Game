
package Jade.Scenes;

import Jade.Camera;
import Jade.Scene;
import Jade.EventListener.keysListener;
import Jade.GameObject;
import Util.AssetPool;
import Util.Transform;
import components.SpriteRender;


import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.*;
        
public class LevelEditoScene extends Scene {
    
    
    public LevelEditoScene() {

    }

    @Override
    public void init() {
        this.camera = new Camera(new Vector2f(-250, 0));

        GameObject obj1 = new GameObject("Object 1", new Transform(new Vector2f(100, 100), new Vector2f(256, 256)));
        obj1.addComponent(new SpriteRender(AssetPool.getTexture("src/main/java/assets/images/testImage.png")));
        this.addGameObjectToScene(obj1);

        GameObject obj2 = new GameObject("Object 2", new Transform(new Vector2f(400, 100), new Vector2f(256, 256)));
        obj2.addComponent(new SpriteRender(AssetPool.getTexture("src/main/java/assets/images/testImage2.png")));
        this.addGameObjectToScene(obj2);
        
        GameObject obj3 = new GameObject("Object 3", new Transform(new Vector2f(800, 100), new Vector2f(256, 256)));
        obj3.addComponent(new SpriteRender(AssetPool.getTexture("src/main/java/assets/images/testImage3.png")));
        this.addGameObjectToScene(obj3);

        loadResources();
    }

    private void loadResources() {
        AssetPool.getShader("src/main/java/assets/shader/default.glsl");
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
