package ua.com.yaroslav.httpserver;

import ua.com.yaroslav.statistics.ServerStats;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders.Values;
import static io.netty.handler.codec.http.HttpHeaders.Names.*;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

// Responses to "/hello" request 
public class HelloResponseWriter extends ResponseWriter implements Runnable {		
	
	// Output message
	public static final String HELLO_MESSAGE = "Hello world";	
	
	public HelloResponseWriter(ChannelHandlerContext context,  
			ServerStats stats, RequestInfo reqInfo) {		
		super(context, stats, reqInfo);
	}
	
	public void run() {		
		writeResponse();		
	}	
	
	public void writeResponse() {		
		FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, 
				Unpooled.wrappedBuffer(HELLO_MESSAGE.getBytes()));
		response.headers().set(CONTENT_TYPE, "text/plain");
		response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
		
		int responseSize = response.toString().getBytes().length;		
		
		long startTime = System.nanoTime();
		if (!isKeepAlive()) {
			getContext().write(response).addListener(ChannelFutureListener.CLOSE);				
		} else {
			response.headers().set(CONNECTION, Values.KEEP_ALIVE);			
			getContext().writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);					
		}
		long endTime = System.nanoTime();	
		
		double speed = calculateSpeed(startTime, endTime, responseSize);
		getStats().updateLastConnection(responseSize, speed);
	}
}

