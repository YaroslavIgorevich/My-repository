package ua.com.yaroslav.statistics;

// HTMLBuilder uses classes that implement this interface to build html table
public interface TableIf {	
	String getLabel();
	String[] getHeaders();
	String[][] getData();	
}
