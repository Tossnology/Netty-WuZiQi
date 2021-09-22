package net;

public class JoinFailMsg extends Msg{
    public JoinFailMsg() {}

    @Override
    public MsgType getMsgType() {
        return MsgType.JoinFailMsg;
    }

    @Override
    public byte[] toBytes() {
        return new byte[0];
    }

    @Override
    public void parse(byte[] bytes) {

    }

    @Override
    public void handle() {
        System.out.println("进入房间失败，房间已满！");
        Client.getInstance().getChannel().close();
        System.exit(0);
    }
}
