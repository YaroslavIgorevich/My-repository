package ua.com.yaroslav.httpserver;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import ua.com.yaroslav.statistics.ServerStats;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.util.concurrent.EventExecutor;
import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

// Server handler, which handles requests received
public class HttpServerHandler extends ChannelInboundHandlerAdapter {
	
	// Statistics container
	private ServerStats stats;	
	
	public HttpServerHandler(ServerStats stats) {
		this.stats = stats;
	}	
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {		
		Date requestTime = new Date();
				
		if (msg instanceof HttpRequest) {			
			HttpRequest request = (HttpRequest)msg;				
			
			// Creating information container for request
			RequestInfo reqestInfo = getRequestInfo(ctx, request, requestTime);
			String requestUri = reqestInfo.getRequestUri();
			
			// Adding CONTINUE header if necessary
			if (HttpHeaders.is100ContinueExpected(request)) {
				ctx.write(new DefaultFullHttpResponse(HTTP_1_1, CONTINUE));
			}						
			
			// Processing "/" URI
			if (requestUri.equals(URIContainer.ROOT)) {			
				new RootResponseWriter(ctx, stats, reqestInfo).writeResponse();				
			}
			
			// Processing "/hello" URI
			if (requestUri.equals(URIContainer.HELLO)) {
				EventExecutor executor = ctx.executor();				
				executor.schedule(new HelloResponseWriter(ctx, stats, reqestInfo), 10000, TimeUnit.MILLISECONDS);
			}			
			
			// Processing "/redirect?url=<url>" URI
			if (requestUri.contains(URIContainer.URL)) {				
				 String url = getUrlFromUri(requestUri);
				 new RedirectResponseWriter(ctx, url, stats, reqestInfo).writeResponse();
			}
			
			// Processing "/status" URI
			if (requestUri.equals(URIContainer.STATUS)) {				
				StatusResponseWriter statusWriter = new StatusResponseWriter(ctx, stats, reqestInfo);
				statusWriter.writeResponse();
			}	
		}
	}	
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)	throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
	
	// Retrieves URL from redirect request
	private String getUrlFromUri(String requestUri) {
		String url = new String("");		
		QueryStringDecoder queryDecoder = new QueryStringDecoder(requestUri);
		Map<String, List<String>> params =  queryDecoder.parameters();
		if (!params.isEmpty()) {
			for (Entry<String, List<String>> p : params.entrySet()) {
				if (p.getKey().equals("url")) {
					List<String> valuess = (ArrayList<String>)p.getValue();
					url = valuess.get(0);					
				}						
			}
		}		
		return url;
	}
	
	// Gets source ip address
	private String getIpFromContext(ChannelHandlerContext ctx) {
		InetSocketAddress socketAddress = (InetSocketAddress)ctx.channel().remoteAddress();			
		InetAddress inetAddress = socketAddress.getAddress();
		return inetAddress.getHostAddress();
	}
	
	// Builds new request info container 
	private RequestInfo getRequestInfo(ChannelHandlerContext ctx, HttpRequest request, Date requestTime) {
		int requestSize = request.toString().getBytes().length;			
		String requestUri = request.getUri();
		boolean isKeepAlive = HttpHeaders.isKeepAlive(request);	
		String ip = getIpFromContext(ctx);
		
		return new RequestInfo(requestTime, requestUri, requestSize, isKeepAlive, ip);
	}
}
