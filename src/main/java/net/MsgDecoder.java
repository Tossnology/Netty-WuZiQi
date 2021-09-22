package net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MsgDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < 8) return;

        byteBuf.markReaderIndex();

        MsgType type = MsgType.values()[byteBuf.readInt()];

        int length = byteBuf.readInt();

        if (byteBuf.readableBytes() < length) {
            byteBuf.resetReaderIndex();
            return;
        }

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Msg msg = (Msg) Class.forName("net." + type).getDeclaredConstructor().newInstance();

        msg.parse(bytes);

        list.add(msg);
    }
}
