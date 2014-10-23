package ua.com.yaroslav.statistics;

// Represents one record in URL table
public class URLTableRecord {
	
	// Number of fields is used to return table data for building HTML
	public static final int FIELDS_NUM = 2;
	
	// URL to redirect
	private String url;
	
	// Number of redirections to this URL
	private int redirectNumber;
	
	public URLTableRecord(String url, int redirectNumber) {		
		this.url = url;
		this.redirectNumber = redirectNumber;
	}
	
	public void incRedirectNumber() {
		redirectNumber++;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getRedirectNumber() {
		return redirectNumber;
	}

	public void setRedirectNumber(int redirectNumber) {
		this.redirectNumber = redirectNumber;
	}	
}
