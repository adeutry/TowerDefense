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
import com.jme3.scene.shape.Dome;
import com.jme3.scene.shape.PQTorus;
import com.jme3.scene.shape.Sphere;

/**
 *
 * @author mikey
 */
public class Trojan extends Enemy{

    Geometry g;
    int health=100; 

    public Trojan(Main main, Vector3f pos) {
        this.hp = health;
        this.main = main;
        //this.bitDrop = 25; 
        initModel(main);
        this.setLocalTranslation(pos);
        
        this.addControl(new Trojan.TrojanControl(this));
    }

    private void initModel(Main main) {
        Dome mesh = new Dome(Vector3f.ZERO, 2, 4, .5f,false); // Pyramid
        g = new Geometry("Trojan", mesh);
        g.move(0, 0f, 0);
        Material mat = new Material(main.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Red);
        g.setMaterial(main.TrojanGlow);
        this.attachChild(g);

    }

    @Override
    public void die() {
        new SingleParticleEmitter((SimpleApplication) main, this, Vector3f.ZERO, "enemyDeath");
        g.removeFromParent();
        main.explosion.playInstance();
        main.enemyCount--;
    }

    public class TrojanControl extends EnemyControl {

        Node testEnemyNode;
        private static final int MOVING = 0;
        private static final int ATTACKING = 1;
        private static final float ATTACK_RANGE = 3f;
        private float ATTACK_COOLDOWN = 2f;
        private float ATTACK_DMG = 5f;
        private float attackTimer = 0;
        private float time = 0;
        private int state;
        Tower target;
        Enemy e;
        
        private float teleportTimer = 0;
        private float teleportPause=4;
        

        public TrojanControl(Node enemyNode) {
            this.testEnemyNode = enemyNode;
            this.e = (Enemy) enemyNode;

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
                    .distance(testEnemyNode.getLocalTranslation()) > ATTACK_RANGE) {

                testEnemyNode.move(target.getLocalTranslation()
                        .subtract(testEnemyNode.getLocalTranslation())
                        .normalize()
                        .mult(tpf));
            }
            //Random jump around until in range of attack. Doesn't jump if within attacking range
            if(((teleportTimer+= tpf) >= teleportPause) && !(target.getLocalTranslation()
                    .distance(testEnemyNode.getLocalTranslation()) <= ATTACK_RANGE+5)){
                 int dirX, dirZ;
                // float dirY;
                 
                dirX = FastMath.nextRandomInt(-4,4);
                dirZ = FastMath.nextRandomInt(0, 6);
               // dirY = FastMath.nextRandomFloat();
                
                  testEnemyNode.move(target.getLocalTranslation()
                        .subtract(testEnemyNode.getLocalTranslation())
                        .normalize()
                        .mult(tpf)
                          .add(dirX, 0, dirZ));
                teleportTimer=0;
            }
            

            //if target is within range, attack
            if (e.alive && (target != null)
                    && target.isAlive() && target.getLocalTranslation()
                    .distance(testEnemyNode.getLocalTranslation()) <= ATTACK_RANGE) {
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


            //testEnemyNode.move(0, 0, -tpf);
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
                        .distance(testEnemyNode.getLocalTranslation());
                newTarget = target;
            }

            //look for closer target
            for (Tower t : main.towers) {

                if (t.isAlive()) {
                    //get the distance to the current tower under consideration
                    distTo = t.getLocalTranslation()
                            .distance(testEnemyNode.getLocalTranslation());

                    //check to see if it is closer than the closest tower
                    //we current have
                    if (distTo < minDist) {
                        newTarget = t;
                        minDist = distTo;
                    }
                }

            }

            if (newTarget != null) {
                target = newTarget;
            }
        }
    }
}
