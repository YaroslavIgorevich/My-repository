package ua.com.yaroslav.httpserver;

import static io.netty.handler.codec.http.HttpHeaders.Names.*;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import ua.com.yaroslav.statistics.ServerStats;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders.Values;

//Responses to "/" request
public class RootResponseWriter extends ResponseWriter {	
	
	// Output message
	public static final String ROOT_MESSAGE = "Wellcome to web server!";
	
	public RootResponseWriter(ChannelHandlerContext context, ServerStats stats, 
			RequestInfo reqInfo) {
		super(context, stats, reqInfo);		
	}	
	
	public void writeResponse() {		
		FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, 
				Unpooled.wrappedBuffer(ROOT_MESSAGE.getBytes()));
		response.headers().set(CONTENT_TYPE, "text/plain");
		response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
		
		int responseSize = response.toString().getBytes().length;	
		
		// Mark start time
		long startTime = System.nanoTime();
		if (!isKeepAlive()) {			
			getContext().writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);				
		} else {			
			response.headers().set(CONNECTION, Values.KEEP_ALIVE);			
			getContext().writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);				
		}
		// Mark end time
		long endTime = System.nanoTime();
		
		// Calculating speed
		double speed = calculateSpeed(startTime, endTime, responseSize);
		getStats().updateLastConnection(responseSize, speed);
	}
}
