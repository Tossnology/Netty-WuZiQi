package net;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerHandler extends SimpleChannelInboundHandler<Msg>{
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Msg msg) throws Exception {
        System.out.println(msg);
        for (Channel c : RoomServer.getInstance().getChannels()) {
            if (ctx.channel() != c) {
                c.writeAndFlush(msg);
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 尝试进入房间");
        if (RoomServer.getInstance().getChannels().size() < 2) {
            System.out.println(ctx.channel().remoteAddress() + " 进入了房间");
            RoomServer.getInstance().getChannels().add(ctx.channel());
            RoomServer.getInstance().getChannels().writeAndFlush(new JoinSuccessMsg(ctx.channel().remoteAddress().toString()));
            return;
        }
        System.out.println(ctx.channel().remoteAddress() + " 进入房间失败，房间已满！");
        ctx.channel().writeAndFlush(new JoinFailMsg());
        ctx.channel().close();
    }
}
