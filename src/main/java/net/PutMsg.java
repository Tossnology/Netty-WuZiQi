package net;

import game.GameModel;
import game.WuZiQiFrame;

import java.io.*;

public class PutMsg extends Msg{
    private int x, y;

    public PutMsg() {}

    public PutMsg(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public byte[] toBytes() {
        DataOutputStream dos = null;
        ByteArrayOutputStream out = null;
        byte[] bytes = null;
        try {
            out = new ByteArrayOutputStream();
            dos = new DataOutputStream(out);
            dos.writeInt(x);
            dos.writeInt(y);
            dos.flush();
            bytes = out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bytes;
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.PutMsg;
    }

    @Override
    public void parse(byte[] bytes) {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        DataInputStream dis = null;
        try  {
            dis = new DataInputStream(bis);
            this.x = dis.readInt();
            this.y = dis.readInt();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (dis != null) {
                    dis.close();
                }
                bis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void handle() {
        GameModel.getInstance().put(x, y, true);
        WuZiQiFrame.getInstance().repaint();
    }

    @Override
    public String toString() {
        return "PutMsg{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
