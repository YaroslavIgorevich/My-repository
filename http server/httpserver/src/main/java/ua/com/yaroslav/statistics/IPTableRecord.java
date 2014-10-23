package ua.com.yaroslav.statistics;

import java.util.ArrayList;
import java.util.Date;
import ua.com.yaroslav.httpserver.RequestInfo;

// Represents one record in IP table
public class IPTableRecord {
	
	// Number of fields is used to return table data for building HTML
	public static final int FIELDS_NUM = 4;
	
	// Client IP
	private String ipAddress;
	
	// Number of requests from this IP
	private int requestNumber;
	
	// Number of unique requests for this IP
	private int uniqueRequestNumber;
	
	// Time of last request from this IP
	private Date lastRequestTime;
	private ArrayList<String> requestedUris;
	
	public IPTableRecord(String ipAddress, int requestNumber,
			int uniqueRequestNumber, RequestInfo reqInfo) {		
		this.ipAddress = ipAddress;
		this.requestNumber = requestNumber;
		this.uniqueRequestNumber = uniqueRequestNumber;
		this.lastRequestTime = reqInfo.getRequestTime();
		requestedUris = new ArrayList<>();
		requestedUris.add(reqInfo.getRequestUri());
	}
	
	public void updateRecord(RequestInfo reqInfo) {
		incrementRequestNumber();
		lastRequestTime = reqInfo.getRequestTime();
		if (!alreadyRequested(reqInfo.getRequestUri())) {
			requestedUris.add(reqInfo.getRequestUri());
			uniqueRequestNumber = requestedUris.size();
		}
	}
	
	private boolean alreadyRequested(String reqUri) {
		for (String uri : requestedUris) {
			if (uri.equals(reqUri)) {
				return true;
			}
		}
		return false;
	}
	
	public void incrementRequestNumber() {
		requestNumber++;
	}
	
	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public int getRequestNumber() {
		return requestNumber;
	}

	public void setRequestNumber(int requestNumber) {
		this.requestNumber = requestNumber;
	}

	public int getUniqueRequestNumber() {
		return uniqueRequestNumber;
	}

	public void setUniqueRequestNumber(int uniqueRequestNumber) {
		this.uniqueRequestNumber = uniqueRequestNumber;
	}

	public Date getLastRequestTime() {
		return lastRequestTime;
	}

	public void setLastRequestTime(Date lastRequestTime) {
		this.lastRequestTime = lastRequestTime;
	}	
}
