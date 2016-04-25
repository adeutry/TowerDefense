package mygame;

import com.jme3.math.Vector3f;

/**
 *
 * @author Belcky
 */
public class Virus extends TestEnemy {

    public Virus(Main main, Vector3f pos) {
        super(main, pos);
        this.hp = 150;
        this.speed = 5f;
        this.strength = 40;
        this.towerPriority = ANTIVIRUS;
        this.atkSpeed = 5;
        this.range = 2;
        this.bitDrop = 50;
        this.setName("virus");
        this.addControl(new TestEnemyControl(this));
    }
}
