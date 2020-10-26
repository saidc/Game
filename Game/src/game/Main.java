
package game;

import game.Build.Window;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.joml.Random;
import org.joml.Vector2i;
//import java.util.function.Function;


public class Main {
    
    private static List<List<Integer>> GeneratePlainMap(){
        List<List<Integer>> map = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            List<Integer> line = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                line.add(0);
            }
            map.add(line);
        }
        return map;
    }
    private static void ShowMap(List<List<Integer>> map){
        for (List<Integer> line : map) {
            for (Integer integer : line) {
                System.out.print(integer + " ");
            }
            System.out.println("");
        }
        System.out.println("");
    }
    private static void addMountainAndHillInPos(Vector2i pos , List<List<Integer>> map){
        int hill     = 1 ;
        int Mountain = 2 ;
        
        for (int i = -2; i <= 3; i++) {
            List<Integer> line = map.get(pos.y+i);
            for (int j = -2; j <= 3; j++) {
                if((i >= 0 && i < 2)&& (j >= 0 && j < 2)){
                    line.set(pos.x+j, Mountain);
                }else{
                    line.set(pos.x+j, hill);
                }
            }
        }
    }
    private static Vector2i getRandomMountainPosition(){
        Random r1 = new Random();
        Random r2 = new Random();
        int low1  = 2;
        int high1 = 100-3;
        int low2  = 2;
        int high2 = 100-3;
        return new Vector2i(r1.nextInt(high1-low1) + low1 , r2.nextInt(high2-low2) + low2) ;
    }
    public static void main(String[] args) {
        
        
        /*
        List<List<Integer>> map = GeneratePlainMap();
        ShowMap(map);
        List<Integer> line = map.get(0);
        line.set(4,5);
        ShowMap(map);
        addMountainAndHillInPos(new Vector2i(5,3),map);
        ShowMap(map);
        */
//        for (int i = 0; i < 100; i++) {
//            Vector2i pos = getRandomMountainPosition();
//            System.out.println("x: "+pos.x+" ,y: "+pos.y);
//            
//        }
        
        Window window = Window.get();
        window.run();
        
    }
    
    
}

