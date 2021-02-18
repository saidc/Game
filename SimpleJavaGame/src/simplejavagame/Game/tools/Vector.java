
package simplejavagame.Game.tools;

public class Vector {
    private Vector2i Point1 = null;
    private Vector2i Point2 = null;
    private float x , y;
    
    public Vector(Vector2i _Point1 , Vector2i _Point2){
        this.Point1 = _Point1;
        this.Point2 = _Point2;
        this.x = this.Point2.X() - this.Point1.X();
        this.y = this.Point2.Y() - this.Point1.Y();
    }
    public float getY(int x){
        try{
            return (getM()*(x-this.Point1.X()))+this.Point1.Y();
        }catch(Exception e){
            return 0;
        }
    }
    private float getM(){
        return this.y/this.x;
    }
    public static int compare(float x1,float y1 , float x2,float y2){
        System.out.println("y1: "+y1+" , y2: "+y2);
        if(      x1 == x2 && y1 == y2){ return 0;
        }else if(x1 <  x2 && y1 == y2){ return 1; 
        }else if(x1 <  x2 && y1 >  y2){ return 2;
        }else if(x1 == x2 && y1 >  y2){ return 3;
        }else if(x1 >  x2 && y1 >  y2){ return 4;
        }else if(x1 >  x2 && y1 == y2){ return 5;
        }else if(x1 >  x2 && y1 <  y2){ return 6;
        }else if(x1 == x2 && y1 <  y2){ return 7;
        }else if(x1 <  x2 && y1 <  y2){ return 8;
        }
        return -1;
    }
    
}
