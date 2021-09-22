package net;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {
    private static final Client INSTANCE = new Client();

    private Channel channel;

    private Client() {};

    public static Client getInstance() {
        return INSTANCE;
    }

    public Channel getChannel() {
        return channel;
    }

    public void start() {
        EventLoopGroup group = new NioEventLoopGroup(1);

        Bootstrap b = new Bootstrap();

        b.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new MsgDecoder());
                        socketChannel.pipeline().addLast(new MsgEncoder());
                        socketChannel.pipeline().addLast(new ClientHandler());
                    }
                });
        try {
            ChannelFuture cf = b.connect("localhost", 8888).sync();
            channel = cf.channel();
            channel.closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public void send(Msg msg) {
        channel.writeAndFlush(msg);
    }
}
