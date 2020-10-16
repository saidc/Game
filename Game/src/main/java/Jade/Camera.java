
package Jade;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Camera {
    private Matrix4f projectionMatrix ,viewMatrix;
    private Vector2f position;
    
    public Camera(Vector2f _position){
        this.position = _position;
        this.projectionMatrix = new Matrix4f();
        this.viewMatrix = new Matrix4f();
        adjustProjection();
    }
    public void addPosition (Vector2f addP){
        this.position.x += addP.x ;
        this.position.y += addP.y ;
    }
    public void adjustProjection (){
        this.projectionMatrix.identity();
        this.projectionMatrix.ortho(0.0f, 32.0f * 40.0f, 0.0f, 32.0f*21.0f, 0.0f, 100.0f);
        
    }
    public Matrix4f getViewMatrix(){
        Vector3f cameraFront    = new Vector3f(0.0f,0.0f,-1.0f);
        Vector3f cameraUp       = new Vector3f(0.0f,1.0f, 0.0f);
        this.viewMatrix.identity();
        this.viewMatrix = this.viewMatrix.lookAt(new Vector3f(this.position.x, this.position.y, 20.0f),
                                                 cameraFront.add(this.position.x, this.position.y, 0.0f),
                                                 cameraUp);
        return this.viewMatrix;
    }
    public Matrix4f getProjectionMatrix(){
        return this.projectionMatrix;
    }
    
}
