package mygame;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bounding.BoundingVolume;
import com.jme3.collision.CollisionResults;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.ArrayList;

public class RoundState extends AbstractAppState implements ActionListener, ScreenController{

  Main main;
  private String newMappings[];
  Tower[] towers = new Tower[10];
  ArrayList<Node> collideList;
  private boolean active = true;
  //How often enemies are spawned. A lower value increases the spawn rate.
  private float ENEMY_SPAWN_RATE = 3f;
  private float ENEMY_SPAWN_RATE_INCR = 0.4f;
  //how far away from the origin the enemies will spawn
  private static final float ENEMY_SPAWN_OFFSET = 30f;
  //the maximum angle the enemies are allowed to spawn. If the angle = 180deg
  //the enemies will spawn from any angle on the negative Z axis side. If 
  //angle = 360 the enemies will spawn from any angle around the tower
  private static final float ENEMY_SPAWN_ANGLE_RANGE = 70f;
  private static final float MAX_ROUND_TIME = 20f;
  private float enemySpawnTimer = 0;
  private float roundTime = 0;
  
  private float testEnemyFreq = 0.5f;
  private float spywareFreq = 0.25f;
  private float trojanFreq = 0.25f;
  //MainTower
  MainTower tow;
  //lasers
  long totalTime, currentTime;
  long HALF_SEC = 500;

  @Override
  public void initialize(AppStateManager stateManager, Application app) {

    
    this.main = (Main) app;
    main.getFlyByCamera().setDragToRotate(false);
    main.stateInfoText.setText("state: RoundState\nPause Game: P\nEnd Screen: Space");
    collideList = new ArrayList<Node>();
    main.enemyCount = 0;
    main.roundNum++;
    ENEMY_SPAWN_RATE-=ENEMY_SPAWN_RATE_INCR*main.roundNum;
    
    //Add main tower + controls
     if(main.mainTower == null || main.getRootNode().getChild("computer") == null)
     {
       tow = new MainTower(main);
       main.mainTower = tow;
       main.mainTower.Tower.setName("computer");
     }else{
				 
				 main.mainTower.Tower.getControl(MainTowerControl.class).resetCam();
		 }
     
        //Keys
        InputManager inputManager = main.getInputManager();
        inputManager.addMapping("Pause", new KeyTrigger(KeyInput.KEY_P));
        inputManager.addListener(this, newMappings = new String[]{"Pause"});
        main.getNifty().fromXml("Interface/Menus.xml", "hud", this);

        // attach the Nifty display to the gui view port as a processor
        main.getGuiViewPort().addProcessor(main.getNiftyDisplay());

  }

@Override
  public void update(float tpf) {
    
    //increment the enemy spawn timer and if it exceeds the enemy spawn rate
    //spawn another enemy
    //Updates HUD data
    main.health = main.homeTower.getHealth();
    Label health = main.getNifty().getCurrentScreen().findNiftyControl("hp", Label.class);
    Label money = main.getNifty().getCurrentScreen().findNiftyControl("money", Label.class);
    health.setText("Health: " + main.health);
    money.setText("BIT: " + main.bit);
    enemySpawnTimer += tpf;
    if (active && (enemySpawnTimer >= ENEMY_SPAWN_RATE)) {
      System.out.println("Spawning enemy!");
      float angle = ((float) Math.random() - 0.5f) * ENEMY_SPAWN_ANGLE_RANGE * FastMath.DEG_TO_RAD;
      System.out.println("Angle: " + angle);
      float posX = FastMath.sin(angle) * ENEMY_SPAWN_OFFSET;
      float posZ = -FastMath.cos(angle) * ENEMY_SPAWN_OFFSET;
      System.out.println("posX: " + posX + "\nposY: " + posZ + "\n");
      Enemy te;
      float r = (float)Math.random();
      if(r < testEnemyFreq )
      {
        te = new Virus(main, new Vector3f(posX, 0, posZ), (float) (main.diff+main.roundDifficultyIncr));
      }else if( (r > testEnemyFreq) && (r < testEnemyFreq + trojanFreq) ) {
        te = new Trojan(main, new Vector3f(posX,0,posZ), (float) (main.diff+main.roundDifficultyIncr));
      } else{
        te = new SpywareEnemy(main, new Vector3f(posX, 0, posZ),(float) (main.diff+main.roundDifficultyIncr));
      }
      
      main.enemies.add(te);
      main.getRootNode().attachChild(te);
      //te.setLocalTranslation(posX, 0, posZ);
      enemySpawnTimer = 0;
      main.enemyCount++;
    }

    //update the round timer
    if (active && ((roundTime += tpf) >= MAX_ROUND_TIME)) {
      active = false;
      System.out.println("round state deactivated");
    }

    //if the round has ended and no more enemies remain we end this round
    if (!active && (main.enemyCount <= 0)) {
        main.roundDifficultyIncr+=0.1;
      System.out.println("transitioning to break state...");
      BreakState bs = new BreakState();
      main.getStateManager().detach(this);
      main.getStateManager().attach(bs);
    }
    
    //check if the main tower is still alive
    //if not transition to the end screen
    if(!main.homeTower.isAlive())
    {
      //transition to initial break screen
      AppStateManager asm = main.getStateManager();
			
		  tow.Tower.getControl(MainTowerControl.class).resetCam();
      EndScreenState s = new EndScreenState();
      asm.detach(this);
      asm.attach(s);
    }

  }

    public void onAction(String name, boolean isPressed, float tpf) {

        //Pausing game during round
        if (name.equals("Pause") && isPressed) {
            System.out.println("Paused!");
            //transition to initial break screen
            AppStateManager asm = main.getStateManager();
            PauseState breakState = new PauseState();
            asm.detach(this);
            asm.attach(breakState);
        } else if (name.equals("End") && isPressed) {
            //transition to initial break screen
            AppStateManager asm = main.getStateManager();
            EndScreenState endScreenState = new EndScreenState();
            asm.detach(this);
            asm.attach(endScreenState);
        }
    }
  @Override
  public void cleanup() {
    System.out.println("Cleaning up round state...");
    main.deleteInputMappings(newMappings);
    main.getGuiViewPort().removeProcessor(main.getNiftyDisplay());
    main.enemies.clear();
  }

    public void bind(Nifty nifty, Screen screen) {
    }

    public void onStartScreen() {
    }

    public void onEndScreen() {
    }
}
