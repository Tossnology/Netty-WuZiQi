package net;

import game.GameModel;

public class ReadyMsg extends Msg{
    @Override
    public MsgType getMsgType() {
        return MsgType.ReadyMsg;
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
        GameModel.getInstance().isGoing++;
    }
}
