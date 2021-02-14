
package simplejavagame.Object;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import simplejavagame.Game.tools.Vector2i;
import simplejavagame.Game.tools.Vector4i;
import simplejavagame.Game.tools.tools;

public class Object {
    
    private int type    ;
    private int level   ;
    private String name = "";
    private boolean hasName = true;
    // Square
        private Vector2i position       ;   
        private Vector2i dimension      = null;   
        private Vector4i color          ;
        private boolean hasLineBetweenSquares = false ;
        private Vector4i LineBetweenSquares_color          ;
        private boolean LineBetweenSquares_Visible = false;
    private boolean hasCallbackEvent    ;
    private String CallbackName         ;
    private Consumer<String> RootOfCallback = null;
    private boolean isVisible            ;
    private ArrayList<Map<String, String>> actions ;
    // Text 
        private String  Text = "";
        private boolean hasFont = false;
        private Font    font =  new Font(null, 1, 16); // default
        private boolean HasUpdateFont = false;
        private Vector4i background_color ;
        private boolean hasBackground = false;
        private Vector4i hover_background_color ;
        private boolean hasHover_background_color = false;
        private boolean isHover_background_color = false;
        private Vector4i hover_text_color ;
        private boolean hasHover_text_color = false;
        private boolean isHover_text_color = false;
    // Line
        private Vector2i target       ;   
// PUBLIC
    public Object( int _type, Map<String, String> _attributes){
        this.type = _type;
        this.isVisible = true;
        this.actions = new ArrayList<>();
        initObject(_attributes);
    }
    public void update(){
        int actions_length = this.actions.size();
        for (int i = 0; i < actions_length; i++) {
            doAction(this.actions.get(i));
            this.actions.remove(this.actions.get(i));
        }
    }
    public void doAction(Map<String, String> acc){
        String action = acc.get("action-type");
        int action_type = -1;
        try {
            action_type = Integer.parseInt(action);
        } catch (Exception e) {
            action_type = -1;
            System.out.println("error to get the acction-type");
        }
        switch(action_type){
            case Type.IsClicked:
                if(this.isVisible && this.hasCallbackEvent){
                    String pos_Stirng = acc.get("position");
                    Vector2i position = Vector2i.StringToVector2i(pos_Stirng);
                    if(isInside(position)){
                        RootOfCallback.accept(this.CallbackName);
                    }
                }
                break;
            case Type.HoverEvent:
                break;
        }
    }
    public void render(Graphics graphics){
        if(this.isVisible){
            if(this.type == Type.Square){
                if(this.LineBetweenSquares_Visible && this.hasLineBetweenSquares){
                    graphics.setColor( Color.RED );
                    graphics.fillRect( this.position.X()-1,this.position.Y()-1,this.dimension.W()+ 2, this.dimension.H() + 2);
                }
                graphics.setColor( tools.NewColor(this.color) );
                graphics.fillRect(this.position.X(),this.position.Y(),this.dimension.W(),this.dimension.H());
            }else if (this.type == Type.Text){
                if(this.hasFont){
                    graphics.setFont(this.font);
                }
                if(this.dimension == null || this.HasUpdateFont){
                    this.HasUpdateFont = false;
                    //setActualDimension();
                    AffineTransform affinetransform = new AffineTransform();     
                    FontRenderContext frc = new FontRenderContext(affinetransform,true,true);  
                    int height = (int)(this.font.getStringBounds(this.Text, frc).getHeight());
                    int width = graphics.getFontMetrics().stringWidth(this.Text);
                    this.dimension = new Vector2i(width, height);
                }
                if(this.hasBackground){
                    if(hasHover_background_color && this.isHover_background_color){
                        graphics.setColor(tools.NewColor(this.hover_background_color));
                    }else{
                        graphics.setColor(tools.NewColor(this.background_color));
                    }
                    graphics.fillRect(this.position.X()-(this.dimension.W()/2),this.position.Y()-(this.dimension.H()),this.dimension.W(),this.dimension.H());
                }
                if(hasHover_text_color && this.isHover_text_color){
                    graphics.setColor(tools.NewColor(this.hover_text_color));
                }else{
                    graphics.setColor(tools.NewColor(this.color));
                }
                graphics.drawString(this.Text, this.position.X()-(this.dimension.W()/2), this.position.Y()-(this.dimension.H()/4));
            }else if (this.type == Type.Line){
                graphics.setColor(tools.NewColor(this.color));
                graphics.drawLine(this.position.X(), this.position.Y(), this.target.X(), this.target.Y());

            }
        }
    }
    public void isClicked(Vector2i position){
        Map<String, String> acc = new HashMap<>();
        acc.put("action-type", Integer.toString(Type.IsClicked));
        acc.put("position", position.toString());
        this.actions.add(acc);
    }
    public void addRootOfCallbacks(Consumer<String> root){
        this.RootOfCallback = root;
    }
    public String resume(){
        if(this.type == Type.Square){
            return   "{"
                    +"'position':"
                        +"{'x':'"+this.position.X()+"','y':'"+this.position.Y()+"'},"
                    +"'dimension':"
                        +"{'x':'"+this.dimension.X()+"','y':'"+this.dimension.Y()+"'},"
                    +"'color':"
                        +"{'x':'"+this.color.X()+"','y':'"+this.color.Y()+"','z':'"+this.color.Z()+"','w':'"+this.color.W()+"'},"
                    +"}";
        }
        return "null";
    }
    public void RemoveCallbackEvent(){
        this.CallbackName = "";
        this.hasCallbackEvent = false;
    }
    public boolean hasLineBetweenSquares (){
        return this.hasLineBetweenSquares;
    }
    public void SwitchLineBetweenSquares_Visible(){
        this.LineBetweenSquares_Visible = !this.LineBetweenSquares_Visible;
    }
    public void isHover(Vector2i point) {
        if(isInside(point)){
            isHover_text_color = true;
            isHover_background_color = true;
        }else{
            isHover_text_color = false;
            isHover_background_color = false;
        }
    }
    // Set
    public void setVisible(){
        this.isVisible = true;
    }
    public void setHide(){
        this.isVisible = false;
    }
    public void setBackground(Vector4i color){
        this.hasBackground = true;
        this.background_color = color;
    }
    public void setHover_background_color(Vector4i color){
        this.hasHover_background_color = true;
        this.hover_background_color = color;
    }
    public void setHover_text_color(Vector4i color){
        this.hasHover_text_color = true;
        this.hover_text_color = color;
    }
    public void setCallbackEvent(String CallbackName){
        this.hasCallbackEvent = true;
        this.CallbackName = CallbackName;
    }
    public void setFont(Font _font){
        this.hasFont = true;
        this.font    =  _font;
        this.HasUpdateFont = true;
        //setActualDimension();
    }
    public void setText(String _text){
        this.Text = _text;
        this.HasUpdateFont = true;
        //setActualDimension();
    }
    public void setName(String _name){
        this.hasName = true;
        this.name = _name;
    }
    public void setLineBetweenSquares_color(Vector4i color){
        this.LineBetweenSquares_color = color;
        this.hasLineBetweenSquares = true;
    }
    public void SetLineBetweenSquares_Hide(){
        this.LineBetweenSquares_Visible = false;
    }
    public void SetLineBetweenSquares_Visible(){
        this.LineBetweenSquares_Visible = true;
    }
    public void SetColor(Vector4i color){
        this.color = color;
    }
    public void setPosition(int x , int y){
        this.position.setPosition(x, y);
    }
    public void setPosition(Vector2i _position){
        this.position.setPosition(_position.X(), _position.Y());
    }
    
    // get
    public int getLevel(){
        return this.level;
    }
    public String getName(){
        return this.name;
    }
    public Vector2i getPosition(){
        return this.position;
    }
    public Vector4i getColor(){
        return this.color;
    }
    public Vector2i getDimension(){
        return this.dimension;
    }
    
// PRIVATE
    private boolean isInside(Vector2i point){
        if(this.type == Type.Square){
            return (     (point.X() > this.position.X() && point.Y() > this.position.Y()) &&
                (point.X() < this.position.X() + this.dimension.W() && point.Y() < this.position.Y() + this.dimension.H())
            );
        }else if(this.type == Type.Text){
            try{
                return (
                           (point.X() > this.position.X()-(this.dimension.W()/2) 
                        &&  point.Y() > this.position.Y()-(this.dimension.H()) 
                           ) 
                        && (point.X() < this.position.X()+(this.dimension.W()/2) 
                        &&  point.Y() < this.position.Y()
                           )
                       );
            }catch(Exception e){
                return false;
            }
        }
        return false;
    }
    private void initObject(Map<String, String> attr){
        if(this.type == Type.Square){
            try {
                this.position     = Vector2i.StringToVector2i(attr.get("position"))    ;
            } catch (Exception e) {
                this.position     = new Vector2i(0 ,0)         ;
            }
            try {
                this.dimension    = Vector2i.StringToVector2i(attr.get("dimension"))   ;
            } catch (Exception e) {
                this.dimension    = new Vector2i(10,10)        ;
            }
            try {
                this.color        = Vector4i.StringToVector4i(attr.get("color"))       ;
            } catch (Exception e) {
                this.color        = new Vector4i(0 ,0  ,0,255) ;
            }
            try {
                this.level        = Integer.parseInt(attr.get("level"))                ;
            } catch (Exception e) {
                this.level        = 0                          ;
            }
        }else if (this.type == Type.Text){
            try {
                this.position     = Vector2i.StringToVector2i(attr.get("position"))    ;
            } catch (Exception e) {
                this.position     = new Vector2i(0 ,0)         ;
            }
            try {
                this.color        = Vector4i.StringToVector4i(attr.get("color"))       ;
            } catch (Exception e) {
                this.color        = new Vector4i(0 ,0  ,0,255) ;
            }
            try {
                this.level        = Integer.parseInt(attr.get("level"))                ;
            } catch (Exception e) {
                this.level        = 0                          ;
            }
            try {
                this.Text        = attr.get("text")            ;
            } catch (Exception e) {
                this.Text        = ""                          ;
            }
        }else if (this.type == Type.Line){
            try {
                this.position     = Vector2i.StringToVector2i(attr.get("position"))    ;
            } catch (Exception e) {
                this.position     = new Vector2i(0 ,0)         ;
            }
            try {
                this.target    = Vector2i.StringToVector2i(attr.get("target"))   ;
            } catch (Exception e) {
                this.target    = new Vector2i(10,10)        ;
            }
            try {
                this.color        = Vector4i.StringToVector4i(attr.get("color"))       ;
            } catch (Exception e) {
                this.color        = new Vector4i(0 ,0  ,0,255) ;
            }
            try {
                this.level        = Integer.parseInt(attr.get("level"))                ;
            } catch (Exception e) {
                this.level        = 0                          ;
            }
        }
    }

}
