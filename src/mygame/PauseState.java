package mygame;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.controls.ActionListener;

public class PauseState extends AbstractAppState implements ActionListener {

	@Override
	public void initialize(AppStateManager stateManager, Application app) 
	{
	
	}


	@Override
	public void update(float tpf){

	}
	
	public void onAction(String name, boolean isPressed, float tpf) {
			
	}

	@Override
	public void cleanup()
	{
		
	}


}