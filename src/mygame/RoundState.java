package mygame;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;

public class RoundState extends AbstractAppState implements ActionListener {

	Main main;
	Tower[] towers = new Tower[10];		
	
	//How often enemies are spawned. A lower value increases the spawn rate.
	private static final float ENEMY_SPAWN_RATE = 1f;
	
	//how far away from the origin the enemies will spawn
	private static final float ENEMY_SPAWN_OFFSET = 20f;
	
  //the maximum angle the enemies are allowed to spawn. If the angle = 180deg
	//the enemies will spawn from any angle on the negative Z axis side. If 
	//angle = 360 the enemies will spawn from any angle around the tower
	private static final float ENEMY_SPAWN_ANGLE_RANGE = 90f;
	
	private float enemySpawnTimer = 0;
	
	@Override
	public void initialize(AppStateManager stateManager, Application app) 
	{
				this.main = (Main)app;
				main.stateInfoText.setText("state: RoundState\nPause Game: P\nEnd Screen: Space");
				
				//Camera
				main.getCamera().setLocation(new Vector3f(0,8,0));
				main.getCamera().lookAt(new Vector3f(0,0,-15), Vector3f.UNIT_Y);
				main.getFlyByCamera().setEnabled(true);
				
				
				//Keys
				InputManager inputManager = main.getInputManager();
				//inputManager.clearMappings();
				inputManager.addMapping("Pause", new KeyTrigger(KeyInput.KEY_P));
				inputManager.addMapping("End", new KeyTrigger(KeyInput.KEY_SPACE));
				inputManager.addListener(this, "Pause","End");	
				
				//Add some test towers
				Tower temp = new Tower(main);
				temp.setLocalTranslation(0, 0, -20);
				main.towers.add(temp);
				main.getRootNode().attachChild(main.towers.get(0));
				
				temp = new Tower(main);
				temp.setLocalTranslation(10, 0, -10);
				main.towers.add(temp);
				main.getRootNode().attachChild(main.towers.get(1));
				
				temp = new Tower(main);
				temp.setLocalTranslation(-10, 0, -10);
				main.towers.add(temp);
				main.getRootNode().attachChild(main.towers.get(2));
								
				//Add a testEnemy
				//main.getRootNode().attachChild(new TestEnemy(main));
	}


	@Override
	public void update(float tpf){
			//increment the enemy spawn timer and if it exceeds the enemy spawn rate
			//spawn another enemy
			enemySpawnTimer += tpf;
			if(enemySpawnTimer >= ENEMY_SPAWN_RATE)
			{
					System.out.println("Spawning enemy!");
					float angle = ((float)Math.random()-0.5f)*ENEMY_SPAWN_ANGLE_RANGE*FastMath.DEG_TO_RAD;
					System.out.println("Angle: " + angle);
					float posX = FastMath.sin(angle)*ENEMY_SPAWN_OFFSET;
					float posZ = -FastMath.cos(angle)*ENEMY_SPAWN_OFFSET;
					System.out.println("posX: " + posX + "\nposY: " + posZ + "\n");
					TestEnemy te = new TestEnemy(main, new Vector3f(posX, 0, posZ));
					main.getRootNode().attachChild(te);
					//te.setLocalTranslation(posX, 0, posZ);
					enemySpawnTimer = 0;
			}
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