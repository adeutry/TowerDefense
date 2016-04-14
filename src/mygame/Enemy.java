/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 *
 * @author adeut_000
 */
public class Enemy extends Node {
				
				Main main;
				EnemyControl control;
				int hp;
				boolean alive = true;
				
				public Enemy()
				{
							this.hp = 100;	
				}
				
				public void receiveDamage(float dmg)
				{
						new EnemyDamageParticleEmitter((SimpleApplication)main, this, Vector3f.ZERO);
						//check if enemy is dead
						if( (this.hp -= dmg) <= 0 )
						{
								this.alive = false;
								die();
						}
				}
				
				public void die()
				{
						
				}
				
				public boolean isAlive()
				{
						return alive;
				}
				
				
}
