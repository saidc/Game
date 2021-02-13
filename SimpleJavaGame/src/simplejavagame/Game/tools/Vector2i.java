
package simplejavagame.Game.tools;

public class Vector2i {
    private int x;
    private int y;
    public Vector2i(int x,int y){
        this.x = x;
        this.y = y;
    }
    public int X(){
        return this.x;
    }
    public int Y(){
        return this.y;
    }
    public int W(){
        return this.x;
    }
    public int H(){
        return this.y;
    }
    public String toString(){
        return "x:"+this.x+",y:"+this.y;
    }
    public void setDimension(int w , int h){
        this.x = w;
        this.y = h;
    }
    public void setPosition(int _x , int _y){
        this.x = _x;
        this.y = _y;
    }
    public boolean equals(Vector2i p){
        return (this.x == p.x && this.y == p.y);
    }
    
    public static Vector2i StringToVector2i(String s){
        try {
            String[] n = s.split(",");
            String[] x_String = n[0].split(":");
            String[] y_String = n[1].split(":");
            int x = Integer.parseInt(x_String[1]);
            int y = Integer.parseInt(y_String[1]);
            return new Vector2i(x,y);
        } catch (Exception e) {
            return null;
        }
    }
    
    public int compare(Vector2i p){
        if(p != null){
            if(      this.x == p.x && this.y == p.y){ return 0;
            }else if(this.x <  p.x && this.y == p.y){ return 1; 
            }else if(this.x <  p.x && this.y >  p.y){ return 2;
            }else if(this.x == p.x && this.y >  p.y){ return 3;
            }else if(this.x >  p.x && this.y >  p.y){ return 4;
            }else if(this.x >  p.x && this.y == p.y){ return 5;
            }else if(this.x >  p.x && this.y <  p.y){ return 6;
            }else if(this.x == p.x && this.y <  p.y){ return 7;
            }else if(this.x <  p.x && this.y <  p.y){ return 8;
            }
        }
        return -1;
    }
}
