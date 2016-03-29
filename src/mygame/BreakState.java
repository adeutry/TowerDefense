package mygame;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.niftygui.NiftyJmeDisplay;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class BreakState extends AbstractAppState implements ActionListener, ScreenController {

    Main main;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        this.main = (Main) app;
        main.stateInfoText.setText("state: BreakScreenState\nStart Round: Space");

        //Keys
        InputManager inputManager = main.getInputManager();
        inputManager.clearMappings();
        inputManager.addMapping("StartRound", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(this, "StartRound");
        main.getNifty().fromXml("Interface/Menus.xml", "upgrade", this);
// nifty.fromXml("Interface/helloworld.xml", "start", new MySettingsScreen(data));
// attach the Nifty display to the gui view port as a processor
        main.getGuiViewPort().addProcessor(main.getNiftyDisplay());
        main.getFlyByCamera().setDragToRotate(true);
    }

    @Override
    public void update(float tpf) {
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        if (name.equals("StartRound") && isPressed) {
            //transition to initial break screen
            AppStateManager asm = main.getStateManager();
            RoundState roundState = new RoundState();
            asm.detach(this);
            asm.attach(roundState);
        }
    }

    public void startGame() {
        System.out.println("Beginning game...");
        AppStateManager asm = main.getStateManager();
        RoundState roundState = new RoundState();
        asm.detach(this);
        asm.attach(roundState);
    }

    public void quitGame() {
        System.out.println("quit being called");
        main.stop();
    }

    @Override
    public void cleanup() {
        System.out.println("cleaning up break screen...");
        main.getGuiViewPort().removeProcessor(main.getNiftyDisplay());
    }

    public void bind(Nifty nifty, Screen screen) {
    }

    public void onStartScreen() {
    }

    public void onEndScreen() {
    }
}