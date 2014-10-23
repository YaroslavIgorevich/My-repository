package ua.com.yaroslav.statistics;

import ua.com.yaroslav.httpserver.RequestInfo;

// Server statistics container
public class ServerStats {
	
	// Total connections number
	private int totalConnectionsNumber;	
	
	// IP table
	private IPTable ipTable;
	
	// URL table
	private URLTable urlTable;
	
	// Connections log
	private ConnectionsLog log;
	
	public ServerStats() {
		ipTable = new IPTable();
		urlTable = new URLTable();
		log = new ConnectionsLog();
	}	
	
	public synchronized void updateStats(RequestInfo requestInfo) {
		incTotalConnectionsNumber();		
		ipTable.updateTable(requestInfo);
		log.addLog(requestInfo);
	}
	
	public synchronized void updateLastConnection(int responseSize, double speed) {			
		updateLastConnectionSentBytes(responseSize);
		updateLastConnectionSpeed(speed);
	}
	
	public void updateLastConnectionSentBytes(int responseSize) {		
		log.getLogs().get(0).setSentBytes(responseSize);
	}
	
	public void updateLastConnectionSpeed(double speed) {		
		log.getLogs().get(0).setSpeed(speed);
	}
	
    private void incTotalConnectionsNumber() {
		totalConnectionsNumber++;
	}    
    
	public int getTotalConnectionsNumber() {
		return totalConnectionsNumber;
	}

	public IPTable getIpTable() {
		return ipTable;
	}

	public void setIpTable(IPTable ipTable) {
		this.ipTable = ipTable;
	}

	public URLTable getUrlTable() {
		return urlTable;
	}

	public void setUrlTable(URLTable urlTable) {
		this.urlTable = urlTable;
	}

	public ConnectionsLog getLog() {
		return log;
	}

	public void setLog(ConnectionsLog log) {
		this.log = log;
	}		
}
