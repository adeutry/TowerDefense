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
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.debug.Grid;
import java.util.LinkedList;

public class UpgradePlacementState extends AbstractAppState implements ActionListener {

    Main main;
    private String newMappings[];
    private String upgrade;
    protected Tower tower;
    private boolean canPlace;

    public UpgradePlacementState(String upgrade) {
        this.upgrade = upgrade;
        canPlace = true;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        this.main = (Main) app;
        main.stateInfoText.setText("state: UpgradePlacementState\nConfirm placement: Space");
        makeUpgrade();

        //Keys
        InputManager inputManager = main.getInputManager();
        inputManager.deleteMapping("StartRound");
        inputManager.addMapping("Confirm", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("Cancel", new KeyTrigger(KeyInput.KEY_BACK));
        inputManager.addMapping("Quit", new KeyTrigger(KeyInput.KEY_ESCAPE));
        inputManager.addMapping("Forward", new KeyTrigger(KeyInput.KEY_UP));
        inputManager.addMapping("Backward", new KeyTrigger(KeyInput.KEY_DOWN));
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addListener(this, newMappings = new String[]{"Confirm", "Quit",
            "Cancel", "Forward", "Backward", "Left", "Right"});
    }

    @Override
    public void update(float tpf) {
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        if (name.equals("Confirm") && isPressed) {
            //returns back to the upgrade screen
            confirm();
        }
        if (name.equals("Cancel") && isPressed) {
            //returns back to the upgrade screen
            cancel();
        }
        if (name.equals("Quit") && isPressed) {
            //exits out of game
            quitGame();
        }
        if (name.equals("Forward") && isPressed) {
            //Places tower 1 space forward
            tower.move(new Vector3f(0, 0, -1));
            if (main.hasObject(tower.getControl(CollisionControl.class).getCollisions(), Tower.class)) {
                canPlace = false;
                System.out.println("Can't place item here");
            } else {
                canPlace = true;
            }
        }
        if (name.equals("Backward") && isPressed) {
            //Places tower 1 space backward
            tower.move(new Vector3f(0, 0, 1));
            if (main.hasObject(tower.getControl(CollisionControl.class).getCollisions(), Tower.class)) {
                canPlace = false;
                System.out.println("Can't place item here");
            } else {
                canPlace = true;
            }

        }
        if (name.equals("Left") && isPressed) {
            //Places tower 1 space left
            tower.move(new Vector3f(-1, 0, 0));
            if (main.hasObject(tower.getControl(CollisionControl.class).getCollisions(), Tower.class)) {
                canPlace = false;
                System.out.println("Can't place item here");
            } else {
                canPlace = true;
            }
        }
        if (name.equals("Right") && isPressed) {
            //Places tower 1 space right
            tower.move(new Vector3f(1, 0, 0));
            if (main.hasObject(tower.getControl(CollisionControl.class).getCollisions(), Tower.class)) {
                canPlace = false;
                System.out.println("Can't place item here");
            } else {
                canPlace = true;
            }
        }
    }

    public void confirm() {
        if (!canPlace) {
            System.out.println("Can't place tower here. Please move it");
        } else {
            System.out.println("Upgrade placed. Returning to upgrade screen...");
            AppStateManager asm = main.getStateManager();
            BreakState bs = new BreakState();
            asm.detach(this);
            asm.attach(bs);
        }
    }

    public void cancel() {
        System.out.println("Cancel upgrade placement. Returning to upgrade screen...");
        main.getRootNode().detachChild(tower);
        AppStateManager asm = main.getStateManager();
        BreakState bs = new BreakState();
        asm.detach(this);
        asm.attach(bs);
    }

    public void quitGame() {
        System.out.println("quit being called");
        main.stop();
    }

    private void makeUpgrade() {
        ColorRGBA colors[] = {ColorRGBA.White, ColorRGBA.Yellow, ColorRGBA.Pink,
            ColorRGBA.Green, ColorRGBA.Cyan, ColorRGBA.Orange, ColorRGBA.Red,
            ColorRGBA.Gray, ColorRGBA.Magenta};
        int upType = Integer.parseInt(this.upgrade.substring(3));
        Tower newTower = new Tower(main);
        Geometry towGeom = (Geometry) newTower.getChild(0);
        towGeom.getMaterial().setColor("Color", colors[upType - 1]);
        newTower.move(new Vector3f(-2, 0, 0));
        tower = newTower;
        tower.setName(upgrade);
        tower.addControl(new CollisionControl(main, tower));
        main.getRootNode().attachChild(newTower);
    }

    @Override
    public void cleanup() {
        System.out.println("cleaning up upgrade placement screen...");
        main.deleteInputMappings(newMappings);
    }
}