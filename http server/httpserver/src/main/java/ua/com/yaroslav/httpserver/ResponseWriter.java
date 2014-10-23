package ua.com.yaroslav.httpserver;

import ua.com.yaroslav.statistics.ServerStats;
import io.netty.channel.ChannelHandlerContext;

// Responses to different requests depending on specific writer
public abstract class ResponseWriter {	
	
	// Enables a handler to interact with its pipeline and other handlers
	private ChannelHandlerContext context;
	
	// Flag for persistent connection
	private boolean isKeepAlive;
	
	// Statistics container
	private ServerStats stats;
	
	public ResponseWriter(ChannelHandlerContext context,  
			ServerStats stats, RequestInfo reqInfo) {
		this.stats = stats;
		setContext(context);
		setKeepAlive(reqInfo.isKeepAlive());
		stats.updateStats(reqInfo);
	}
		
	// Calculates speed
	public double calculateSpeed(long startTime, long endTime, int responseSize) {		
		long writingTime = endTime - startTime;		
		return responseSize * 1_000_000_000 / writingTime;
	}
	
	// Writes response
	public abstract void writeResponse();	
	
	public ChannelHandlerContext getContext() {
		return context;
	}

	public void setContext(ChannelHandlerContext context) {
		this.context = context;
	}

	public boolean isKeepAlive() {
		return isKeepAlive;
	}

	public void setKeepAlive(boolean isKeepAlive) {
		this.isKeepAlive = isKeepAlive;
	}

	public ServerStats getStats() {
		return stats;
	}		
}
