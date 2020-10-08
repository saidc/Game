
package Jade;

import jdk.nashorn.internal.runtime.Version;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Window {
    private int width , height;
    private String Tittle ;
    private static Window window = null;
    long glfwWindow;
    
    private Window(){
        this.width  = 1920;
        this.height = 1080;
        this.Tittle = "Game";
        
    }
    public static Window get(){
        if(Window.window == null){
            Window.window = new Window();
        }
        return Window.window;
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
       
        
    }
    public void loop(){
        while(! GLFW.glfwWindowShouldClose(glfwWindow)){
            //poll Events
            GLFW.glfwPollEvents();
            
            GL11.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT); // said how to clear the buffer
            GLFW.glfwSwapBuffers(glfwWindow);
            
        }
    }
    
}
