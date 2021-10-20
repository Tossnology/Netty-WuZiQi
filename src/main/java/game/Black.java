package game;

public class Black {
    public static void main(String[] args) {
        GameModel.getInstance().self.setNickname("黑方");
        GameModel.getInstance().self.setGroup(Group.BLACK);
        WuZiQiFrame frame = WuZiQiFrame.getInstance();
    }
}
