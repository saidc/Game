
package Jade.Scenes;

import Jade.Camera;

import Jade.Scene;
import Jade.EventListener.keysListener;
import Jade.GameObject;
import Util.AssetPool;
import Util.Transform;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import components.Sprite;
import components.SpriteRender;
import components.Spritesheet;

import renderer.Texture;

import org.joml.Vector2f;
import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.*;

        
public class LevelEditoScene extends Scene {
    
    private GameObject obj1;
    private Spritesheet sprites;
    
    public LevelEditoScene() {

    }

    @Override
    public void init() {
        loadResources();
        this.camera = new Camera(new Vector2f(-250, 0));
        
        
        this.sprites = AssetPool.getSpritesheet("src/main/java/assets/images/tilemap.png");
        
        obj1 = new GameObject("Object 1", new Transform(new Vector2f(100, 100), new Vector2f(40, 40)) , -1);
        SpriteRender obj1SpriteRenderer = new SpriteRender();
        obj1SpriteRenderer.setSprite(sprites.getSprite(24));    // sprite
        obj1.addComponent(obj1SpriteRenderer);
        this.addGameObjectToScene(obj1);
        
        GameObject obj2 = new GameObject("Object 2", new Transform(new Vector2f(250, 100), new Vector2f(100, 100)),0);
        Sprite obj2sprite = new Sprite();
        obj2sprite.setTexture(AssetPool.getTexture("src/main/java/assets/images/testImage2.png")); // image 2
        SpriteRender obj2SpriteRender = new SpriteRender();
        obj2SpriteRender.setSprite(obj2sprite);
        obj2.addComponent(obj2SpriteRender);
        this.addGameObjectToScene(obj2);
        
        GameObject obj3 = new GameObject("Object 3", new Transform(new Vector2f(400, 100), new Vector2f(100, 100)),-2);
        
        SpriteRender obj3SpriteRender = new SpriteRender();
        obj3SpriteRender.setColor(new Vector4f(1.0f,0.0f,0.0f,1.0f));
        obj3.addComponent(obj3SpriteRender); // just a color 
        this.addGameObjectToScene(obj3);
        
        GameObject obj4 = new GameObject("Object 4", new Transform(new Vector2f(550, 100), new Vector2f(100, 100)),0);
        Sprite obj4Sprite = new Sprite();
        obj4Sprite.setTexture(AssetPool.getTexture("src/main/java/assets/images/testImage.png"));
        SpriteRender obj4SpriteRender = new SpriteRender();
        obj4SpriteRender.setSprite(obj4Sprite);
        obj4.addComponent(obj4SpriteRender); // image 1
        this.addGameObjectToScene(obj4);
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        System.out.println(gson.toJson("hello world"));
    }
    
    private int sw_init = 0;
    private void InitGameObjects(){
        if(this.sw_init < 2){
            
            for (int i = 0; i < this.gameObjects.size(); i++) {
                if(this.sw_init == 0){
                    this.gameObjects.get(i).move(new Vector2f(1));
                }
                if(this.sw_init == 1){
                    this.gameObjects.get(i).move(new Vector2f(-1));
                }
            }
            
            this.sw_init += 1;
        }
    }
    
    private void loadResources() {
        AssetPool.getShader("src/main/java/assets/shader/default.glsl");
        AssetPool.addSpritesheet("src/main/java/assets/images/tilemap.png",
                new Spritesheet(AssetPool.getTexture("src/main/java/assets/images/tilemap.png"),
                        16, 16, 486, 1));
    }
    
    private int spriteIndex = 0;
    private float spriteFlipTime = 0.2f;
    private float spriteFlipTimeLeft = 0.0f;
    private float contador = 0.0f;
    private int conteo = 0;
    
    
    @Override
    public void update(float dt) {
        InitGameObjects();
        // Events
        
        int dir = 4;
        
        if (keysListener.isKeyPress(GLFW_KEY_RIGHT)) {
            //camera.addPosition(new Vector2f( 100f * dt,0.0f));
            this.gameObjects.get(0).move(new Vector2f( 100f * dt,0.0f));
            dir = 3;
        } else if (keysListener.isKeyPress(GLFW_KEY_LEFT)) {
            //camera.addPosition(new Vector2f(-100f * dt,0.0f));
            this.gameObjects.get(0).move(new Vector2f(-100f * dt,0.0f));
            dir = 0;
        }
        if (keysListener.isKeyPress(GLFW_KEY_UP)) {
            //camera.addPosition(new Vector2f(0.0f, 100f * dt));
            this.gameObjects.get(0).move(new Vector2f(0.0f,  100f * dt));
            dir = 2;
        } else if (keysListener.isKeyPress(GLFW_KEY_DOWN)) {
            //camera.addPosition(new Vector2f(0.0f,-100f * dt));
            this.gameObjects.get(0).move(new Vector2f(0.0f, -100f * dt));
            dir = 1;
        }
        
       
        
        spriteFlipTimeLeft -= dt;
        if (spriteFlipTimeLeft <= 0) {
            spriteFlipTimeLeft = spriteFlipTime;
            
            switch(spriteIndex){
                case 0:
                    obj1.getComponent(SpriteRender.class).setSprite(sprites.getSprite(23+dir));
                    break;
                case 1:
                    obj1.getComponent(SpriteRender.class).setSprite(sprites.getSprite(50+dir));
                    break;
                case 2:
                    obj1.getComponent(SpriteRender.class).setSprite(sprites.getSprite(77+dir));
                    break;
                case 3:
                    spriteIndex = 0;
            }
            if(!(dir >= 0 && dir < 4 )){
                obj1.getComponent(SpriteRender.class).setSprite(sprites.getSprite(24));
            }
            
            spriteIndex++;
        }
        
        // update gameObjects information
        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }
        
        // draw images to the window
        this.renderer.render();
        
        
    }

    @Override
    public void MouseRelease(int button) {
        
    }
    @Override
    public void MousePress(int button) {
        
    }
}
