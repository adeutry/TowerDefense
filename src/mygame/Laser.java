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
    
    
    public Laser(AssetManager am)
    {
        beamMat = new Material(am , "Common/MatDefs/Misc/Unshaded.j3md");

        beamMat.setColor("Color", ColorRGBA.Orange);
        
    }
    
    public void shoot( Vector3f pos )
    {      
        this.detachAllChildren();     
        float dist = this.getLocalTranslation().distance(pos);
        laserBeamCylinder = new Cylinder(20 , 50 , .1f , dist, true);
        laserBeam = new Geometry("laserBeam" , laserBeamCylinder);
        laserBeam.setMaterial(beamMat);
        laserBeam.setLocalTranslation(0, 0, dist/2);        
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
     
    }
    
    public void update(float tpf)
    {
        
    }
}
