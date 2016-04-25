/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;


public class AntiVirusTower extends Tower {

    public AntiVirusTower(Main main) {
        super(main);
        this.enemyPriority = VIRUS;
        this.strength = 100;
        this.speed = 10;
        this.range = 50;
        this.setName("Antivirus");
        this.addControl(new TowerControl(this));
    }
    
}
