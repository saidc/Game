
package game.Scene;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import game.Build.Component.Component;
import game.Build.GameObject;
import game.Build.serializer.*;
import game.Renderer.Renderer;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public abstract class Scene implements KeyListener,MouseListener{
    private List<GameObject> gameObjects = new ArrayList<>();
    public abstract void update(long time);
    private boolean isRunning = false;
    protected Renderer renderer = new Renderer();
    protected boolean levelLoaded = false;
    
    public void addGameObjectToScene(GameObject go) {
        if (!isRunning) {
            gameObjects.add(go);
        } else {
            gameObjects.add(go);
            go.start();
            this.renderer.add(go);
        }
    }
    public void addGameObjectToScene(List<GameObject> gos) {
        if (!isRunning) {
            gameObjects.addAll(gos);
        } else {
            gameObjects.addAll(gos);
            for (GameObject go : gos) {
                go.start();
                this.renderer.add(go);
            }
        }
    }
    
    public List<GameObject> getGameObjectList(){
        return this.gameObjects;
    }
    
    public abstract void init();
    
    public void start( int width, int height) {
        
        this.renderer.setDimension(width, height);
        
        for (GameObject go : gameObjects) {
            go.start();
            this.renderer.add(go);
        }
        isRunning = true;
    }
    
    public void load() {
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current relative path is: " + s);
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Component.class, new ComponentDeserializer())
                .registerTypeAdapter(GameObject.class, new GameObjectDeserializer())
                .create();

        String inFile = "";
        try {
            inFile = new String(Files.readAllBytes(Paths.get("assets/files/level.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!inFile.equals("")) {
            GameObject[] objs = gson.fromJson(inFile, GameObject[].class);
            for (int i=0; i < objs.length; i++) {
                addGameObjectToScene(objs[i]);
            }
            this.levelLoaded = true;
        }
    }
    public void saveExit() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Component.class, new ComponentDeserializer())
                .registerTypeAdapter(GameObject.class, new GameObjectDeserializer())
                .create();

        try {
            FileWriter writer = new FileWriter("level.txt");
            writer.write(gson.toJson(this.gameObjects));
            writer.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
}
