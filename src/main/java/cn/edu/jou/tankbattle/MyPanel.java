package cn.edu.jou.tankbattle;

import javax.swing.*;
import java.awt.*;

/**
 * 坦克大战的绘图区
 */
public class MyPanel extends JPanel {
    Hero hero; // 定义自己的坦克

    public MyPanel() {
        hero = new Hero(100, 100); // 初始化自己的坦克
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1000, 750); // 填充矩形，默认黑色
        drawTank(hero.getX(), hero.getY(), g, 0, 0); // 画坦克
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
            case 0: // 我们的坦克
                g.setColor(Color.cyan);
                break;
            case 1: // 敌人的坦克
                g.setColor(Color.yellow);
                break;
        }

        // 根据坦克的方向，绘制坦克
        switch (direct) {
            case 0:
                g.fill3DRect(x, y, 10, 60, false); // 画出坦克左侧轮子
                g.fill3DRect(x + 30, y, 10, 60, false); // 画出坦克右侧轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false); // 画出坦克中间矩形
                g.fillOval(x + 10, y + 20, 20, 20); // 画出坦克圆形盖子
                g.drawLine(x + 20, y + 30, x + 20, y); // 画出炮筒
                break;
            case 1:
                break;
            default:
                System.out.println("暂时不处理");
        }
    }
}
