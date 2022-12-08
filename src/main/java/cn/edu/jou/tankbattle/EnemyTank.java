package cn.edu.jou.tankbattle;

import java.util.Vector;

/**
 * 敌人的坦克
 */
public class EnemyTank extends Tank {
    private Vector<Shot> shots = new Vector<>();
    private boolean isLive = true;

    public EnemyTank(int x, int y) {
        super(x, y);
    }

    public EnemyTank(int x, int y, int speed) {
        super(x, y, speed);
    }

    public Vector<Shot> getShots() {
        return shots;
    }

    public void setShots(Vector<Shot> shots) {
        this.shots = shots;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }
}
