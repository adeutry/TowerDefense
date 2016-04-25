/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.Application;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CylinderCollisionShape;
import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl.ControlDirection;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Cylinder;
import com.jme3.scene.shape.Sphere;

/**
 *
 * @author mikey
 */
public class MainTower {
Application app;
  Main main;
  Material mat;
  Material mat1, mat2;
  Cylinder cin;
  public Node Head;
  public Geometry circ, laserGeom;
  Node laserNode, Tower;

  MainTower(Application app) {
    this.app = app;
    this.main = (Main) app;
    init();

    initLaser();
    initPhysics();
    main.initCrossHairs();
    //MainTowerControl d = new MainTowerControl(app, this);

  }

  public void init() {

    Box box = new Box(1, 3, 1);
    Box box2 = new Box(1, 1, 1);
    Sphere head = new Sphere(10, 10, .5f);

    Geometry gBox = new Geometry("s", box);
    circ = new Geometry("s", head);



    Tower = new Node();
    Head = new Node();



    gBox.setLocalTranslation(0, 0, 0);

    circ.setLocalTranslation(0, 0, 0);

    mat = new Material(main.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
    mat.setColor("Color", ColorRGBA.Pink);
    mat1 = new Material(main.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
    mat1.setColor("Color", ColorRGBA.Green);

    gBox.setMaterial(mat);
    circ.setMaterial(mat1);

    Head.setLocalTranslation(0, 6, 0);

    //Camera
    Vector3f camVec = new Vector3f(Head.getLocalTranslation().x, Head.getLocalTranslation().y, Head.getLocalTranslation().z);
    camVec.normalize();
    main.getCamera().setLocation(camVec.add(0, -.25f, 0));
    main.getCamera().lookAt(Head.getLocalTranslation().add(0, 0, -5), Vector3f.UNIT_Y);
    //System.out.println("maintow cam vector " + main.getCamera().getDirection());
    //System.out.println("Head direction vector " + Head.getLocalTranslation());


    Head.attachChild(circ);
    Tower.attachChild(Head);
    Tower.attachChild(gBox);
    Tower.addControl(new MainTowerControl(app, this));
    main.getRootNode().attachChild(Tower);

  }

  public void initLaser() {

    cin = new Cylinder(10, 10, .03f, 60);
    laserGeom = new Geometry("laser", cin);
    laserGeom.setMaterial(mat);
    laserGeom.setLocalTranslation(0, 0, 0);
  }

  public void initPhysics() {

    GhostControl ghost = new GhostControl(
            new CapsuleCollisionShape(.03f, 30));  // a capsule shaped 
    laserNode = new Node("a ghost-controlled thing");
    laserNode.addControl(ghost);


  }
}
