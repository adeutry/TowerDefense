package mygame;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;

public class EndScreenState extends AbstractAppState implements ActionListener {

    Main main;
    private String newMappings[];

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        this.main = (Main) app;
        main.stateInfoText.setText("state: EndScreenState\nMain Menu: Space");

        //Keys
        InputManager inputManager = main.getInputManager();
        inputManager.addMapping("Menu", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(this, newMappings = new String[]{"Menu"});
    }

    @Override
    public void update(float tpf) {
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        //Pausing game during round
        if (name.equals("Menu") && isPressed) {
            //transition to initial break screen
            AppStateManager asm = main.getStateManager();
            StartScreenState startScreenState = new StartScreenState();
            asm.detach(this);
            asm.attach(startScreenState);
        }
    }

    @Override
    public void cleanup() {
        main.deleteInputMappings(newMappings);
    }
}