package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.jme3.util.SkyFactory;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * test
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    private static Dimension screen;
    BitmapText stateInfoText;
    
    public static void main(String[] args) {
        Main app = new Main();
	initAppScreen(app);
        app.start();
	    System.out.println("Hello!");
    }
    

    @Override
    public void simpleInitApp() {
       
	initMaterials();
	initLights();
	initModels();
	initGui();
        System.out.println(">:C");
	
	StartScreenState startScreen = new StartScreenState();
	stateManager.attach(startScreen);
    }

  private void initMaterials()
  {
	  
  }
  
  private void initLights()
  {
	  
  }
  
  private void initModels()
  {
	//attach skybox
	Spatial sky = SkyFactory.createSky(assetManager, "Textures/Sky/BlueClouds.dds", false);
	rootNode.attachChild(sky);

  }
  
  private void initGui()
  {
	  //create the state info break in the top right
	  BitmapFont bmf = this.getAssetManager().loadFont("Interface/Fonts/ArialBlack.fnt");
          stateInfoText = new BitmapText(bmf);
	  stateInfoText.setSize(bmf.getCharSet().getRenderedSize() * 1f);
          stateInfoText.setColor(ColorRGBA.White);
	  stateInfoText.setText("");
	  stateInfoText.setLocalTranslation(10f, this.getAppSettings().getHeight() - 50, 0f);
	  this.getGuiNode().attachChild(stateInfoText);
  }
  
  public Dimension getScreenDimension()
  {
	  return this.screen;
  }
  
  public AppSettings getAppSettings()
  {
      return this.settings;
  }
  
  private static void initAppScreen(SimpleApplication app)
  {
	//set screen settings 
	AppSettings aps = new AppSettings(true);
        screen = Toolkit.getDefaultToolkit().getScreenSize();
        screen.width *= 0.80;
        screen.height *= 0.75;
        aps.setResolution(screen.width, screen.height);
        app.setSettings(aps);
	
	//get rid of initial jmonkey screen
        app.setShowSettings(false);  
  }
}
