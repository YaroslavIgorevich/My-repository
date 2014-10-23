package ua.com.yaroslav.httpserver;

import static io.netty.handler.codec.http.HttpHeaders.Names.*;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import ua.com.yaroslav.statistics.HTMLBuilder;
import ua.com.yaroslav.statistics.ServerStats;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders.Values;

//Responses to "/status" request
public class StatusResponseWriter extends ResponseWriter {	
	private String statusContent;
	
	public StatusResponseWriter(ChannelHandlerContext context,
			ServerStats stats, RequestInfo reqInfo) {
		super(context, stats, reqInfo);
		this.statusContent = new HTMLBuilder().buildHTML(stats);
	}

	public void writeResponse() {		
		FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, 
				Unpooled.wrappedBuffer(statusContent.getBytes()));
		response.headers().set(CONTENT_TYPE, "text/html");
		response.headers().set(CONTENT_LENGTH, response.content().readableBytes());		
		
		int responseSize = response.toString().getBytes().length;
		getStats().updateLastConnectionSentBytes(responseSize);
		new HTMLBuilder().buildHTML(getStats());
		
		response = new DefaultFullHttpResponse(HTTP_1_1, OK, 
				Unpooled.wrappedBuffer(statusContent.getBytes()));
		response.headers().set(CONTENT_TYPE, "text/html");
		response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
			
		if (!isKeepAlive()) {
			getContext().writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);				
		} else {
			response.headers().set(CONNECTION, Values.KEEP_ALIVE);						
			getContext().writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);			
		}
	}
}
