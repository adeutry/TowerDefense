package mygame;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;

public class BreakState extends AbstractAppState implements ActionListener {

	Main main;
	
	@Override
	public void initialize(AppStateManager stateManager, Application app) 
	{
		this.main = (Main)app;
		main.stateInfoText.setText("state: BreakScreenState\nStart Round: Space");	
		
		//Keys
		InputManager inputManager = main.getInputManager();
		//inputManager.clearMappings();
		inputManager.addMapping("StartRound", new KeyTrigger(KeyInput.KEY_SPACE));
		inputManager.addListener(this, "StartRound");	
	}


	@Override
	public void update(float tpf){

	}
	
	public void onAction(String name, boolean isPressed, float tpf) {
		if(name.equals("StartRound") && isPressed)
		{
			//transition to initial break screen
			AppStateManager asm = main.getStateManager();
			RoundState roundState = new RoundState();
			asm.detach(this);
			asm.attach(roundState);
		}
	}

	@Override
	public void cleanup()
	{
		
	}


}