/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.shape.Sphere;

/**
 *
 * @author adeut_000
 */
public class TestEnemy extends Enemy {
				
				public TestEnemy(Main main)
				{
								this.hp = 100;
								this.main = main;
								initModel(main);
								this.addControl(new TestEnemyControl(this));
				}
				
				private void initModel(Main main){
								Sphere s = new Sphere(10, 10, 0.5f);
								Geometry g = new Geometry("TestEnemy" , s);
								Material mat = new Material(main.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
								mat.setColor("Color", ColorRGBA.Red);
								g.setMaterial(mat);
								this.attachChild(g);
								
				}
				
				public class TestEnemyControl extends EnemyControl{
						
								Node testEnemyNode;
								
								public TestEnemyControl(Node enemyNode)
								{
										this.testEnemyNode = enemyNode;
								}

								@Override
								protected void controlUpdate(float tpf) {
												testEnemyNode.move(0,0,tpf);
								}

								@Override
								protected void controlRender(RenderManager rm, ViewPort vp) {
												
								}
								
				}
}
