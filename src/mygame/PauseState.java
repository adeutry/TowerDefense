package mygame;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;

public class PauseState extends AbstractAppState implements ActionListener {

    Main main;
    private String newMappings[];

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        this.main = (Main) app;
        main.stateInfoText.setText("state: Game Paused\nResume Game: P");

        //Keys
        InputManager inputManager = main.getInputManager();
        inputManager.addMapping("Unpause", new KeyTrigger(KeyInput.KEY_P));
        inputManager.addListener(this, newMappings = new String[]{"Unpause"});
    }

    @Override
    public void update(float tpf) {
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        //Pausing game during round
        if (name.equals("Unpause") && isPressed) {
            System.out.println("Unpaused!");
            //transition to initial break screen
            AppStateManager asm = main.getStateManager();
            RoundState roundState = new RoundState();
            asm.detach(this);
            asm.attach(roundState);
        }
    }

    @Override
    public void cleanup() {
        main.deleteInputMappings(newMappings);
    }
}