package cn.edu.jou.tankbattle;

import java.io.*;
import java.util.Vector;

/**
 * 该类用于记录相关信息，和文件交互
 */
public class Recorder {
    private static int allEnemyTankNum = 0;
    private static String recordFile = "src/record.txt";
    private static Vector<EnemyTank> enemyTanks = null;
    private static Vector<Node> nodes = new Vector<>();

    public static void keepRecord() throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(recordFile))) {
            bw.write(allEnemyTankNum + "");
            bw.newLine();
            // 遍历敌人坦克的 Vector
            for (int i = 0; i < enemyTanks.size(); i++) {
                EnemyTank enemyTank = enemyTanks.get(i);
                if (enemyTank.isLive) {
                    String record = enemyTank.getX() + " " + enemyTank.getY() + " " + enemyTank.getDirect();
                    bw.write(record);
                    bw.newLine();
                }
            }
        }
    }

    public static Vector<Node> getNodesAndEnemyTankRec() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(recordFile))) {
            allEnemyTankNum = Integer.parseInt(br.readLine());
            String line;
            while ((line = br.readLine()) != null) {
                String[] xyd = line.split(" ");
                Node node = new Node(Integer.parseInt(xyd[0]), Integer.parseInt(xyd[1]), Integer.parseInt(xyd[2]));
                nodes.add(node);
            }
        }

        return nodes;
    }

    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
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
