
package Jade.Scenes;

import Jade.Scene;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
        
import org.lwjgl.opengl.GL20 ;
import static org.lwjgl.opengl.GL30.* ;
import renderer.Shader;
        
public class LevelEditoScene extends Scene {
    
    private String vertexShaderSrc      = 
            "#version 430 core\n" +
            "layout (location=0) in vec3 aPos;\n" +
            "layout (location=1) in vec4 aColor;\n" +
            "\n" +
            "out vec4 fColor;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    fColor = aColor;\n" +
            "    gl_Position = vec4(aPos, 1.0);\n" +
            "}";
    private String fragmentShaderSrc    = 
            "#version 430 core\n" +
            "\n" +
            "in vec4 fColor;\n" +
            "\n" +
            "out vec4 color;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    color = fColor;\n" +
            "}";
    private int  shaderProgram;
    private float[] vertexArray = {
        // Position             // Color
         0.5f, -0.5f , 0.0f,     1.0f, 0.0f, 0.0f, 1.0f, // Bottom right    0
        -0.5f,  0.5f , 0.0f,     1.0f, 0.0f, 0.0f, 1.0f, // Top left        1
         0.5f,  0.5f , 0.0f,     1.0f, 0.0f, 0.0f, 1.0f, // Top Right       2
        -0.5f, -0.5f , 0.0f,     1.0f, 0.0f, 0.0f, 1.0f, // Bottom Left     3
    };
    
    // IMPORTANT: Must be in counter-Clockwise Order
    private int[] elementArray = {
        2,1,0, // Top Right Triangle
        0,1,3  // Bottom left Triangle      
    };
    
    private int vaoID, vboID, eboID;
    private Shader defaultShader;
    
    public LevelEditoScene(){
        System.out.println("you are in LevelEditorScene");
        
        
    }
    
    @Override
    public void init() {
        this.defaultShader = new Shader("src/main/java/assets/shader/default.glsl");
        this.defaultShader.compile();
        
        
        /*  ===================================================================
         *  Generate VAO, VBO, add EBO Buffer Objects, and send to de GPU
         *  =================================================================== 
         */
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // Create a float buffer of vertices
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();

        // Create VBO upload the vertex buffer
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        // Create the indices and upload
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elementBuffer.put(elementArray).flip();

        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

        // Add the vertex attribute pointers
        int positionsSize = 3;
        int colorSize = 4;
        int floatSizeBytes = 4;
        int vertexSizeBytes = (positionsSize + colorSize) * floatSizeBytes;
        glVertexAttribPointer(0, positionsSize, GL_FLOAT, false, vertexSizeBytes, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, positionsSize * floatSizeBytes);
        glEnableVertexAttribArray(1);
        
        
        
    }
    
    @Override
    public void update(float dt) {
        this.defaultShader.use();
        
        // Bind the VAO that we're using
        glBindVertexArray(vaoID);

        // Enable the vertex attribute pointers
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

        // Unbind everything
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        glBindVertexArray(0);

        this.defaultShader.detach();
    }
}
