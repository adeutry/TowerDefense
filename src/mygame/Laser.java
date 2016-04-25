/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Cylinder;

/**
 *
 * @author adeut_000
 */
public class Laser extends Node {

    Geometry laserBeam;
    Cylinder laserBeamCylinder;
    Material beamMat;
    private static float LIFETIME = 0.4f;
    private float laserTimer = 0;
    private boolean shooting = false;
    Main main;

    public Laser(AssetManager am, Main main) {
        this.main = main;
        beamMat = new Material(am, "Common/MatDefs/Misc/Unshaded.j3md");
        beamMat.setColor("Color", ColorRGBA.Orange);

    }

    public void shoot(Vector3f pos) {
        this.detachAllChildren();
        float dist = this.getLocalTranslation().distance(pos);
        laserBeamCylinder = new Cylinder(20, 50, .1f, dist, true);
        laserBeam = new Geometry("laserBeam", laserBeamCylinder);
        laserBeam.setMaterial(main.laserGlow);
        laserBeam.setLocalTranslation(0, 0, dist / 2);
        Vector3f relativeVector = pos.subtract(this.getLocalTranslation());

        //calculate angle sin
        float sin = Vector3f.UNIT_Z.cross(relativeVector.normalize()).length();

        //calculate angle cos
        float cos = Vector3f.UNIT_Z.dot(relativeVector.normalize());

        float angle = -FastMath.atan2(sin, cos);
        Quaternion q = new Quaternion();
        q.fromAngleAxis(angle, relativeVector.cross(Vector3f.UNIT_Z));

        this.attachChild(laserBeam);
        this.setLocalRotation(q);
        shooting = true;

    }

    public void update(float tpf) {
        if (shooting) {
            laserTimer += tpf;
            laserBeam.setLocalScale(1, (LIFETIME - laserTimer) / LIFETIME, 1);
            if (laserTimer >= LIFETIME) {
                this.detachAllChildren();
                shooting = false;
                laserTimer = 0;
            }
        }

    }
}
