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
        
        Spatial s = main.AntiTrojanSpatial.clone();
        s.setLocalScale(0.3f);
    Material mat = new Material(main.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
    mat.setColor("Color", ColorRGBA.Orange);
    s.setMaterial(mat);
    
       // this.towerLaser = new Laser(main.getAssetManager(), main);
   // this.attachChild((Node) towerLaser);
   // this.addControl(new TowerControl(this));
    
        this.addControl(new Tower.TowerControl(this));
    this.attachChild(s);
   // this.move(0, -1, 0);
    this.getChild("TowerGeo").removeFromParent();
    
        this.enemyPriority = TROJAN;
        this.strength = 40;
        this.speed = 11;
        this.range = 10;
        this.setName("Antitrojan");
    
    
    
    }
        
        
}