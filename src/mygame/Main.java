package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.InputManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.jme3.util.SkyFactory;
import de.lessvoid.nifty.Nifty;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.LinkedList;
import java.util.ArrayList;

/**
 * test
 *
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    private static Dimension screen;
    BitmapText stateInfoText;
    private NiftyJmeDisplay niftyDisplay;
    private Nifty nifty;
		public ArrayList<Tower> towers;

    public static void main(String[] args) {
        Main app = new Main();
        initAppScreen(app);
        app.start();
    }

    @Override
    public void simpleInitApp() {

        initMaterials();
        initLights();
        initModels();
        initGui();

        StartScreenState startScreen = new StartScreenState();
        stateManager.attach(startScreen);
    }

    private void initMaterials() {
    }

    private void initLights() {
    }

    private void initModels() {
				
				//initialize towers array
				towers = new ArrayList<Tower>();
				
        //attach skybox
        Spatial sky = SkyFactory.createSky(assetManager, "Textures/Sky/BlueClouds.dds", false);
        rootNode.attachChild(sky);

    }

    private void initGui() {
        //create the state info break in the top right
        BitmapFont bmf = this.getAssetManager().loadFont("Interface/Fonts/ArialBlack.fnt");
        stateInfoText = new BitmapText(bmf);
        stateInfoText.setSize(bmf.getCharSet().getRenderedSize() * 1f);
        stateInfoText.setColor(ColorRGBA.White);
        stateInfoText.setText("");
        stateInfoText.setLocalTranslation(10f, this.getAppSettings().getHeight() - 50, 0f);
        this.getGuiNode().attachChild(stateInfoText);

        niftyDisplay = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort);
        nifty = niftyDisplay.getNifty();
    }

    public Dimension getScreenDimension() {
        return this.screen;
    }

    public AppSettings getAppSettings() {
        return this.settings;
    }

    public Nifty getNifty() {
        return this.nifty;
    }

    public NiftyJmeDisplay getNiftyDisplay() {
        return this.niftyDisplay;
    }

    public InputManager getInputManager() {
        return this.inputManager;
    }

    private static void initAppScreen(SimpleApplication app) {
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

    public void deleteInputMappings(String mappings[]) {
        for (String i : mappings) {
            inputManager.deleteMapping(i);
        }
    }

    public boolean hasObject(LinkedList<?> sp, Class cls) {
        for (Object s : sp) {
            if (s.getClass().equals(cls)) {
                return true;
            }
        }
        return false;
    }
}
