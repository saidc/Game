
package components;

import Jade.GameObject;


public abstract class Component {
    public transient GameObject gameObject = null;

    public void start() {

    }

    public abstract void update(float dt);
}
