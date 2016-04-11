/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
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
				
				public TestEnemy(Main main, Vector3f pos)
				{
								this.hp = 100;
								this.main = main;
								initModel(main);
								this.setLocalTranslation(pos);
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
								private static final int MOVING = 0;
								private static final int ATTACKING = 1;
								private int state;
								Tower target;
					
								
								public TestEnemyControl(Node enemyNode)
								{
										this.testEnemyNode = enemyNode;
										
										//find first target
										this.target = null;
										findTarget();
										
								}

								@Override
								protected void controlUpdate(float tpf) {
										
										System.out.println("testEnemyNode pos: " + testEnemyNode.getLocalTranslation());
										
										if(target != null)
										{
												testEnemyNode.move(
																target.getLocalTranslation()
																.subtract(testEnemyNode.getLocalTranslation())
																.normalize()
																.mult(tpf)
																);
										}
												
										
										//testEnemyNode.move(0, 0, -tpf);
								}

								@Override
								protected void controlRender(RenderManager rm, ViewPort vp) {
												
								}
								
								//this method will find the closest tower to this enemy node 
								//and set it as its target. If this enemy currently has a target 
								//it will change its target if it finds a closer tower. 
								void findTarget()
								{
										float distTo, minDist;
										Tower newTarget;
														
										//if no current target find initial target
										if(target == null)
										{
												minDist = Float.MAX_VALUE;
												newTarget = null;
										}else{
												minDist = target.getLocalTranslation()
																.distance(testEnemyNode.getLocalTranslation());
												newTarget = target;
										}
										
										
									
										
										
										
										//look for closer target
										for(Tower t: main.towers)
										{
												
												//get the distance to the current tower under consideration
												distTo = t.getLocalTranslation()
																.distance(testEnemyNode.getLocalTranslation());
												
												//check to see if it is closer than the closest tower
												//we current have
												if(distTo < minDist)
												{
														newTarget = t;
														minDist = distTo;
												}					
										}
										
										if(newTarget != null)
										{
												target = newTarget;
										}
								}
								
				}
}
