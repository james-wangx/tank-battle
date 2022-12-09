package cn.edu.jou.tankbattle;

/**
 * 爆炸
 */
public class Bomb {
    int x; // x 坐标
    int y; // y 坐标
    int life = 9; // 生命周期
    boolean isLive = true; // 是否存活

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // 减少生命值
    // 配合出现图片的爆炸效果
    public void lifeDown() {
        if (life > 0) {
            life--;
        } else {
            isLive = false;
        }
    }
}
