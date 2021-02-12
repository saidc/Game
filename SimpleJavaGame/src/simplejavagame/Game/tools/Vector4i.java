
package simplejavagame.Game.tools;

public class Vector4i {
    private int x;
    private int y;
    private int z;
    private int w;
    public Vector4i(int x,int y, int z,int w){
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    public int X(){
        return this.x;
    }
    public int Y(){
        return this.y;
    }
    public int Z(){
        return this.z;
    }
    public int W(){
        return this.w;
    }
    public String toString(){
        return "x:"+this.x+",y:"+this.y+",z:"+this.z+",w:"+this.w;
    }
    public static Vector4i StringToVector4i(String s){
        try {
            String[] n = s.split(",");
            String[] x_String = n[0].split(":");
            String[] y_String = n[1].split(":");
            String[] z_String = n[2].split(":");
            String[] w_String = n[3].split(":");
            int x = Integer.parseInt(x_String[1]);
            int y = Integer.parseInt(y_String[1]);
            int z = Integer.parseInt(z_String[1]);
            int w = Integer.parseInt(w_String[1]);
            return new Vector4i(x,y,z,w);
        } catch (Exception e) {
            return null;
        }
    }
}
