package ua.com.yaroslav.statistics;

import java.util.ArrayList;
import ua.com.yaroslav.httpserver.RequestInfo;

// IP information table
public class IPTable implements TableIf {
	
	private final String IP_TABLE_LABEL = "IP table";
	private final String[] IP_HEADERS   = {"ip", "request_number", "unique_request_number",
	 									   "last_request_time"};
	private ArrayList<IPTableRecord> records;

	public IPTable() {		
		records = new ArrayList<>();
	}
	
	public synchronized void updateTable(RequestInfo reqInfo) {
		String ipAddress = reqInfo.getIp();
		if (exist(ipAddress)) {
			IPTableRecord record = getRecordByIP(ipAddress);
			record.updateRecord(reqInfo);
		} else {			
			IPTableRecord newRecord = new IPTableRecord(ipAddress, 1, 1, reqInfo);
			records.add(newRecord);
		}
	}
	
	private boolean exist(String ipAddress) {
		for (IPTableRecord record : records) {
			if (ipAddress.equals(record.getIpAddress())) {
				return true;
			}
		}
		return false;
	}
	
	private IPTableRecord getRecordByIP(String ipAddress) {
		for (IPTableRecord record : records) {
			if (ipAddress.equals(record.getIpAddress())) {
				return record;
			}
		}
		return null;
	}
	
	@Override
	public String getLabel() {
		return IP_TABLE_LABEL;
	}
	
	@Override
	public String[] getHeaders() {
		return IP_HEADERS;
	}
	
	@Override
	public String[][] getData() {
		String[][] data = new String[records.size()][IPTableRecord.FIELDS_NUM];
		
		for (int i = 0; i < records.size(); i++) {
			IPTableRecord currentRecord = records.get(i);
			data[i][0] = currentRecord.getIpAddress();
			data[i][1] = String.valueOf(currentRecord.getRequestNumber());
			data[i][2] = String.valueOf(currentRecord.getUniqueRequestNumber());
			data[i][3] = currentRecord.getLastRequestTime().toString();			
		}
		return data;
	}	
	
	public ArrayList<IPTableRecord> getRecords() {
		return records;
	}

	public void setRecords(ArrayList<IPTableRecord> records) {
		this.records = records;
	}	
}
