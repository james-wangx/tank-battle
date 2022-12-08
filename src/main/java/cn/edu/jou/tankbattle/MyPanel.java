package cn.edu.jou.tankbattle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

/**
 * 坦克大战的绘图区
 */
public class MyPanel extends JPanel implements KeyListener {
    private final HeroTank heroTank; // 自己的坦克
    private final Vector<EnemyTank> enemyTanks = new Vector<>(); // 敌人的坦克
    private final int enemyTankSize = 3; // 敌人坦克的数量

    public MyPanel() {
        heroTank = new HeroTank(100, 100, 10); // 初始化自己的坦克
        for (int i = 0; i < enemyTankSize; i++) { // 初始化敌人的坦克
            EnemyTank enemyTank = new EnemyTank((i + 1) * 100, 0, 10);
            enemyTank.setDirect(2); // 设置坦克方向
            enemyTanks.add(enemyTank);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1000, 750); // 填充矩形，默认黑色
        drawTank(heroTank.getX(), heroTank.getY(), g, heroTank.getDirect(), 1); // 画自己的坦克
        for (EnemyTank enemyTank : enemyTanks) { // 画敌人的坦克
            drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 0);
        }
    }

    /**
     * 画出坦克
     *
     * @param x      坦克左上角的 x 坐标
     * @param y      坦克右上角的 y 坐标
     * @param g      画笔
     * @param direct 坦克方向
     * @param type   坦克类型
     */
    public void drawTank(int x, int y, Graphics g, int direct, int type) {
        // 根据不同的坦克，设置不同的颜色
        switch (type) {
            case 0: // 敌人的坦克
                g.setColor(Color.cyan);
                break;
            case 1: // 我们的坦克
                g.setColor(Color.yellow);
                break;
        }

        // 根据坦克的方向，绘制坦克
        // 0：向上，1：向右，2：向下，3：向左
        switch (direct) {
            case 0:
                g.fill3DRect(x, y, 10, 60, false); // 画出坦克左侧轮子
                g.fill3DRect(x + 30, y, 10, 60, false); // 画出坦克右侧轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false); // 画出坦克中间矩形
                g.fillOval(x + 10, y + 20, 20, 20); // 画出坦克圆形盖子
                g.drawLine(x + 20, y + 30, x + 20, y); // 画出炮筒
                break;
            case 1:
                g.fill3DRect(x, y, 60, 10, false);
                g.fill3DRect(x, y + 30, 60, 10, false);
                g.fill3DRect(x + 10, y + 10, 40, 20, false);
                g.fillOval(x + 20, y + 10, 20, 20);
                g.drawLine(x + 30, y + 20, x + 60, y + 20);
                break;
            case 2:
                g.fill3DRect(x, y, 10, 60, false);
                g.fill3DRect(x + 30, y, 10, 60, false);
                g.fill3DRect(x + 10, y + 10, 20, 40, false);
                g.fillOval(x + 10, y + 20, 20, 20);
                g.drawLine(x + 20, y + 30, x + 20, y + 60);
                break;
            case 3:
                g.fill3DRect(x, y, 60, 10, false);
                g.fill3DRect(x, y + 30, 60, 10, false);
                g.fill3DRect(x + 10, y + 10, 40, 20, false);
                g.fillOval(x + 20, y + 10, 20, 20);
                g.drawLine(x + 30, y + 20, x, y + 20);
                break;
            default:
                System.out.println("暂时不处理");
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * 处理键盘按下的情况
     *
     * @param e 键盘事件
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_W) {
            heroTank.setDirect(0);
            heroTank.moveUp();
        } else if (keyCode == KeyEvent.VK_D) {
            heroTank.setDirect(1);
            heroTank.moveRight();
        } else if (keyCode == KeyEvent.VK_S) {
            heroTank.setDirect(2);
            heroTank.moveDown();
        } else if (keyCode == KeyEvent.VK_A) {
            heroTank.setDirect(3);
            heroTank.moveLeft();
        } else if (keyCode == KeyEvent.VK_J) {
            System.out.println("用户按下了J，开始射击");
            heroTank.shotEnemy();
        }

        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
