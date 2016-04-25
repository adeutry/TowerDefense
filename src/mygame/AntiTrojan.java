/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.shape.Box;
import static mygame.Tower.TROJAN;

/**
 *
 * @author mikey
 */
public class AntiTrojan extends Tower{

    
        Geometry g;
        int height; 
        
        
        public AntiTrojan(Main main) {

        
        
    super(main);
    this.main = main;
    this.enemyPriority = VIRUS;
    this.strength = 40;
    this.speed = 2;
    this.range = 10;
    this.setName("Antitrojan");
    
        
    //custom spatial
        Spatial s = main.AntiTrojanSpatial.clone();
    s.setLocalScale(0.3f);
    Material mat = new Material(main.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
    mat.setColor("Color", ColorRGBA.Orange);
    s.setMaterial(mat);
    
    
    this.addControl(new TowerControl(this));
    this.attachChild(s);
    this.move(0, -1.55f, 0);
    this.towerLaser.move(0, 3.2f, 0);
    this.getChild("TowerGeo").removeFromParent();
        
        
    
    }
        
        
}