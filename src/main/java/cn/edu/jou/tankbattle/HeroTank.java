package cn.edu.jou.tankbattle;

import java.util.Vector;

/**
 * 自己的坦克
 */
public class HeroTank extends Tank {
    // 可以发射多颗子弹
    Vector<Shot> shots = new Vector<>();
    private Shot shot; // 一颗子弹

    public HeroTank(int x, int y) {
        super(x, y);
    }

    public HeroTank(int x, int y, int speed) {
        super(x, y, speed);
    }

    /**
     * 向敌人开火，根据坦克自身方向确定子弹的方向
     * 启动射击线程，让子弹移动
     */
    public void shotEnemy() {
        // 控制最多 5 颗子弹
        if (shots.size() == 5) {
            return;
        }
        // 根据当前坦克的位置和方向，创建 Shot 对象
        switch (getDirect()) {
            case 0:
                shot = new Shot(getX() + 20, getY(), 0);
                break;
            case 1:
                shot = new Shot(getX() + 60, getY() + 20, 1);
                break;
            case 2:
                shot = new Shot(getX() + 20, getY() + 60, 2);
                break;
            case 3:
                shot = new Shot(getX(), getY() + 20, 3);
                break;
        }
        // 把新创建的 shot 放入到 shots
        shots.add(shot);
        // 启动射击线程
        new Thread(shot).start();
    }

    public Shot getShot() {
        return shot;
    }

    public void setShot(Shot shot) {
        this.shot = shot;
    }
}
