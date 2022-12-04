package cn.edu.jou.tankbattle;

import javax.swing.*;

public class Game extends JFrame {
    MyPanel mp; // 定义 MyPanel

    public static void main(String[] args) {
        new Game();
    }

    public Game() {
        mp = new MyPanel();
        this.add(mp); // 添加面板，也就是游戏的绘图区
        this.setSize(1000, 750);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
