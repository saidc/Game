
package components;

import Jade.Component;

import java.awt.font.FontRenderContext;

public class FontRenderer extends Component{
    
    @Override
    public void start() {
        if (gameObject.getComponent(SpriteRender.class) != null) {
            System.out.println("Found Font Renderer!");
        }
    }

    @Override
    public void update(float dt) {

    }
}
