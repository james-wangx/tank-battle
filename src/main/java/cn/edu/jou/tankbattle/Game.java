package cn.edu.jou.tankbattle;

import javax.swing.*;

public class Game extends JFrame {
    MyPanel mp; // 定义 MyPanel

    public static void main(String[] args) {
        new Game();
    }

    public Game() {
        mp = new MyPanel();
        Thread thread = new Thread(mp);
        thread.start(); // 启动重绘线程
        this.add(mp); // 添加面板，也就是游戏的绘图区
        this.setSize(1000, 750);
        this.addKeyListener(mp); // 让 JFrame 监听 mp 的键盘事件
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
