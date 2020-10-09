
package Util;

public class Time {
    public static float timeStarted = System.nanoTime();
    
    public static float getTime(){
        return (float)((System.nanoTime() - Time.timeStarted)* 1E-9);
    }
}
