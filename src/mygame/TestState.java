/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Cylinder;
import java.awt.Color;

/**
 *
 * @author adeut_000
 */
public class TestState extends AbstractAppState implements ActionListener {

  Main main;
  Geometry cylGeo;
  float laserScale = 2;

  @Override
  public void initialize(AppStateManager stateManager, Application app) {
    this.main = (Main) app;
    main.stateInfoText.setText("state: TestState\nMain Menu: Space");

    main.getCamera().setLocation(new Vector3f(0, 2, 10));

    //add tower
   // Tower t1 = new Tower(main);
    //main.towers.add(t1);
    Tower t1 = new SpywareTower(main);
    main.towers.add(t1);

    //enemy
    TestEnemy enemy = new TestEnemy(main, new Vector3f(10f, 0, 0));
    main.enemies.add(enemy);
    main.getRootNode().attachChild(t1);
    main.getRootNode().attachChild(enemy);

    //laser beam thing
    Cylinder cyl = new Cylinder(20, 50, 0.001f, 10);
    cylGeo = new Geometry("laser", cyl);
    cylGeo.rotate(0, FastMath.HALF_PI, 0);
    cylGeo.setMaterial(main.laserGlow);
    main.getRootNode().attachChild(cylGeo);

    //Keys
    InputManager inputManager = main.getInputManager();
    inputManager.addMapping("Menu", new KeyTrigger(KeyInput.KEY_SPACE));
    inputManager.addListener(this, new String[]{"Menu"});
    
    SpywareEnemy se = new SpywareEnemy(main, Vector3f.ZERO);
    main.getRootNode().attachChild(se);
  }

  @Override
  public void update(float tpf) {
    laserScale = laserScale * 1.21f;
    //	cylGeo.setLocalScale(laserScale,laserScale,1);



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
  }
}
