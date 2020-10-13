
package renderer;

import java.nio.file.Files;
import java.nio.file.Paths;

//import java.nio.file.Path;
public class Shader {
    
    private int shaderProgramID;
    private String   vertexSource;
    private String fragmentSource;
    private String filePath;
    
    public Shader(String filePath){
        /*
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current relative path is: " + s);
        */
        this.filePath = filePath;
        try {
            String source = new String(Files.readAllBytes(Paths.get(filePath)));
            String[] splitString = source.split("(#type)( )+[a-zA-Z]+");
            
            // Find the first pattern after #type 'pattern'
            int index = source.indexOf("#type") + 6;
            int eol = source.indexOf("\r\n", index);
            String firstPattern = source.substring(index, eol).trim();
            
            // Find the second pattern after #type 'pattern'
            index = source.indexOf("#type", eol) + 6 ;
            eol = source.indexOf("\r\n", index);
            String secondPattern = source.substring(index, eol).trim();
            
            if(firstPattern.equals("vertex")){
                this.vertexSource = splitString[1];
            }else if(firstPattern.equals("fragment")){
                this.fragmentSource = splitString[1];
            }else{
                throw new Exception("Unexpected token '"+ firstPattern+"'");
            }
            
            if(secondPattern.equals("vertex")){
                this.vertexSource = splitString[1];
            }else if(secondPattern.equals("fragment")){
                this.fragmentSource = splitString[1];
            }else{
                throw new Exception("Unexpected token '"+ secondPattern+"'");
            }
        } catch (Exception e) {
            e.printStackTrace();
            assert false: "error could not open file for shader: '"+filePath+"'";
        }
        
        System.out.println(this.vertexSource);
        System.out.println(this.fragmentSource);
    }
    
    public void compile(){
        
    }
    
    public void use(){
        
    }
    
    public void detach(){
        
    }
}
