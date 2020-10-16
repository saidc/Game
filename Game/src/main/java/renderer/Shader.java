
package renderer;

import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import org.lwjgl.opengl.GL20;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;

public class Shader {

    private int shaderProgramID;

    private String vertexSource;
    private String fragmentSource;
    private String filepath;

    public Shader(String filepath) {
        this.filepath = filepath;
        try {
            String source = new String(Files.readAllBytes(Paths.get(this.filepath)));
            String[] splitString = source.split("(#type)( )+([a-zA-Z]+)");

            // Find the first pattern after #type 'pattern'
            int index = source.indexOf("#type") + 6;
            int eol = source.indexOf("\n", index);
            String firstPattern = source.substring(index, eol).trim();

            // Find the second pattern after #type 'pattern'
            index = source.indexOf("#type", eol) + 6;
            eol = source.indexOf("\n", index);
            String secondPattern = source.substring(index, eol).trim();

            if (firstPattern.equals("vertex")) {
                vertexSource = splitString[1];
            } else if (firstPattern.equals("fragment")) {
                fragmentSource = splitString[1];
            } else {
                throw new Exception("Unexpected token '" + firstPattern + "'");
            }

            if (secondPattern.equals("vertex")) {
                vertexSource = splitString[2];
            } else if (secondPattern.equals("fragment")) {
                fragmentSource = splitString[2];
            } else {
                throw new Exception("Unexpected token '" + secondPattern + "'");
            }
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Error: Could not open file for shader: '" + filepath + "' :" + e.getMessage());
            assert false : "Error: Could not open file for shader: '" + filepath + "'";
        }
        //System.out.println(vertexSource);
        //System.out.println(fragmentSource);
    }

    public void compile() {
        /* =================================
            Compile and Link Shaders
           =================================
        */ 
        
        int vertexID, fragmentID;
        
        // First load and Compile the vertex shader
        vertexID = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        
        // Pass the shader source to the GPU
        GL20.glShaderSource(vertexID, vertexSource);
        GL20.glCompileShader(vertexID);
        
        // check for errors in compilation
        int success = GL20.glGetShaderi(vertexID, GL20.GL_COMPILE_STATUS);
        if( success == GL20.GL_FALSE ){
            int len = GL20.glGetShaderi(vertexID, GL20.GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: '"+this.filepath+"'\n\t Vertex Shader compilation failed.");
            System.out.println(GL20.glGetShaderInfoLog(vertexID, len));
            assert false : "";
            
        }
        
         // First load and Compile the vertex shader
        fragmentID = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        
        // Pass the shader source to the GPU
        GL20.glShaderSource(fragmentID, fragmentSource);
        GL20.glCompileShader(fragmentID);
        
        // check for errors in compilation
        success = GL20.glGetShaderi(fragmentID, GL20.GL_COMPILE_STATUS);
        if( success == GL20.GL_FALSE ){
            int len = GL20.glGetShaderi(fragmentID, GL20.GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: '"+this.filepath+"'\n\t Fragment Shader compilation failed.");
            System.out.println(GL20.glGetShaderInfoLog(fragmentID, len));
            assert false : "";
        }
        
        // Link shaders and check for errors.
        shaderProgramID = GL20.glCreateProgram();
        GL20.glAttachShader(shaderProgramID, vertexID);
        GL20.glAttachShader(shaderProgramID, fragmentID);
        GL20.glLinkProgram(shaderProgramID);
        
        // Check for Linking errors
        success = GL20.glGetProgrami(shaderProgramID, GL20.GL_LINK_STATUS);
        if(success == GL20.GL_FALSE ){
            int len = GL20.glGetProgrami(shaderProgramID, GL20.GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: '"+this.filepath+"'\n\t Vertex Shader compilation failed.");
            System.out.println(GL20.glGetProgramInfoLog(shaderProgramID, len));
            assert false : "";
        }
        
    }

    public void use() {
        // Bind shader program
        glUseProgram(shaderProgramID);
    }

    public void detach() {
        glUseProgram(0);
    }
    
    public void uploadMat4f(String varName, Matrix4f mat4){
        int varLocation = glGetUniformLocation(this.shaderProgramID, varName);
        FloatBuffer matBuffer = BufferUtils.createFloatBuffer(16);
        mat4.get(matBuffer);
        glUniformMatrix4fv(varLocation, false, matBuffer);
        
    }
}
