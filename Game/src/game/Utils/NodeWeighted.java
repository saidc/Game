
package game.Utils;

import game.Build.GameObject;
import java.util.LinkedList;


/**
 *
 * @author saidc
 */
public class NodeWeighted {
    // The int n and String name are just arbitrary attributes
    // we've chosen for our nodes these attributes can of course
    // be whatever you need
    private String name;
    private GameObject go;
    private boolean visited;
    
    LinkedList<EdgeWeighted> edges;

    public NodeWeighted(String name) {
        
        this.name = name;
        visited = false;
        edges = new LinkedList<>();
    }
    public NodeWeighted(GameObject go) {
        this.go = go;
        this.name = go.getname();
        visited = false;
        edges = new LinkedList<>();
    }

    boolean isVisited() {
        return visited;
    }
    public String getName(){
        return this.name;
    }
    public GameObject getGmObj(){
        return this.go;
    }
    void visit() {
        visited = true;
    }

    void unvisit() {
        visited = false;
    }
}