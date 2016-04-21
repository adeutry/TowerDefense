package mygame;

import com.jme3.bounding.BoundingVolume;
import com.jme3.collision.CollisionResults;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author adeut_000
 */
public class CollisionControl extends AbstractControl {

    private Main main;
    private String name;
    private Node a;
    private LinkedList<Spatial> b = new LinkedList<Spatial>();

    public CollisionControl(Main main, Node a) {
        this.main = main;
        this.a = a;
        this.name = a.getName();
    }

    private void checkCollision() {
        CollisionResults results = new CollisionResults();
        int i = 0;
        LinkedList<Spatial> objB = new LinkedList<Spatial>();
        for (Spatial b : main.towers) {
            if (b != a) {
                BoundingVolume bv = b.getWorldBound();
                a.collideWith(bv, results);
                if (results.size() > 0) {
                    objB.add(b);
//                System.out.println(name + " Object: " + a
//                        + " Collided with Object: " + b);
                    results.clear();
                }
            }
            i++;
        }
        this.b = objB;
    }

    public LinkedList<Spatial> getCollisions() {
        return this.b;
    }

    @Override
    protected void controlUpdate(float tpf) {
        checkCollision();
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }
}
