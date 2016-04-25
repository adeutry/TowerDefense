package mygame;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.ui.Picture;

public class StartScreenState extends AbstractAppState implements ActionListener {

	private Picture logoPic;
        private String newMappings[];
	Main main;
	
	@Override
	public void initialize(AppStateManager stateManager, Application app) 
	{
		this.main = (Main)app;
		main.stateInfoText.setText("state: StartScreenState\nGo to Round Screen: Space");
		
		//create and attach logo
		logoPic = new Picture("startScreenLogo");
		logoPic.setImage(app.getAssetManager(), "Textures/UI/startScreenLogo2.png", true);
		logoPic.setWidth(main.getAppSettings().getWidth()*.6f);
		logoPic.setHeight(main.getAppSettings().getHeight()*.4f);
		logoPic.setPosition(
			 main.getAppSettings().getWidth()*.2f, 
		   main.getAppSettings().getHeight()*.3f);
		main.getGuiNode().attachChild(logoPic);
		
		//Keys
		InputManager inputManager = main.getInputManager();
		//inputManager.clearMappings();
		
		inputManager.addMapping("Start", new KeyTrigger(KeyInput.KEY_SPACE));
		inputManager.addListener(this, newMappings = new String[]{"Start"});	
	}


	@Override
	public void update(float tpf){

	}
	
	public void onAction(String name, boolean isPressed, float tpf) {
		if(name.equals("Start") && isPressed)
		{
			//transition to initial break screen
			AppStateManager asm = main.getStateManager();
			ChooseDifficultyState s = new ChooseDifficultyState();
			asm.detach(this);
			asm.attach(s);
		}
			
	}

	@Override
	public void cleanup()
	{
		System.out.println("cleaning up startScreen...");
		main.getGuiNode().detachChild(logoPic);
                main.deleteInputMappings(newMappings);
	}


}