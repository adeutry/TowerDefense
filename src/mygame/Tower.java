/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;

/**
 *
 * @author adeut_000
 */
public class Tower extends Node {

    public Tower(Main main) {
        float height = 2;
        Box b = new Box(.5f, height, .5f);
        Geometry g = new Geometry("TestTower", b);
        g.move(0, height, 0);
        Material mat = new Material(main.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        g.setMaterial(mat);
        this.attachChild(g);
    }
}
