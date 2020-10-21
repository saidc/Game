
package game.Renderer;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class Texture {
    private String filepath;
    private int texID;
    private int width, height;
    
//    public Texture(String filepath) {
//
//    }

    public void init(String filepath) {
        this.filepath = filepath;

        // Generate texture 

        // Set texture parameters
        
        IntBuffer width = null  ;//= BufferUtils.createIntBuffer(1);
        IntBuffer height = null ;//= BufferUtils.createIntBuffer(1);
        
        ByteBuffer image = null ;//= stbi_load(filepath, width, height, channels, 0);

        if (image != null) {
            this.width = width.get(0);
            this.height = height.get(0);
            /*
            if (channels.get(0) == 3) {
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width.get(0), height.get(0),
                        0, GL_RGB, GL_UNSIGNED_BYTE, image);
            } else if (channels.get(0) == 4) {
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(0), height.get(0),
                        0, GL_RGBA, GL_UNSIGNED_BYTE, image);
            } else {
                assert false : "Error: (Texture) Unknown number of channesl '" + channels.get(0) + "'";
            }
            */
        } else {
            assert false : "Error: (Texture) Could not load image '" + filepath + "'";
        }

        //stbi_image_free(image);
    }

    public void bind() {
        
    }

    public void unbind() {
        
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
