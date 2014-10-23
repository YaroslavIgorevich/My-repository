package ua.com.yaroslav.statistics;

import java.sql.Timestamp;
import java.util.ArrayList;
import ua.com.yaroslav.httpserver.RequestInfo;

// Connection logs container
public class ConnectionsLog implements TableIf {
	
	private final String LOG_TABLE_LABEL   = "Log tabel";
	private final String[] LOG_HEADERS = {"src_ip", "uri", "timestamp", "bytes_sent",
			  							  "bytes_recieved", "speed"};	
	private ArrayList<LogRecord> logs;
	
	public ConnectionsLog() {
		logs = new ArrayList<>();
	}
	
	public void addLog(RequestInfo reqInfo) {
		String srcIp = reqInfo.getIp();
		Timestamp timestamp = new Timestamp(reqInfo.getRequestTime().getTime());
		String uri = reqInfo.getRequestUri();
		int recievedBytes = reqInfo.getSize();
		LogRecord newRecord = new LogRecord(srcIp, uri, timestamp, recievedBytes);
		logs.add(0, newRecord);
		if (logs.size() > 16) {
			logs.remove(logs.size() - 1);
		}
	}		
	
	@Override
	public String getLabel() {		
		return LOG_TABLE_LABEL;
	}

	@Override
	public String[] getHeaders() {
		return LOG_HEADERS;
	}
	
	@Override
	public String[][] getData() {
		String[][] data = new String[logs.size()][LogRecord.FIELDS_NUM];
		
		for (int i = 0; i < logs.size(); i++) {
			LogRecord currentRecord = logs.get(i);
			data[i][0] = currentRecord.getSrcIp();
			data[i][1] = currentRecord.getUri();
			data[i][2] = currentRecord.getTimestamp().toString();
			data[i][3] = String.valueOf(currentRecord.getSentBytes());	
			data[i][4] = String.valueOf(currentRecord.getRecievedBytes());
			data[i][5] = String.valueOf(currentRecord.getSpeed());
		}
		return data;
	}
	
	public ArrayList<LogRecord> getLogs() {
		return logs;
	}

	public void setLogs(ArrayList<LogRecord> logs) {
		this.logs = logs;
	}		
}
