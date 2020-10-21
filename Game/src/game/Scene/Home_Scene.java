
package game.Scene;

import game.Build.Component.Spritesheet;
import game.Build.GameObject;
import game.Utils.AssetPool;


public class Home_Scene extends Scene{
    
    @Override
    public void init() {
        loadResources();
        
        
    }
    
    @Override
    public void update(float dt) {
        
        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }

        this.renderer.render();
    }
    
     private void loadResources() {
        
        // TODO: FIX TEXTURE SAVE SYSTEM TO USE PATH INSTEAD OF ID
        AssetPool.addSpritesheet("assets/images/spritesheet.png",
                new Spritesheet(AssetPool.getTexture("assets/images/spritesheet.png"),
                        16, 16, 26, 0));
        AssetPool.getTexture("assets/images/blendImage2.png");
    }
}
