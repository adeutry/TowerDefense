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
    protected int hp = 100;
    public static final int ANTIVIRUS = 0, SPYWARESWEEPER = 1, ANTITROJAN = 2;
    protected static final String towerMap[] = {"antivirus", "spywaresweeper", "antitrojan", "tower"};
    protected float range = 2;
    protected int strength;
    protected float speed;
    protected float atkSpeed;
    protected int towerPriority = 3;
    protected int bitDrop = 0;
    boolean alive = true;

    public Enemy() {
        this.setName("enemy");
    }

    public void receiveDamage(float dmg) {
        new SingleParticleEmitter((SimpleApplication) main, this, Vector3f.ZERO, "enemyDamage");
        //check if enemy is dead
        if ((this.hp -= dmg) <= 0) {
            this.alive = false;
            die();
        }
    }

    public void die() {
    }

    public boolean isAlive() {
        return alive;
    }
}
