package net;

import game.GameModel;

public class CancelReadyMsg extends Msg{
    @Override
    public MsgType getMsgType() {
        return MsgType.CancelReadyMsg;
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
        GameModel.getInstance().isGoing--;
    }
}
