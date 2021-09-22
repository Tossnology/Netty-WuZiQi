package game;

import net.Client;

import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) {


        GameModel.getInstance().self.setNickname(args[0]);
        if (args[1].equals("black")) {
            GameModel.getInstance().self.setGroup(Group.BLACK);
        } else if (args[1].equals("white")) {
            GameModel.getInstance().self.setGroup(Group.WHITE);
        } else {
            return;
        }
        WuZiQiFrame frame = WuZiQiFrame.getInstance();
    }
}
