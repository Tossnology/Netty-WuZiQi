package game;

public class Player {
    private String nickname;
    private Group group;
    private String address;

    public Player() {
    }

    public Player(String nickname, Group group) {
        this.nickname = nickname;
        this.group = group;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
