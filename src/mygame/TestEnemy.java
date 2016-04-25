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
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.shape.Sphere;

/**
 *
 * @author adeut_000
 */
public abstract class TestEnemy extends Enemy {


    Geometry g;

    public TestEnemy(Main main, Vector3f pos) {
        this.hp = 100;
        this.main = main;
        initModel(main);
        this.setLocalTranslation(pos);
    }


    private void initModel(Main main) {
        Sphere s = new Sphere(10, 10, 0.5f);
        g = new Geometry("TestEnemy", s);
        g.move(0, 0.4f, 0);
        Material mat = new Material(main.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.White);
        g.setMaterial(main.whiteGlow);
        this.attachChild(g);


    }

    @Override
    public void die() {
        new SingleParticleEmitter((SimpleApplication) main, this, Vector3f.ZERO, "enemyDeath");
        g.removeFromParent();
        main.explosion.playInstance();
        main.enemyCount--;
        main.bit += this.bitDrop;
    }

    public class TestEnemyControl extends EnemyControl {
        private static final int MOVING = 0;
        private static final int ATTACKING = 1;
        private float ATTACK_RANGE = 2f;
        private float ATTACK_COOLDOWN = 2f;
        private float SPEED = 1f;
        private float ATTACK_DMG = 2f;
        private float attackTimer = 0;
        private float time = 0;
        private int state;
        Tower target;
        Enemy e;

        public TestEnemyControl(Node enemyNode) {
            this.e = (Enemy) enemyNode;
            this.ATTACK_RANGE = e.range;
            this.ATTACK_DMG = e.strength;
            this.SPEED = e.speed;
            this.ATTACK_COOLDOWN = e.atkSpeed;

            //find first target
            this.target = null;
            findTarget();

        }

        @Override
        protected void controlUpdate(float tpf) {

            time += tpf;

            //if not within range of target move towards it	
            if (target != null
                    && target.isAlive()
                    && target.getLocalTranslation()
                    .distance(e.getLocalTranslation()) > ATTACK_RANGE) {

                Vector3f move = target.getLocalTranslation()
                        .subtract(e.getLocalTranslation())
                        .normalize()
                        .mult(tpf);
                e.move(move.mult(SPEED));
            }

            //if target is within range, attack
            if (e.alive && (target != null)
                    && target.isAlive() && target.getLocalTranslation()
                    .distance(e.getLocalTranslation()) <= ATTACK_RANGE) {
                if ((attackTimer += tpf) >= ATTACK_COOLDOWN) {
                    System.out.println("Attacking tower!");
                    target.receiveDamage(ATTACK_DMG);
                    attackTimer = 0;
                }
            }

            //if our  current target is dead, find a new target
            if ((target != null) && !target.isAlive()) {
                findTarget();
            }


            //e.move(0, 0, -tpf);
        }

        @Override
        protected void controlRender(RenderManager rm, ViewPort vp) {
        }

        //this method will find the closest tower to this enemy node 
        //and set it as its target. If this enemy currently has a target 
        //it will change its target if it finds a closer tower. 
        void findTarget() {
            float distTo, minDist;
            Tower newTarget;

            //if no current target find initial target
            if (target == null || !target.isAlive()) {
                minDist = Float.MAX_VALUE;
                newTarget = null;
            } else {
                minDist = target.getLocalTranslation()
                        .distance(e.getLocalTranslation());
                newTarget = target;
            }

            //look for closer target
            for (Tower t : main.towers) {

                if (t.isAlive()) {
                    //get the distance to the current tower under consideration
                    distTo = t.getLocalTranslation()
                            .distance(e.getLocalTranslation());

                    //check to see if it is closer than the closest tower
                    //we current have
                    //If within range and of high priority then taget
                    if ((distTo < minDist) && t.getName().contains(e.towerMap[e.towerPriority])) {
                        newTarget = t;
                        minDist = distTo;
                        //If within range, but not high priority but previous target isn't of high priority either
                    } else if (distTo < minDist) {
                        if (newTarget != null) {
                            if (!newTarget.getName().contains(e.towerMap[e.towerPriority])) {
                                newTarget = t;
                                minDist = distTo;
                            }
                        } else {
                            newTarget = t;
                            minDist = distTo;
                        }
                    }

                }
            }

            if (newTarget != null) {
                target = newTarget;
            }
        }

    }
}
