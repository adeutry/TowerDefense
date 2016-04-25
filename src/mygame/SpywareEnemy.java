/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.shape.Sphere;

/**
 *
 * @author adeut_000
 */
public class SpywareEnemy extends TestEnemy {

  public SpywareEnemy(Main main, Vector3f pos,float dif) {
     super(main, pos);
    this.hp = 100*dif;
    this.bitDrop = 25;
    this.speed = 3;
    this.strength= (this.strength*dif);
    this.atkSpeed = 4;
    this.main = main;
    this.towerPriority = SPYWARESWEEPER;
    initModel(main);
    this.setLocalTranslation(pos);
    this.addControl(new TestEnemyControl(this));
    this.setName("spyware");
  }

  private void initModel(Main main) {
      this.detachChild(g);
    g = main.spikeBombSpatial.clone();
    g.scale(0.6f);
    g.move(0, 0.4f, 0);
    g.setMaterial(main.redGlow);
    this.attachChild(g);

  }
}
