package ua.com.yaroslav.httpserver;

import ua.com.yaroslav.statistics.ServerStats;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders.Values;
import static io.netty.handler.codec.http.HttpHeaders.Names.*;
import static io.netty.handler.codec.http.HttpResponseStatus.SEE_OTHER;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

// Responses to "redirect" request
public class RedirectResponseWriter extends ResponseWriter {
	
	// URL to redirect
	private final String newLocation;
		
	public RedirectResponseWriter(ChannelHandlerContext context,  
			String newLocation, ServerStats stats, RequestInfo reqInfo) {
		super(context, stats, reqInfo);		
		this.newLocation = newLocation;
		stats.getUrlTable().updateTable(newLocation);
	}

	@Override
	public void writeResponse() {		
		FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, SEE_OTHER);
		response.headers().set(LOCATION, newLocation);
		
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
