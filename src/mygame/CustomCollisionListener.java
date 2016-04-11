/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.scene.Spatial;

/**
 *
 * @author mikey
 */
public class CustomCollisionListener extends RigidBodyControl implements PhysicsCollisionListener{

    
    Main handle;
    public void MyCustomControl(Main m) {
        m.bulletAppState.getPhysicsSpace().addCollisionListener(this);
        handle=m;
    }

    
    public void collision(PhysicsCollisionEvent event) {
        
           if ( event.getNodeA().getName().equals("laser") ) {
             Spatial node = event.getNodeA();
            // handle.getRootNode().detachChild(node);
               System.out.println("Enemy Hit");
            handle.getRootNode().detachChild(node);
        } else if ( event.getNodeB().getName().equals("TestEnemy") ) {
             Spatial node = event.getNodeB();
            handle.getRootNode().detachChild(node);
               System.out.println("Enemy Hit");
            
        } 
               
         
            
            
        }
        
    }
    

