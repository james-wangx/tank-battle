package cn.edu.jou.tankbattle;

/**
 * 自己的坦克
 */
public class HeroTank extends Tank {
    private Shot shot; // 射击线程

    public HeroTank(int x, int y) {
        super(x, y);
    }

    public HeroTank(int x, int y, int speed) {
        super(x, y, speed);
    }

    /**
     * 向敌人开火
     */
    public void shotEnemy() {
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
        // 启动射击线程
        new Thread(shot).start();
    }
}
