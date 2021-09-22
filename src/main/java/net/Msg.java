package net;

public abstract class Msg {
    public abstract MsgType getMsgType();
    public abstract byte[] toBytes();
    public abstract void parse(byte[] bytes);
    public abstract void handle();
}
