package cn.edu.jou.tankbattle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

/**
 * 坦克大战的绘图区
 */
public class MyPanel extends JPanel implements KeyListener, Runnable {
    private final HeroTank ht; // 自己的坦克
    private final Vector<EnemyTank> etv = new Vector<>(); // 敌人的坦克
    private final int ets = 3; // 敌人坦克的数量

    public MyPanel() {
        ht = new HeroTank(100, 100, 10); // 初始化自己的坦克
        for (int i = 0; i < ets; i++) { // 初始化敌人的坦克
            EnemyTank et = new EnemyTank((i + 1) * 100, 0, 10);
            et.setDirect(2); // 设置坦克方向
            Shot shot = new Shot(et.getX() + 20, et.getY() + 60, et.getDirect());
            et.getShots().add(shot); // 给敌人坦克加入一颗子弹
            new Thread(shot).start();
            etv.add(et);
        }
    }

    public static boolean hitTank(Shot s, EnemyTank et) {
        switch (et.getDirect()) {
            case 0: // 上
            case 2: // 下
                if (s.getX() > et.getX() && s.getX() < et.getX() + 40
                        && s.getY() > et.getY() && s.getY() < et.getY() + 60) {
                    s.setLive(false);
                    et.setLive(false);
                }
                return true;
            case 1:
            case 3:
                if (s.getX() > et.getX() && s.getX() < et.getX() + 60
                        && s.getY() > et.getY() && s.getY() < et.getY() + 40) {
                    s.setLive(false);
                    et.setLive(false);
                }
                return true;
        }

        return false;
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

    public void drawShot(int x, int y, Graphics g) {
        g.setColor(Color.WHITE);
        g.fill3DRect(x, y, 1, 1, false);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1000, 750); // 填充矩形，默认黑色
        drawTank(ht.getX(), ht.getY(), g, ht.getDirect(), 1); // 画自己的坦克
        for (EnemyTank et : etv) { // 画敌人的坦克
            if (et.isLive()) {
                drawTank(et.getX(), et.getY(), g, et.getDirect(), 0);
                for (int i = 0; i < et.getShots().size(); i++) { // 画敌人坦克的子弹
                    Shot shot = et.getShots().get(i);
                    if (shot.isLive()) {
                        drawShot(shot.getX(), shot.getY(), g);
                    } else {
                        et.getShots().remove(shot); // 删除该子弹
                    }
                }
            } /*else {
                etv.remove(et);
            }*/
        }

        // 画出 ht 子弹
        Shot shot = ht.getShot();
        if (shot != null && shot.isLive()) {
            drawShot(shot.getX(), shot.getY(), g);
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
            ht.setDirect(0);
            ht.moveUp();
        } else if (keyCode == KeyEvent.VK_D) {
            ht.setDirect(1);
            ht.moveRight();
        } else if (keyCode == KeyEvent.VK_S) {
            ht.setDirect(2);
            ht.moveDown();
        } else if (keyCode == KeyEvent.VK_A) {
            ht.setDirect(3);
            ht.moveLeft();
        } else if (keyCode == KeyEvent.VK_J) {
            System.out.println("用户按下了J，开始射击");
            ht.shotEnemy();
        }

        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    /**
     * 每隔 50 ms，重绘
     */
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            // 判断是否击中了敌人的坦克
            if (ht.getShot() != null && ht.getShot().isLive()) {
                // 遍历所有敌人坦克
                for (EnemyTank et : etv) {
                    hitTank(ht.getShot(), et);
                }
            }

            repaint();
        }
    }
}
