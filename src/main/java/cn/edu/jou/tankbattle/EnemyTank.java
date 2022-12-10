package cn.edu.jou.tankbattle;

import java.util.Vector;

/**
 * 敌人的坦克
 */
public class EnemyTank extends Tank implements Runnable {
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

    /**
     * 随机产生方向，让坦克朝一个方向移动 30 次，然后换一个方向继续
     */
    @Override
    public void run() {
        while (isLive) {
            // 如果没有子弹，再加入一颗子弹，并发射
            if (shots.size() == 0) {
                Shot shot = null;
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
                shots.add(shot);
                new Thread(shot).start();
            }
            switch (getDirect()) {
                case 0:
                    // 让坦克保持一个方向，走 30 步
                    for (int i = 0; i < 30; i++) {
                        if (getY() > 0) {
                            moveUp();
                        }
                        // 休眠 50 ms
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 1:
                    for (int i = 0; i < 30; i++) {
                        if (getX() + 60 < 1000) {
                            moveRight();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 2:
                    for (int i = 0; i < 30; i++) {
                        if (getY() + 60 < 750) {
                            moveDown();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 3:
                    for (int i = 0; i < 30; i++) {
                        if (getX() > 0) {
                            moveLeft();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
            }
            // 随机改变方向
            setDirect((int) (Math.random() * 4));
        }
    }
}
