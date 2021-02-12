package simplejavagame.Game.tools;


import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import simplejavagame.Game.tools.Figures;


public class FileManage {
    public static Node getSceneNode(String FilePath){
        try {
            //creating a constructor of file class and parsing an XML file  
            File file = new File(FilePath);  
            //an instance of factory that gives a document builder  
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
            //an instance of builder to parse the specified xml file  
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            if(doc.hasChildNodes()){
                NodeList NodeList = doc.getChildNodes();  
                if(NodeList.getLength() == 1){
                    Node n = NodeList.item(0);
                    if(n.getNodeName() == "Scene"){
                        return n;
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            System.out.println("error to get Scene");
        }
        return null;
    }
    public static String localPath(){
        Path currentRelativePath = Paths.get("");
        return currentRelativePath.toAbsolutePath().toString();
        //return System.getProperty("user.dir") ;
    }
}
