
package game.Renderer;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Texture {
    private String filepath;
    private int texID;
    private int width, height;
    private BufferedImage image = null; 
    
    public void init(String filepath) {
        this.filepath = filepath;

        // Set texture parameters
        try {
            BufferedImage image = ImageIO.read(new File(filepath));
            if (image != null) {
                this.width  = image.getHeight(null);
                this.height = image.getHeight(null);
                this.image = image;
            } else {
                assert false : "Error: (Texture) Could not load image '" + filepath + "'";
            }
        } catch (IOException ex) {
            Logger.getLogger(Texture.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
    public boolean hasTexture(){
        return (image != null);
    }
    public String getfilePath (){
        return this.filepath;
    }
    public Image getSubImage(int upper_left_X,int upper_left_Y , int Width , int Height){
        return this.image.getSubimage(upper_left_X, upper_left_Y, Width, Height);
    }
    public Image getImage() {
        return this.image;
    }
}
