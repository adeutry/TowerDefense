package mygame;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.controls.ActionListener;

public class BreakState extends AbstractAppState implements ActionListener {

	Main main;
	
	@Override
	public void initialize(AppStateManager stateManager, Application app) 
	{
		this.main = (Main)app;
		main.stateInfoText.setText("state: BreakScreenState");
		System.out.println("Hello");
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