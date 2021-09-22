package net;

import game.GameModel;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class JoinSuccessMsg extends Msg{
    String address;
    GameModel gm = GameModel.getInstance();

    public JoinSuccessMsg() {}

    public JoinSuccessMsg(String address) {
        this.address = address;
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.JoinSuccessMsg;
    }

    @Override
    public byte[] toBytes() {
        return address.getBytes();
    }

    @Override
    public void parse(byte[] bytes) {
        this.address = new String(bytes);
    }

    @Override
    public void handle() {
        System.out.println(address + " 进入了房间");

        if (!address.equals(gm.self.getAddress())) {
            gm.opponent.setAddress(address);
        }
    }
}
