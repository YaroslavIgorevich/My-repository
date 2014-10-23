package ua.com.yaroslav.httpserver;

import ua.com.yaroslav.statistics.ServerStats;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

// Main class of http server. Here server is initiated and starts
public final class HttpServer {
	
	// Server port
	private final int PORT;
	
	// Statistics container
	private ServerStats stats;
	
    public HttpServer(int port) {
    	PORT = port;
    	stats = new ServerStats();
	}    
    
    public void run() throws Exception {    	
    	EventLoopGroup bossGroup = new NioEventLoopGroup();
    	EventLoopGroup workerGroup = new NioEventLoopGroup();
    	    	
    	try {
    		ServerBootstrap bootstrap = new ServerBootstrap();
    		
    		bootstrap.group(bossGroup, workerGroup)
    			.channel(NioServerSocketChannel.class)
    			.handler(new LoggingHandler(LogLevel.INFO))
    			.childHandler(new HttpServerInitializer(stats));	
    		Channel channel = bootstrap.bind(PORT).sync().channel();
    		channel.closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}   	
    }    

    public static void main(String[] args) throws Exception {
       new HttpServer(8080).run();
    }
}
