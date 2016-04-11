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
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;

/**
 *
 * @author adeut_000
 */
public class Tower extends Node {

		Main main;
		public float hp = 100;
		private boolean alive = true;
		
    public Tower(Main main) {
        this.main = main;
				float height = 2;
        Box b = new Box(.5f, height, .5f);
        Geometry g = new Geometry("TestTower", b);
        g.move(0, height, 0);
        Material mat = new Material(main.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        g.setMaterial(mat);
				this.addControl(new TowerControl(this));
        this.attachChild(g);
    }
		
		public void receiveDamage(float dmg)
		{
				
				new SingleBurstParticleEmitter((SimpleApplication)main, this, Vector3f.ZERO);
				System.out.println("Ow!, I took " + dmg + "damage!");
				if((this.hp -= dmg) <= 0)
				{
						System.out.println("Oh no! I have died...");
						die();
						alive = false;
				}
		}
		
		public boolean isAlive()
		{
				return alive;
		}
		
		//death animation
		public void die()
		{
				new SingleBurstParticleEmitter((SimpleApplication)main, this, Vector3f.ZERO);
		}
		
		public class TowerControl extends AbstractControl{

				Tower tower;
				
				public TowerControl(Tower tower)
				{
						this.tower = tower;
				}
				
				@Override
				protected void controlUpdate(float tpf) {
						if(!tower.isAlive())
								tower.move(0, -tpf, 0);
				}

				@Override
				protected void controlRender(RenderManager rm, ViewPort vp) {
						
				}
				
		}
}
