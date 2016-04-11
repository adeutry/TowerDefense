package mygame;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.debug.Grid;
import com.jme3.scene.shape.Box;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class BreakState extends AbstractAppState implements ActionListener, ScreenController {

    Main main;
    private Node ground;
    private Tower computerTower;
    private String newMappings[];

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        this.main = (Main) app;
        main.stateInfoText.setText("state: BreakScreenState\nStart Round: Space");
        if(main.getRootNode().getChild("floorGrid") == null){
            ground = new Node();
            ground.setName("floorGrid");
            attachGrid(Vector3f.ZERO, 50, 50, 1, ColorRGBA.White);
        }
        if(main.getRootNode().getChild("TestTower") == null){
            computerTower = new Tower(main);
            computerTower.setName("computer");
            main.getRootNode().attachChild(computerTower);
        }

        //Keys
        InputManager inputManager = main.getInputManager();
        inputManager.addMapping("StartRound", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(this, newMappings = new String[]{"StartRound"});
        main.getNifty().fromXml("Interface/Menus.xml", "upgrade", this);
// nifty.fromXml("Interface/helloworld.xml", "start", new MySettingsScreen(data));
// attach the Nifty display to the gui view port as a processor
        main.getGuiViewPort().addProcessor(main.getNiftyDisplay());
        main.getFlyByCamera().setDragToRotate(true);
        
        //add the ground
        Box b = new Box(20, 0.2f, 20);
        Geometry g = new Geometry("Ground", b);
        g.move(0, -0.5f, 0);
        Material mat = new Material(main.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Black);
        g.setMaterial(mat);
        main.getRootNode().attachChild(g);
        
    }

    @Override
    public void update(float tpf) {
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        if (name.equals("StartRound") && isPressed) {
            //transition to the round state
            startGame();
        }
    }
        private void attachGrid(Vector3f pos, int length, int width, float unit, ColorRGBA color) {
        Geometry g = new Geometry("grid", new Grid(length, width, unit));
        Material mat = new Material(main.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.getAdditionalRenderState().setWireframe(true);
        mat.setColor("Color", color);
        g.setMaterial(mat);
        g.center().move(pos);
        this.ground.attachChild(g);
        main.getRootNode().attachChild(this.ground);
    }

    public void startGame() {
        //transitiong to the round state
        System.out.println("Beginning game...");
        AppStateManager asm = main.getStateManager();
        RoundState roundState = new RoundState();
        asm.detach(this);
        asm.attach(roundState);
    }
    
    public void getUpgradeSel(String upgrade){
        //used to get the selection made by the gui and move to the placement state
        System.out.println("Selected upgrade: " + upgrade);
        AppStateManager asm = main.getStateManager();
        UpgradePlacementState ups = new UpgradePlacementState(upgrade);
        asm.detach(this);
        asm.attach(ups);
    }

    public void quitGame() {
        //closes game
        System.out.println("quit being called");
        main.stop();
    }

    @Override
    public void cleanup() {
        System.out.println("cleaning up break screen...");
        main.getGuiViewPort().removeProcessor(main.getNiftyDisplay());
        main.deleteInputMappings(newMappings);
    }

    public void bind(Nifty nifty, Screen screen) {
    }

    public void onStartScreen() {
    }

    public void onEndScreen() {
    }
}