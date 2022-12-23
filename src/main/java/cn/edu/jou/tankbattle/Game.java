package cn.edu.jou.tankbattle;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Scanner;

public class Game extends JFrame {
    MyPanel mp; // 定义 MyPanel

    public Game(String key) throws IOException {
        mp = new MyPanel(key);
        Thread thread = new Thread(mp);
        thread.start(); // 启动重绘线程
        this.add(mp); // 添加面板，也就是游戏的绘图区
        this.setSize(1300, 780);
        this.addKeyListener(mp); // 让 JFrame 监听 mp 的键盘事件
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    Recorder.keepRecord();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) throws IOException {
        System.out.println("请输入选择 1：新游戏 2：继续上局");
        String key = new Scanner(System.in).next();
        new Game(key);
    }
}
