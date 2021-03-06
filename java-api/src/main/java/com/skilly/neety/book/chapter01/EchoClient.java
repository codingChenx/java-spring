package com.skilly.neety.book.chapter01;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by 1254109699@qq.com on 2018/1/15.
 * document
 * 1.创建 Bootstrap
 * <p>
 * 2.指定 EventLoopGroup 来处理客户端事件。由于我们使用 NIO 传输，所以用到了 NioEventLoopGroup 的实现
 * <p>
 * 3.使用的 channel 类型是一个用于 NIO 传输
 * <p>
 * 4.设置服务器的 InetSocketAddress
 * <p>
 * 5.当建立一个连接和一个新的通道时，创建添加到 EchoClientHandler 实例 到 channel pipeline
 * <p>
 * 6.连接到远程;等待连接完成
 * <p>
 * 7.阻塞直到 Channel 关闭
 * <p>
 * 8.调用 shutdownGracefully() 来关闭线程池和释放所有资源
 */
public class EchoClient {

    private final String host;
    private final int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();                //1
            b.group(group)                                //2
                    .channel(NioSocketChannel.class)            //3
                    .remoteAddress(new InetSocketAddress(host, port))    //4
                    .handler(new ChannelInitializer<SocketChannel>() {    //5
                        @Override
                        public void initChannel(SocketChannel ch)
                                throws Exception {
                            ch.pipeline().addLast(
                                    new EchoClientHandler());
                        }
                    });

            ChannelFuture f = b.connect().sync();        //6

            f.channel().closeFuture().sync();            //7
        } finally {
            group.shutdownGracefully().sync();            //8
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 9327;
        String host = "127.0.0.1";
        if (args.length > 0) {
//            System.err.println(
//                    "Usage: " + HttpServer.class.getSimpleName() +
//                            " <port>");
//            return;
            host = args[0];
            port = Integer.parseInt(args[1]);
        }

        new EchoClient(host, port).start();
    }
}