/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.Application;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 *
 * @author mikey
 */
public class MainTowerControl extends Node implements AnalogListener{
    float yDir=0;
    float xDir=0;
    float zDir=0;
    // Vector3f Dir;
    float speed=(float) 1.25;
    
    Application app;
    Main main;
    MainTower m;
    ActionListener actionListener;
    long totalTime, currentTime;
    long HALF_SEC = 500;

    MainTowerControl(Application app,final MainTower m) {
        this.m=m;
        this.app=app;
        this.main=(Main) app;
        //camera stuff
        yDir=6;
        xDir=0;
        zDir=-5;
        // Dir = new Vector3f(zDir,xDir,zDir);
         
         
        System.out.println(yDir+ " " + xDir + " " + zDir);
        
        totalTime = System.currentTimeMillis();
        
        actionListener = new ActionListener() {
        public void onAction(String name, boolean isPressed, float tpf) {
         if(name.equals("shoot")){
               m.Head.attachChild(m.laserGeom);
               m.Head.attachChild(m.laserNode);
               }
            }
        };
        initControls();
    }
    
    
       private void initControls(){    
           
	   InputManager inputManager = main.getInputManager();
           inputManager.addMapping("W", new KeyTrigger(KeyInput.KEY_T));
           inputManager.addMapping("A", new KeyTrigger(KeyInput.KEY_F));
           inputManager.addMapping("S", new KeyTrigger(KeyInput.KEY_G));
           inputManager.addMapping("D", new KeyTrigger(KeyInput.KEY_H));
           inputManager.addMapping("shoot", new KeyTrigger(KeyInput.KEY_Y));
	   inputManager.addListener(this, "W","A","S","D");	
           inputManager.addListener(actionListener, "shoot");
           
           
    }

    public void onAnalog(String name, float value, float tpf) {
            if(name.equals("W")){
               
                Vector3f Dir = new Vector3f(xDir,yDir,zDir);
            
                yDir+=tpf*7;
                m.Head.lookAt(Dir, Vector3f.UNIT_Y);
                main.getCamera().lookAt(Dir, Vector3f.UNIT_Y);
                System.out.println(yDir);
                
                /*
                Vector3f rottt = new Vector3f(xDir,yDir,zDir);
                m.Head.lookAt(rottt, rottt);
                
               m.Head.rotate(value*speed, 0, 0);
                //yDir+=value*speed*4;
               yDir+=tpf*7;
               main.getCamera().lookAt(new Vector3f(xDir, yDir, zDir), Vector3f.UNIT_Y);
                //System.out.println("up");
                * 
                * **/
                
            }else if(name.equals("S")){
                
                
                Vector3f Dir = new Vector3f(xDir,yDir,zDir);
                
                yDir-=tpf*7;
                m.Head.lookAt(Dir, Vector3f.UNIT_Y);
                main.getCamera().lookAt(Dir, Vector3f.UNIT_Y);
                System.out.println(yDir);
                /*
                yDir-=tpf*7;
                 m.Head.rotate(-value*speed, 0, 0);
            main.getCamera().lookAt(new Vector3f(xDir, yDir, zDir), Vector3f.UNIT_Y);
                //System.out.println("look down");
                * */
            }else if(name.equals("A")){
                
                Vector3f Dir = new Vector3f(xDir,yDir,zDir);
                
                    xDir-=tpf*7;
                m.Head.lookAt(Dir, Vector3f.UNIT_Y);
                main.getCamera().lookAt(Dir, Vector3f.UNIT_Y);
                System.out.println(yDir);
                
                /*
                 xDir-=tpf*7;
                 m.Head.rotate(0, value*speed, 0);
            main.getCamera().lookAt(new Vector3f(xDir, yDir, zDir), Vector3f.UNIT_Y);
            * */
            }else if(name.equals("D")){
                
                
                Vector3f Dir = new Vector3f(xDir,yDir,zDir);
                
                    xDir+=tpf*7;
                m.Head.lookAt(Dir, Vector3f.UNIT_Y);
                main.getCamera().lookAt(Dir, Vector3f.UNIT_Y);
                System.out.println(yDir);
                
                /*
                 xDir+=tpf*7;
                m.Head.rotate(0,-value*speed,0);
            main.getCamera().lookAt(new Vector3f(xDir, yDir, zDir), Vector3f.UNIT_Y);
            * */
            }
    }
}