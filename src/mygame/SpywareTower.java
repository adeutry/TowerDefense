/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import static mygame.Tower.VIRUS;

/**
 *
 * @author Milo
 */
public class SpywareTower extends Tower {
  
  public SpywareTower(Main main)
  {
    super(main);
    this.main = main;
    this.enemyPriority = SPYWARE;
    this.strength = 100;
    this.speed = 2;
    this.range = 10;
    this.setName("spywaresweeper");
    
        
    //custom spatial
    Spatial s = main.spywareTowerSpatial.clone();
    s.setLocalScale(0.3f);
    Material mat = new Material(main.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
    mat.setColor("Color", ColorRGBA.Red);
    s.setMaterial(mat);
    
    
    this.addControl(new TowerControl(this));
    this.attachChild(s);
    this.move(0, -1.55f, 0);
    this.towerLaser.move(0, 3.2f, 0);
    this.getChild("TowerGeo").removeFromParent();
  }
}
