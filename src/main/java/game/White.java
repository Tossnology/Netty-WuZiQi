package game;

public class White {
    public static void main(String[] args) {
        GameModel.getInstance().self.setNickname("白方");
        GameModel.getInstance().self.setGroup(Group.WHITE);
        WuZiQiFrame frame = WuZiQiFrame.getInstance();
    }
}
