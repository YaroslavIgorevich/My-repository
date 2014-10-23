package ua.com.yaroslav.statistics;

import java.util.ArrayList;

// Contains information about redirection requests (i.e. "/redirect?url=")
public class URLTable implements TableIf {	
	
	private final String URL_TABLE_LABEL = "URL table";
	private final String[] URL_HEADERS   = {"url", "redirect_number"};
	
	private ArrayList<URLTableRecord> records;

	public URLTable() {
		records = new ArrayList<>();
	}
	
	public void updateTable(String url) {
		if (exist(url)) {
			getRecordByURL(url).incRedirectNumber();
		} else {
			URLTableRecord record = new URLTableRecord(url, 1);
			records.add(record);
		}
	}
	
	private boolean exist(String url) {
		for (URLTableRecord record : records) {
			if (record.getUrl().equals(url)) {
				return true;
			}
		}
		return false;
	}
	
	private URLTableRecord getRecordByURL(String url) {
		for (URLTableRecord record : records) {
			if (record.getUrl().equals(url)) {
				return record;
			}
		}
		return null;
	}
	
	@Override
	public String getLabel() {		
		return URL_TABLE_LABEL;
	}

	@Override
	public String[] getHeaders() {
		return URL_HEADERS;
	}
	
	@Override
	public String[][] getData() {
		String[][] data = new String[records.size()][URLTableRecord.FIELDS_NUM];
		
		for (int i = 0; i < records.size(); i++) {
			URLTableRecord currentRecord = records.get(i);
			data[i][0] = currentRecord.getUrl();
			data[i][1] = String.valueOf(currentRecord.getRedirectNumber());
		}
		return data;
	}	
	
	public ArrayList<URLTableRecord> getRecords() {
		return records;
	}

	public void setRecords(ArrayList<URLTableRecord> records) {
		this.records = records;
	}	
}
