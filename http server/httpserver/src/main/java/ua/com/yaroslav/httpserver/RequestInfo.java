package ua.com.yaroslav.httpserver;

import java.util.Date;

// Contains information about HTTP request
public class RequestInfo {
	
	private Date requestTime;
	private String requestUri;
	private int size;
	private boolean isKeepAlive;
	private String srcIp;
	
	public RequestInfo(Date requestTime, String requestUri, int size,
			boolean isKeepAlive, String ip) {
		this.requestTime = requestTime;
		this.requestUri = requestUri;
		this.size = size;
		this.isKeepAlive = isKeepAlive;
		this.srcIp = ip;
	}

	public Date getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}

	public String getRequestUri() {
		return requestUri;
	}

	public void setRequestUri(String requestUri) {
		this.requestUri = requestUri;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public boolean isKeepAlive() {
		return isKeepAlive;
	}

	public void setKeepAlive(boolean isKeepAlive) {
		this.isKeepAlive = isKeepAlive;
	}

	public String getIp() {
		return srcIp;
	}

	public void setIp(String ip) {
		this.srcIp = ip;
	}	
}
