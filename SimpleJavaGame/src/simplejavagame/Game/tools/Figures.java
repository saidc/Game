
package simplejavagame.Game.tools;


import java.awt.Font;
import java.util.HashMap;
import java.util.Map;
import simplejavagame.Object.Type;

import org.w3c.dom.NodeList;  
import org.w3c.dom.Node;  
import java.util.ArrayList;
import simplejavagame.Object.Object;

public class Figures {
    public static simplejavagame.Object.Object getSquare(Vector2i _position,Vector2i _dimension,Vector4i _color,int level){
        Map<String, String> _attributes = new HashMap<>();
        _attributes.put("position", _position.toString());
        _attributes.put("dimension", _dimension.toString());
        _attributes.put("color", _color.toString());
        _attributes.put("level", Integer.toString(level));
        return new simplejavagame.Object.Object(Type.Square , _attributes);
    }
    private static Object getText(String _text, Vector2i _position, Vector4i _color, int level) {
        Map<String, String> _attributes = new HashMap<>();
        _attributes.put("level", Integer.toString(level));
        _attributes.put("position", _position.toString());
        _attributes.put("color", _color.toString());
        _attributes.put("text", _text);
        return new simplejavagame.Object.Object(Type.Text , _attributes);
    }
    public static simplejavagame.Object.Object getLine(Vector2i _position,Vector2i _target,Vector4i _color,int level){
        Map<String, String> _attributes = new HashMap<>();
        _attributes.put("position", _position.toString());
        _attributes.put("target",   _target.toString());
        _attributes.put("color", _color.toString());
        _attributes.put("level", Integer.toString(level));
        return new simplejavagame.Object.Object(Type.Line , _attributes);
    }
    public static ArrayList<simplejavagame.Object.Object> getObjects(String FilePath){
        Node n = FileManage.getSceneNode(FilePath);
        if(n != null){
            NodeList nodeList = n.getChildNodes();
            ArrayList<simplejavagame.Object.Object> objs = new ArrayList<>();
            for (int i = 0; i < nodeList.getLength(); i++) {
                if(nodeList.item(i).getNodeType() == Node.ELEMENT_NODE){
                    simplejavagame.Object.Object obj = getObject(nodeList.item(i));
                    if(obj != null){
                        objs.add(obj);
                    }
                }
            }
            return objs;
        }
        return new ArrayList<simplejavagame.Object.Object>();
    }
    
    
    private static ArrayList<Node> getChildNodes(Node n){
        ArrayList<Node> nodes = new ArrayList<>();
        if(n.hasChildNodes()){
            NodeList ns = n.getChildNodes();
            for (int i = 0; i < ns.getLength(); i++) {
                if(ns.item(i).getNodeType() == Node.ELEMENT_NODE){
                    nodes.add(ns.item(i));
                }
            }
        }
        return nodes;
    }
    private static simplejavagame.Object.Object getObject(Node n ){
        if(n.getNodeName() == "Square"){
            return NodeToSquare(n);
        }else if(n.getNodeName() == "Text"){
            return NodeToText(n);
        }else if(n.getNodeName() == "Line"){
            return NodeToLine(n);
        }
        return null;
    }
    private static simplejavagame.Object.Object NodeToText(Node n){
        int x = 10,y = 10,r = 0,g = 0,b = 0,a = 255 , level = 0; // default
        boolean hasEvent = false, hasFont = false,hasBackground = false,has_hover_background_color = false,hasHover_text_color = false,hasName = false;
        String callbackName = "" , Value = "", name = "TextObj"; // default
        ArrayList<Node> nodes = getChildNodes(n);
        Font font = null;
        Vector4i background = new Vector4i(0,0,0,255) , hover_background_color = new Vector4i(0,0,0,255),hover_text_color = new Vector4i(0,0,0,255); // default
        for (Node node : nodes)  {
            if(node.getNodeName() == "position"){
                Vector2i p = getPosition(node);
                x = p.X();
                y = p.Y();
            }else if(node.getNodeName() == "Value"){
                Value = node.getTextContent();
            }else if(node.getNodeName() == "font"){
                hasFont = true;
                font = getFont(node);
            }else if(node.getNodeName() == "Event"){
                callbackName = getEvent(node);
                if(callbackName != null){
                    hasEvent = true;
                }else{
                    callbackName = ""; // default
                }
            }else if(node.getNodeName() == "hover-background-color"){
                hover_background_color = getColor(node);
                has_hover_background_color = true;
            }else if(node.getNodeName() == "background-color"){
                background = getColor(node);
                hasBackground = true;
            }else if(node.getNodeName() == "hover-text-color"){
                hover_text_color = getColor(node);
                hasHover_text_color = true;
            }else if(node.getNodeName() == "color"){
                Vector4i color = getColor(node);
                r = color.X();
                g = color.Y();
                b = color.Z();
                a = color.W();
            }else if(node.getNodeName() == "Name"){
                hasName = true;
                name = node.getTextContent();
            }else if(node.getNodeName() == "level"){
                level = getLevel(node);
            }
        }
        simplejavagame.Object.Object Text = getText(Value, new Vector2i(x,y), new Vector4i(r,g,b,a), level );
        if(hasEvent){
            Text.setCallbackEvent(callbackName);
        }
        if(hasBackground){
            Text.setBackground(background);
        }
        if(hasFont){
            Text.setFont(font);
        }
        if(has_hover_background_color){
            Text.setHover_background_color(hover_background_color);
        }
        if(hasHover_text_color){
            Text.setHover_text_color(hover_text_color);
        }
        if(hasName){
            //System.out.println("TextName: " + name);
            Text.setName(name);
        }
        return Text;
    }
    private static simplejavagame.Object.Object NodeToSquare(Node n){
        int x = 10,y = 10,w = 10,h = 10,r = 0,g = 0,b = 0,a = 255 , level = 0; // defauld
        boolean hasEvent = false;
        String callbackName = "";
        ArrayList<Node> nodes = getChildNodes(n);
        for (Node node : nodes)  {
            if(node.getNodeName() == "position"){
                Vector2i p = getPosition(node);
                x = p.X();
                y = p.Y();
            }else if(node.getNodeName() == "dimension"){
                Vector2i d = getDimension(node);
                w = d.W();
                h = d.H();
            }else if(node.getNodeName() == "level"){
                level = getLevel(node);
            }else if(node.getNodeName() == "Event"){
                callbackName = getEvent(node);
                if(callbackName != null){
                    hasEvent = true;
                }else{
                    callbackName = ""; // default
                }
            }else if(node.getNodeName() == "color"){
                Vector4i color = getColor(node);
                r = color.X();
                g = color.Y();
                b = color.Z();
                a = color.W();
            }
        }
        simplejavagame.Object.Object square =  getSquare( new Vector2i(x,y), new Vector2i(w,h), new Vector4i(r,g,b,a), level );
        if(hasEvent){
            square.setCallbackEvent(callbackName);
        }
        return square;
    }
    private static simplejavagame.Object.Object NodeToLine(Node n){
        int xl=0,yl=0 , xt=100,yt=100 ,r=0,g=0,b=0,a=255 , level = 0; //default
        ArrayList<Node> nodes = getChildNodes(n);
        for (Node node : nodes)  {
            if(node.getNodeName() == "position"){
                Vector2i p = getPosition(node);
                xl = p.X();
                yl = p.Y();
            }else if(node.getNodeName() == "target"){
                Vector2i d = getPosition(node);
                xt = d.X();
                yt = d.Y();
            }else if(node.getNodeName() == "color"){
                Vector4i color = getColor(node);
                r = color.X();
                g = color.Y();
                b = color.Z();
                a = color.W();
            }else if(node.getNodeName() == "level"){
                level = getLevel(node);
            }
        }
        return getLine(new Vector2i(xl,yl),new Vector2i(xt,yt),new Vector4i(r,g,b,a), level);
    }
    private static Vector2i getPosition(Node node){
        boolean isX = false , isY = false;
        int x = 10, y = 10 ; // defauld
        ArrayList<Node> p = getChildNodes(node);
        for (Node pos : p) {
            if(pos.getNodeName() == "x"){
                if(tools.isNumber(pos.getTextContent())){
                    isX = true;
                    x = Integer.parseInt(pos.getTextContent());
                }
            }else if(pos.getNodeName() == "y"){
                if(tools.isNumber(pos.getTextContent())){
                    isY = true;
                    y = Integer.parseInt(pos.getTextContent());
                }
            }
        }
        return new Vector2i(x,y);
    }
    private static Vector2i getDimension(Node node){
        boolean isW = false , isH = false;
        int w = 10 ,h = 10; // default
        ArrayList<Node> d = getChildNodes(node);
        for (Node dim : d) {
            if(dim.getNodeName() == "w"){
                if(tools.isNumber(dim.getTextContent())){
                    isW = true;
                    w = Integer.parseInt(dim.getTextContent());
                }
            }else if(dim.getNodeName() == "h"){
                if(tools.isNumber(dim.getTextContent())){
                    isH = true;
                    h = Integer.parseInt(dim.getTextContent());
                }
            }
        }
        return new Vector2i(w,h);
    }
    private static int getLevel(Node node) {
        boolean isLevel = false;
        int level = 0 ; // default
        if(tools.isNumber(node.getTextContent())){
            isLevel = true;
            level = Integer.parseInt(node.getTextContent());
        }
        return level;
    }
    private static Vector4i getColor(Node node) {
        boolean isR = false , isG = false , isB = false , isA = false;
        int r = 0,g = 0,b = 0,a = 255; // default
        ArrayList<Node> c = getChildNodes(node);
        for (Node col : c) {
            if(col.getNodeName() == "r"){
                if(tools.isNumber(col.getTextContent())){
                    isR = true;
                    r = Integer.parseInt(col.getTextContent());
                    if(r > 255){
                        r = 255;
                    }
                }
            }else if(col.getNodeName() == "g"){
                if(tools.isNumber(col.getTextContent())){
                    isG = true;
                    g = Integer.parseInt(col.getTextContent());
                    if(g > 255){
                        g = 255;
                    }
                }
            }else if(col.getNodeName() == "b"){
                if(tools.isNumber(col.getTextContent())){
                    isB = true;
                    b = Integer.parseInt(col.getTextContent());
                    if(b > 255){
                        b = 255;
                    }
                }
            }else if(col.getNodeName() == "a"){
                if(tools.isNumber(col.getTextContent())){
                    isA = true;
                    a = Integer.parseInt(col.getTextContent());
                    if(a > 255){
                        a = 255;
                    }
                }
            }
        }
        return new Vector4i(r,g,b,a);
    }
    private static String getEvent(Node node) {
        String callbackName = "";
        boolean hasEvent = false;
        ArrayList<Node> E = getChildNodes(node);
        for (Node evt : E) {
            if(evt.getNodeName() == "IsClicked"){
                ArrayList<Node> C = getChildNodes(evt);
                for (Node call : C) {
                    if(call.getNodeName() == "callback"){
                        hasEvent = true;
                        callbackName = call.getTextContent();
                    }
                }
            }
        }
        if(hasEvent){
            return callbackName;
        }else{
            return null;
        }
    }
    private static Font getFont(Node node) {
        int size = 16; // default
        String family = "TimesRoman"; // defaut
        ArrayList<Node> c = getChildNodes(node);
        for (Node col : c) {
            if(col.getNodeName() == "family"){
                family = col.getTextContent();
            }else if(col.getNodeName() == "size"){
                if(tools.isNumber(col.getTextContent())){
                    size = Integer.parseInt(col.getTextContent());
                }
            }
        }
        return new Font(family, 1, size);
    }
    
}