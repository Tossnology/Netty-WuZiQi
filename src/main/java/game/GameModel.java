package game;

import net.Client;
import net.PutMsg;

import java.awt.*;

public class GameModel {
    private static GameModel INSTANCE = new GameModel();

    Board board = new Board();
    public Player self = new Player();
    public Player opponent = new Player();
    int turn = 0;
    public int isGoing = 0;

    private GameModel() {

    }

    public void paint(Graphics g) {
        board.paint(g);
    }

    public static GameModel getInstance() {
        return INSTANCE;
    }

    public void put(int x, int y, boolean handle) {
        if (board.put(turn, x, y)) {
            turn++;
        }
        if (board.isEnd) {
            WuZiQiFrame.getInstance().repaint();
            if (!handle) Client.getInstance().getChannel().writeAndFlush(new PutMsg(x, y));
            turn--;
            String winner = turn % 2 == 0 ? "黑" : "白";
            System.out.println("游戏结束，" + winner +"胜！");
            System.exit(0);
        }
    }
}
