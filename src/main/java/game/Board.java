package game;

import java.awt.*;

public class Board {
    final int width = 600;
    final int height = 600;
    final int block = width/16;
    final int size = 15;
    final int biasY = 15;
    final int qiZiSize = 30;
    public boolean isEnd = false;

    public QiZi[][] state = new QiZi[size][size];

    public Board() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                state[i][j] = QiZi.NONE;
            }
        }
    }

    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.decode("#FFDEAD"));
        g.fillRect(0, 0, width, height);
        g.setColor(c);

        for (int i = 0; i < size; i++) {
            g.drawLine(block, block + i * block + biasY, size * block, block + i * block + biasY);
        }
        for (int i = 0; i < size; i++) {
            g.drawLine(block + i * block, block + biasY, block + i * block, size * block + biasY);
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int qX = block + j * block - qiZiSize / 2;
                int qY = block + i * block + biasY - qiZiSize / 2;
                if (state[i][j] == QiZi.BLACK) {
                    g.fillOval(qX, qY, qiZiSize, qiZiSize);
                }
                if (state[i][j] == QiZi.WHITE) {
                    g.setColor(Color.WHITE);
                    g.fillOval(qX, qY, qiZiSize, qiZiSize);
                    g.setColor(c);
                }
            }
        }
    }

    public boolean put(int turn, int x, int y) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int qX = block + j * block - qiZiSize / 2;
                int qY = block + i * block + biasY - qiZiSize / 2;
                if (x >= qX + qiZiSize/4 && x <= qX + qiZiSize * 3 / 4 && y >= qY + qiZiSize/4 && y <= qY + qiZiSize * 3 / 4  && state[i][j] == QiZi.NONE) {
                    state[i][j] = turn % 2 == 0 ? QiZi.BLACK : QiZi.WHITE;
                    isEnd = isEnd(i, j);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isEnd(int i, int j) {
        //横向计算
        int count = 1;
        for (int k = j - 1; k >= j - 4; k--) {
            if (k >= 0 && state[i][k] == state[i][j]) {
                count++;
            } else {
                break;
            }
        }
        for (int k = j + 1; k <= j + 4; k++) {
            if (k <= 14 && state[i][k] == state[i][j]) {
                count++;
            } else {
                break;
            }
        }
        if (count >= 5) return true;


        //纵向计算
        count = 1;
        for (int k = i - 1; k >= i - 4; k--) {
            if (k >= 0 && state[k][j] == state[i][j]) {
                count++;
            } else {
                break;
            }
        }
        for (int k = i + 1; k <= i + 4; k++) {
            if (k <= 14 && state[k][j] == state[i][j]) {
                count++;
            } else {
                break;
            }
        }
        if (count >= 5) return true;


        //斜向计算
        count = 1;
        for (int k = 1; k < 5; k++) {
            if (i - k >= 0 && j - k >= 0 && state[i - k][j - k] == state[i][j]) {
                count++;
            } else {
                break;
            }
        }
        for (int k = 1; k < 5; k++) {
            if (i + k <= 14 && j + k <= 14 && state[i + k][j + k] == state[i][j]) {
                count++;
            } else {
                break;
            }
        }
        if (count >= 5) return true;

        count = 1;
        for (int k = 1; k < 5; k++) {
            if (i + k <= 14 && j - k >= 0 && state[i + k][j - k] == state[i][j]) {
                count++;
            } else {
                break;
            }
        }
        for (int k = 1; k < 5; k++) {
            if (i - k >= 0 && j + k <= 14 && state[i - k][j + k] == state[i][j]) {
                count++;
            } else {
                break;
            }
        }

        return count >= 5;
    }
}
