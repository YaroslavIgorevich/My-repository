package ua.com.yaroslav.statistics;
import java.sql.Timestamp;

// Contains logs about last 16 connections
public class LogRecord {
	
	// Number of fields is used to return table data for building HTML
	public static final int FIELDS_NUM = 6;
	
	// Client IP
	private String srcIp;
	
	// requested URI
	private String uri;
	
	// Timestamp
	private Timestamp timestamp;
	
	// Bytes sent
	private int sentBytes;
	
	// Bytes received
	private int recievedBytes;
	
	// Connection speed
	private double speed;
	
	public LogRecord(String srcIp, String uri, Timestamp timestamp,
			int recievedBytes) {
		this.srcIp = srcIp;
		this.uri = uri;
		this.timestamp = timestamp;		
		this.recievedBytes = recievedBytes;		
	}

	public String getSrcIp() {
		return srcIp;
	}

	public void setSrcIp(String srcIp) {
		this.srcIp = srcIp;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public int getSentBytes() {
		return sentBytes;
	}

	public void setSentBytes(int sentBytes) {
		this.sentBytes = sentBytes;
	}

	public int getRecievedBytes() {
		return recievedBytes;
	}

	public void setRecievedBytes(int recievedBytes) {
		this.recievedBytes = recievedBytes;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}	
	
}
