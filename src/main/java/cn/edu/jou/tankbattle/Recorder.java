package cn.edu.jou.tankbattle;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 该类用于记录相关信息，和文件交互
 */
public class Recorder {
    private static int allEnemyTankNum = 0;
    private static String recordFile = "src/record.txt";

    public static void keepRecord() throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(recordFile))) {
            bw.write(allEnemyTankNum + "");
            bw.newLine();
        }
    }

    public static int getAllEnemyTankNum() {
        return allEnemyTankNum;
    }

    public static void setAllEnemyTankNum(int allEnemyTankNum) {
        Recorder.allEnemyTankNum = allEnemyTankNum;
    }

    public static void addAllEnemyTankNum() {
        allEnemyTankNum++;
    }
}
