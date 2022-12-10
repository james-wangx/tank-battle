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
    HeroTank ht; // 自己的坦克
    Vector<EnemyTank> etv = new Vector<>(); // 敌人的坦克
    // 定义一个 Vector 用来存放炸弹
    // 说明：当子弹击中坦克时，就加入一个 Bomb 对象 到 bombs
    Vector<Bomb> bombs = new Vector<>();
    int ets = 3; // 敌人坦克的数量
    int drawTimes = 0; // 调用 drawImage 方法的次数

    // 定义三张炸弹图片，用于显示爆炸效果
    Image image1;
    Image image2;
    Image image3;

    /**
     * 初始化面板，进行一系列初始化操作
     */
    public MyPanel() {
        // 初始化自己的坦克
        ht = new HeroTank(100, 100, 10);

        // 根据敌人坦克数量，初始化敌人坦克
        for (int i = 0; i < ets; i++) {
            EnemyTank et = new EnemyTank((i + 1) * 100, 0, 5);
            // 坦克默认方向向下
            et.setDirect(2);
            // 启动敌人坦克线程，让它动起来
            new Thread(et).start();
            // 给坦克加入一颗子弹，并立即发射
            Shot shot = new Shot(et.getX() + 20, et.getY() + 60, et.getDirect());
            et.getShots().add(shot);
            new Thread(shot).start();
            etv.add(et);
        }

        // 初始化图片对象
        image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
        image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
        image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));
    }

    /**
     * 绘制图形
     *
     * @param g the <code>Graphics</code> context in which to paint
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // 绘制背景色为黑色
        g.fillRect(0, 0, 1000, 750);
        // 绘制自己的坦克
        drawTank(ht.getX(), ht.getY(), g, ht.getDirect(), 1);
        // 绘制敌人的坦克
        drawEnemyTank(g);
        // 绘制自己坦克的子弹
        drawHeroShots(g);
        // 绘制爆炸效果
        drawBombs(g);
    }

    /**
     * 判断是否击中坦克
     *
     * @param s  子弹
     * @param et 坦克
     */
    public void hitTank(Shot s, EnemyTank et) {
        switch (et.getDirect()) {
            case 0: // 上
            case 2: // 下
                if (s.getX() > et.getX() && s.getX() < et.getX() + 40
                        && s.getY() > et.getY() && s.getY() < et.getY() + 60) {
                    s.setLive(false);
                    et.setLive(false);
                    // 加入一个 Bomb 对象 到 bombs
                    Bomb bomb = new Bomb(et.getX(), et.getY());
                    bombs.add(bomb);
                }
                break;
            case 1:
            case 3:
                if (s.getX() > et.getX() && s.getX() < et.getX() + 60
                        && s.getY() > et.getY() && s.getY() < et.getY() + 40) {
                    s.setLive(false);
                    et.setLive(false);
                    Bomb bomb = new Bomb(et.getX(), et.getY());
                    bombs.add(bomb);
                }
                break;
        }
    }

    /**
     * 判断是否击中敌人坦克
     */
    public void hitEnemyTank() {
        // 遍历子弹
        for (int i = 0; i < ht.shots.size(); i++) {
            Shot shot = ht.shots.get(i);
            // 遍历敌人坦克
            for (int j = 0; j < etv.size(); j++) {
                EnemyTank et = etv.get(j);
                hitTank(shot, et);
            }
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

    /**
     * 绘制子弹
     *
     * @param x 子弹左上角 x 坐标
     * @param y 子弹左上角 y 坐标
     * @param g 画笔
     */
    public void drawShot(int x, int y, Graphics g) {
        g.setColor(Color.WHITE);
        g.fill3DRect(x, y, 1, 1, false);
    }

    /**
     * 绘制敌人坦克
     *
     * @param g 画笔
     */
    public void drawEnemyTank(Graphics g) {
        for (int i = 0; i < etv.size(); i++) {
            EnemyTank et = etv.get(i);
            if (et.isLive()) {
                drawTank(et.getX(), et.getY(), g, et.getDirect(), 0);
                // 绘制敌人坦克的子弹
                for (int j = 0; j < et.getShots().size(); j++) {
                    Shot shot = et.getShots().get(j);
                    if (shot.isLive()) {
                        drawShot(shot.getX(), shot.getY(), g);
                    } else {
                        et.getShots().remove(shot); // 删除该子弹
                    }
                }
            } else {
                etv.remove(et); // 删除该坦克
            }
        }
    }

    /**
     * 绘制自己坦克的子弹
     *
     * @param g 画笔
     */
    public void drawHeroShots(Graphics g) {
        for (int i = 0; i < ht.shots.size(); i++) {
            Shot shot = ht.shots.get(i);
            if (shot.isLive()) {
                drawShot(shot.getX(), shot.getY(), g);
            } else {
                ht.shots.remove(shot); // 删除该子弹
            }
        }
    }

    /**
     * 绘制爆炸效果
     *
     * @param g 画笔
     */
    public void drawBombs(Graphics g) {
        // 如果 bombs 集合中有对象，就画出
        for (int i = 0; i < bombs.size(); i++) {
            // 取出炸弹
            Bomb bomb = bombs.get(i);
            // 根据当前这个 bomb 对象的 life 值去画出对应的图片
            if (bomb.life > 6) {
                g.drawImage(image1, bomb.x, bomb.y, 60, 60, this);
            } else if (bomb.life > 3) {
                g.drawImage(image2, bomb.x, bomb.y, 60, 60, this);
            } else if (bomb.life > 0) {
                g.drawImage(image3, bomb.x, bomb.y, 60, 60, this);
            }
            /*
             * 第一次调用 drawImage 会自动调两次 paint，这样就导致调用一次 drawImage 相当于调用了三次
             * 如果是第一次打中敌人，就会迅速调用三次 drawImage ，不会等待线程休眠，从而导致看不到爆炸效果
             * 设置 drawTimes 可以解决该问题，第一次击中敌人时会连续画九次
             * 所以设置当 drawTimes <= 9 时，每次延迟 50 ms
             */
            if (++drawTimes <= 9) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            // 让炸弹的生命值减少
            bomb.lifeDown();
            // 如果 bomb life 为 0，就从 bombs 集合中删除
            if (!bomb.isLive) {
                bombs.remove(bomb);
            }
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
            if (ht.getY() > 0) {
                ht.moveUp();
            }
        } else if (keyCode == KeyEvent.VK_D) {
            ht.setDirect(1);
            if (ht.getX() + 60 < 1000) {
                ht.moveRight();
            }
        } else if (keyCode == KeyEvent.VK_S) {
            ht.setDirect(2);
            if (ht.getY() + 60 < 750) {
                ht.moveDown();
            }
        } else if (keyCode == KeyEvent.VK_A) {
            ht.setDirect(3);
            if (ht.getX() > 0) {
                ht.moveLeft();
            }
        } else if (keyCode == KeyEvent.VK_J) {
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
            hitEnemyTank();
            repaint();
        }
    }
}
