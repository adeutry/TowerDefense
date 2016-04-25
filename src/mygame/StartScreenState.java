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
        private Picture controlPic;
        private String newMappings[];
	Main main;
	
	@Override
	public void initialize(AppStateManager stateManager, Application app) 
	{
		this.main = (Main)app;
		main.stateInfoText.setText("state: StartScreenState\nGo to Round Screen: Space");
		main.setDisplayFps(false);
                main.setDisplayStatView(false);
		//create and attach logo
		logoPic = new Picture("startScreenLogo");
		logoPic.setImage(app.getAssetManager(), "Textures/UI/startScreenLogo2.png", true);
		logoPic.setWidth(main.getAppSettings().getWidth()*.7f);
		logoPic.setHeight(main.getAppSettings().getHeight()*.5f);
		logoPic.setPosition(
			 main.getAppSettings().getWidth()*.15f, 
		   main.getAppSettings().getHeight()*.45f);
		main.getGuiNode().attachChild(logoPic);
                
                //create and attach logo
		controlPic = new Picture("startScreenLogo");
		controlPic.setImage(app.getAssetManager(), "Textures/UI/controls.png", true);
		controlPic.setWidth(main.getAppSettings().getWidth()*.6f);
		controlPic.setHeight(main.getAppSettings().getHeight()*.4f);
		controlPic.setPosition(
			 main.getAppSettings().getWidth()*.2f, 
		   main.getAppSettings().getHeight()*.1f);
		main.getGuiNode().attachChild(controlPic);
		
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
                main.getGuiNode().detachChild(controlPic);
                main.deleteInputMappings(newMappings);
	}


}