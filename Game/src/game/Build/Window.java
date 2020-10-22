
package game.Build;
import game.Scene.Game_Scene;
import game.Scene.Home_Scene;
import game.Scene.Scene;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;

public class Window implements Runnable,KeyListener,MouseListener,WindowListener{
    private static Window window = null;
    
    private int width, height;
    private String title;
    private int WindowID;

    public float r, g, b, a;
    
    private JFrame frame ;
    private Canvas canvas;
    
    private boolean running = true;
    
    private static Scene currentScene;
    
    public static Window get(){
        if (Window.window == null) {
            Window.window = new Window();
        }
        return Window.window;
    }
    private Window() {
        this.width = 1000;
        this.height = 800;
        this.title = "Game";
        r = 0;
        b = 0;
        g = 0;
        a = 1;
        this.WindowID = this.hashCode();
    }
    
    @Override
    public void run() {
        init();
        loop();
    }
    
    public void init() {
        frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension( width,height));
        canvas.setMaximumSize(new Dimension( width,height));
        canvas.setMinimumSize(new Dimension( width,height));
        canvas.setFocusable(false);
        
        canvas.setBackground(new Color(r,g,b,a)); //Red
        
        frame.add(canvas);
        frame.pack();
        
        
        // Callback Events
        frame.addKeyListener(this);
        frame.addMouseListener(this);
        frame.addWindowListener(this);
        // show window
        frame.setVisible(true);
        
        this.changeScene(0);
    }

    public void loop() {
        
        float beginTime = (float)System.currentTimeMillis();
        float endTime;
        float dt = -1.0f;

        while(this.running) {
            
            //glClearColor(r, g, b, a);
            
            if (dt >= 0) {
                currentScene.update(dt);
            }

            
            endTime = (float)System.currentTimeMillis();
            dt = endTime - beginTime;
            beginTime = endTime;
        }
        
        //currentScene.saveExit();
        System.out.println("Game Over");
    }
    public void changeScene(int newScene) {
        switch (newScene) {
            case 0:
                currentScene = new Home_Scene();
                break;
            case 1:
                currentScene = new Game_Scene();
                break;
            default:
                assert false : "Unknown scene '" + newScene + "'";
                break;
        }

        currentScene.load();
        currentScene.init();
        currentScene.start(canvas, window.width,window.height);
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
    public void windowOpened(WindowEvent we) {
        System.out.println("windowOpened");
    }

    @Override
    public void windowClosing(WindowEvent we) {
        this.running = false;
        System.out.println("windowClosing");
    }

    @Override
    public void windowClosed(WindowEvent we) {
        
        System.out.println("windowClosed");
    }

    @Override
    public void windowIconified(WindowEvent we) {
        System.out.println("windowIconified");
    }

    @Override
    public void windowDeiconified(WindowEvent we) {
        System.out.println("windowDeiconified");
    }

    @Override
    public void windowActivated(WindowEvent we) {
        System.out.println("windowActivated");
    }

    @Override
    public void windowDeactivated(WindowEvent we) {
        System.out.println("windowDeactivated");
    }
}
