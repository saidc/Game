
package Jade.Scenes;

import Jade.Scene;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
        
import org.lwjgl.opengl.GL20 ;
import static org.lwjgl.opengl.GL30.* ;
        
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
    private int vertexID, fragmentID, shaderProgram;
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
    
    public LevelEditoScene(){
        System.out.println("you are in LevelEditorScene");
        
    }
    
    @Override
    public void init() {
        /* =================================
            Compile and Link Shaders
           =================================
        */ 
        
        // First load and Compile the vertex shader
        vertexID = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        
        // Pass the shader source to the GPU
        GL20.glShaderSource(vertexID, vertexShaderSrc);
        GL20.glCompileShader(vertexID);
        
        // check for errors in compilation
        int success = GL20.glGetShaderi(vertexID, GL20.GL_COMPILE_STATUS);
        if( success == GL20.GL_FALSE ){
            int len = GL20.glGetShaderi(vertexID, GL20.GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: 'defauldShader.glsl'\n\t Vertex Shader compilation failed.");
            System.out.println(GL20.glGetShaderInfoLog(vertexID, len));
            assert false : "";
            
        }
        
         // First load and Compile the vertex shader
        fragmentID = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        
        // Pass the shader source to the GPU
        GL20.glShaderSource(fragmentID, fragmentShaderSrc);
        GL20.glCompileShader(fragmentID);
        
        // check for errors in compilation
        success = GL20.glGetShaderi(fragmentID, GL20.GL_COMPILE_STATUS);
        if( success == GL20.GL_FALSE ){
            int len = GL20.glGetShaderi(fragmentID, GL20.GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: 'defauldShader.glsl'\n\t Fragment Shader compilation failed.");
            System.out.println(GL20.glGetShaderInfoLog(fragmentID, len));
            assert false : "";
        }
        
        // Link shaders and check for errors.
        shaderProgram = GL20.glCreateProgram();
        GL20.glAttachShader(shaderProgram, vertexID);
        GL20.glAttachShader(shaderProgram, fragmentID);
        GL20.glLinkProgram(shaderProgram);
        
        // Check for Linking errors
        success = GL20.glGetProgrami(shaderProgram, GL20.GL_LINK_STATUS);
        if(success == GL20.GL_FALSE ){
            int len = GL20.glGetProgrami(shaderProgram, GL20.GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: 'defauldShader.glsl'\n\t Vertex Shader compilation failed.");
            System.out.println(GL20.glGetProgramInfoLog(shaderProgram, len));
            assert false : "";
        }
        
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
        // Bind shader program
        glUseProgram(shaderProgram);
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

        glUseProgram(0);
    }
}
