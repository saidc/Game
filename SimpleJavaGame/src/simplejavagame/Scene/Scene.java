package simplejavagame.Scene;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import simplejavagame.Game.Window;
import simplejavagame.Game.tools.SortbyLevel;
import simplejavagame.Game.tools.Vector2i;

import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public abstract class Scene implements KeyListener,MouseListener ,MouseMotionListener{
    private List<simplejavagame.Object.Object> Objects = new ArrayList<>();
    public ArrayList<Map<String, String>> actions = new ArrayList<>();
    public abstract void init();
    public abstract void logic();
    public abstract void rootOfCallbacks(String callbackname);
    public abstract void doAction(Map<String, String> acc );
    private boolean isSortingObjects = false;
    private Vector2i mouse = new Vector2i(0,0);
    private Vector2i Previous_mouse = new Vector2i(0,0);
    private boolean mouseMove = false;
    public List<simplejavagame.Object.Object> getObjects(){
        return this.Objects;
    }
    public void update() {
        if(Previous_mouse.X() == mouse.X() && Previous_mouse.Y() == mouse.Y()){
            mouseMove = true;
        }
        logic();
        updateObjects();
        if(!isSortingObjects){
            Render();
        }
        Previous_mouse.setPosition(mouse.X(), mouse.Y());
        mouseMove = false;
    }
    public simplejavagame.Object.Object getObjectByName(String name){
        for (simplejavagame.Object.Object obj : Objects) {
            if(obj.getName() == name){
                return obj;
            }
        }
        return null;
    }
    public void removeObjectByName(String name){
        for (int i = 0; i < this.Objects.size(); i++) {
            if(this.Objects.get(i).getName() == name){
                this.Objects.remove(i);
                break;
            }
        }
    }
    
    public void addObjectToScene(simplejavagame.Object.Object obj) {
        isSortingObjects = true;
        this.Objects.add(obj);
        Collections.sort(this.Objects, new SortbyLevel());
        isSortingObjects = false;
    }
    public void isClicked(Vector2i position){
        for (int i = 0; i < this.Objects.size() ; i++) {
            simplejavagame.Object.Object obj = this.Objects.get(i);
            obj.isClicked(position);
        }
    }
    public void updateObjects(){
        for (int i = 0; i < this.Objects.size() ; i++) {
            simplejavagame.Object.Object obj = this.Objects.get(i);
            obj.update();
            if(this.mouseMove){
                obj.isHover(this.mouse);
            }
        }
    }
    public void Render(){
        Canvas canvas = Window.get().canvas;
        BufferStrategy bs = canvas.getBufferStrategy();
        if( bs == null ){
            canvas.createBufferStrategy(3);
            return;
        }
        Graphics graphics = bs.getDrawGraphics();
        graphics.clearRect(0, 0, Window.getDimension().width,Window.getDimension().height);
        for (int i = 0; i < Objects.size(); i++) {
            Objects.get(i).render(graphics);
        }
        bs.show();
        graphics.dispose();
    }
    
    @Override
    public void mouseMoved(MouseEvent me) {
        this.mouse.setPosition(me.getPoint().x , me.getPoint().y);
        this.mouseMove = true;
    }
    // NO UTILIZADOS 
    @Override
    public void keyPressed(KeyEvent ke) {
    }

    @Override
    public void keyReleased(KeyEvent ke) {
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

    @Override
    public void mouseDragged(MouseEvent me) {
        
    }
}
