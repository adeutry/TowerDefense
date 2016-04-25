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
import java.util.ArrayList;

public class RoundState extends AbstractAppState implements ActionListener {

    Main main;
    private String newMappings[];
    Tower[] towers = new Tower[10];
    ArrayList<Node> collideList;
    private boolean active = true;
    //How often enemies are spawned. A lower value increases the spawn rate.
    private static final float ENEMY_SPAWN_RATE = 3f;
    //how far away from the origin the enemies will spawn
    private static final float ENEMY_SPAWN_OFFSET = 30f;
    //the maximum angle the enemies are allowed to spawn. If the angle = 180deg
    //the enemies will spawn from any angle on the negative Z axis side. If 
    //angle = 360 the enemies will spawn from any angle around the tower
    private static final float ENEMY_SPAWN_ANGLE_RANGE = 70f;
    private static final float MAX_ROUND_TIME = 10f;
    private float enemySpawnTimer = 0;
    private float roundTime = 0;
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

        //Keys
        InputManager inputManager = main.getInputManager();
        inputManager.addMapping("Pause", new KeyTrigger(KeyInput.KEY_P));
        inputManager.addMapping("End", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(this, newMappings = new String[]{"Pause", "End"});

        //Add main tower + controls
        tow = new MainTower(main);
    }

    @Override
    public void update(float tpf) {
        //increment the enemy spawn timer and if it exceeds the enemy spawn rate
        //spawn another enemy
        enemySpawnTimer += tpf;
        if (active && (enemySpawnTimer >= ENEMY_SPAWN_RATE)) {
            System.out.println("Spawning enemy!");
            float angle = ((float) Math.random() - 0.5f) * ENEMY_SPAWN_ANGLE_RANGE * FastMath.DEG_TO_RAD;
            System.out.println("Angle: " + angle);
            float posX = FastMath.sin(angle) * ENEMY_SPAWN_OFFSET;
            float posZ = -FastMath.cos(angle) * ENEMY_SPAWN_OFFSET;
            System.out.println("posX: " + posX + "\nposY: " + posZ + "\n");
            TestEnemy te = new TestEnemy(main, new Vector3f(posX, 0, posZ));
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
            System.out.println("transitioning to break state...");
            BreakState bs = new BreakState();
            main.getStateManager().detach(this);
            main.getStateManager().attach(bs);
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
        main.enemies.clear();
    }
}
