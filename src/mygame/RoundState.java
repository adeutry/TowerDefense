package mygame;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;

public class RoundState extends AbstractAppState implements ActionListener {

	Main main;
				
	@Override
	public void initialize(AppStateManager stateManager, Application app) 
	{
				this.main = (Main)app;
				main.stateInfoText.setText("state: RoundState\nPause Game: P\nEnd Screen: Space");
				
				//Keys
				InputManager inputManager = main.getInputManager();
				inputManager.clearMappings();
				inputManager.addMapping("Pause", new KeyTrigger(KeyInput.KEY_P));
				inputManager.addMapping("End", new KeyTrigger(KeyInput.KEY_SPACE));
				inputManager.addListener(this, "Pause","End");	
	}


	@Override
	public void update(float tpf){

	}
	
	public void onAction(String name, boolean isPressed, float tpf) {
		
	  //Pausing game during round
		if(name.equals("Pause") && isPressed)
		{
				System.out.println("Paused!");
				//transition to initial break screen
				AppStateManager asm = main.getStateManager();
				PauseState breakState = new PauseState();
				asm.detach(this);
				asm.attach(breakState);
		}else if(name.equals("End") && isPressed)
		{
				//transition to initial break screen
				AppStateManager asm = main.getStateManager();
				EndScreenState endScreenState = new EndScreenState();
				asm.detach(this);
				asm.attach(endScreenState);
		}
	}

	@Override
	public void cleanup()
	{
		
	}


}