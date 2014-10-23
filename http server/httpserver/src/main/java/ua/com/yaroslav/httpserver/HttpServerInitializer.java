package ua.com.yaroslav.httpserver;

import ua.com.yaroslav.statistics.ServerStats;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

// Server initializer. Here we add handlers to pipeline
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {	
	
	private ServerStats stats;
	
	public HttpServerInitializer(ServerStats stats) {
		this.stats = stats;
	}
	
	@Override
	protected void initChannel(SocketChannel channel) throws Exception {
		ChannelPipeline pipeline = channel.pipeline();			
		pipeline.addLast(new HttpServerCodec());		
		pipeline.addLast(new HttpServerHandler(stats));		
	}
}
