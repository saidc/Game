
package Jade;

import Jade.EventListener.keysListener;
import Jade.EventListener.MouseListener;
import static Jade.EventListener.MouseListener.get;
import Jade.Scenes.LevelEditoScene;
import Jade.Scenes.LevelScene;

import jdk.nashorn.internal.runtime.Version;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL11C;

public class Window {
    
    private int width , height;
    private String Tittle ;
    private long glfwWindow;
    
     public float r, g, b, a;
    private boolean fadeToBlack = false;
    private static Window window = null;

    private static Scene currentScene;
    
    private Window(){
        /*
        this.width  = 1920;
        this.height = 1080;
        */
        this.width  = 800;
        this.height = 800;
        this.Tittle = "Game";
        r = 0;
        b = 0;
        g = 0;
        a = 1;
    }
    
    
    public static void changeScene(int newScene) {
        switch (newScene) {
            case 0:
                currentScene = new LevelEditoScene();
                
                break;
            case 1:
                currentScene = new LevelScene();
                break;
            default:
                assert false : "Unknown scene '" + newScene + "'";
                break;
        }
        currentScene.load();
        currentScene.init();
        currentScene.start();
    }
    
    public static Window get(){
        if(Window.window == null){
            Window.window = new Window();
        }
        return Window.window;
    }
    
    public static Scene getScene() {
        return get().currentScene;
    }
    
    public void run(){
        System.out.println(Tittle + Version.version());
        init();
        loop();
        
        // free memory 
        Callbacks.glfwFreeCallbacks(glfwWindow);
        GLFW.glfwDestroyWindow(glfwWindow);
        
        // Terminate GLFW the free the error callback
        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
        
        
    }
    
    public void init(){
        // set up an error call back 
        GLFWErrorCallback.createPrint(System.err).set();
        
        // initialize GLFW 
        if(!GLFW.glfwInit()){
            throw new IllegalStateException(" Unable to initialize GLFW");
        }
        // Configure GLFW
        GLFW.glfwDefaultWindowHints(); // default resizeble window or other options. 
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_MAXIMIZED, GLFW.GLFW_TRUE);
        
        //create the window
        glfwWindow = GLFW.glfwCreateWindow(this.width,this.height,this.Tittle,MemoryUtil.NULL,MemoryUtil.NULL);
        if( glfwWindow == MemoryUtil.NULL){
            throw new IllegalStateException("Fail to create the GLFW Window");
        }
        
        // Events Callback
        GLFW.glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);  
        //GLFW.glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        GLFW.glfwSetMouseButtonCallback(glfwWindow, this::mouseButtonCallback );
        GLFW.glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        GLFW.glfwSetKeyCallback(glfwWindow, keysListener::keyCallback);
        
        
        // Make the open GL context current
        GLFW.glfwMakeContextCurrent(glfwWindow);
        
        // Enable v-sync (buffer swapping )
        GLFW.glfwSwapInterval(1);
        
        // make the window Visible
        GLFW.glfwShowWindow(glfwWindow);
        //This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is manage externally.
        // LWJGL detect the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();
       
        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
        
        Window.changeScene(0);// init with the scene 0
    }
    
    public void loop(){
        float endTime, beginTime = (float)GLFW.glfwGetTime();//Time.getTime();
        float dt = -1.0f; // delta time
        //GLFW.glfwMaximizeWindow(glfwWindow);
        
        
        while(! GLFW.glfwWindowShouldClose(glfwWindow)){
            //poll Events
            GLFW.glfwPollEvents();
            
            // Render pass 1. Render to picking texture
            //GL11.glDisable(GL11.GL_BLEND);
            //GL11.pickingTexture.enableWriting();
            
            glClearColor(r,g,b,a);
            glClear(GL_COLOR_BUFFER_BIT  ); // said how to clear the buffer
            
            // calling the actual Scene
            if(dt >= 0){
                currentScene.update(dt);
            }
            
            GLFW.glfwSwapBuffers(glfwWindow);
            
            
            // creating a delta time
            endTime = (float)GLFW.glfwGetTime();
            dt = endTime - beginTime;
            beginTime = endTime;
                    
        }
        this.currentScene.saveExit();
    }
    
    private void mouseButtonCallback (long window, int button , int action , int mods){
        MouseListener.mouseButtonCallback(window,button,action,mods);
        if(action == GLFW.GLFW_PRESS){
            this.currentScene.MousePress(button);
        }else if(action == GLFW.GLFW_RELEASE){
            this.currentScene.MouseRelease(button);
        }
    }
    
}
