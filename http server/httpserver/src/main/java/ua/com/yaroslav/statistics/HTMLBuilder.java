package ua.com.yaroslav.statistics;

import com.hp.gagawa.java.elements.*;

// Builds HTML from server statistics
public class HTMLBuilder {
	
	private final String TOTAL_CONNS_LABEL = "Total connections: ";	
	
	public String buildHTML(ServerStats stats) {
		Html html = new Html();
		Body body = new Body();
		
		addTotalConnsParagraph(stats, body);
		
		addTableParagraph(stats.getUrlTable(), body);
		addTableParagraph(stats.getIpTable(), body);
		addTableParagraph(stats.getLog(), body);			
		
		html.appendChild(body);
		return html.write();
	}

	private void addTableParagraph(TableIf table, Body body) {
		P tableParagraph = new P();
		tableParagraph.appendChild(new Text(table.getLabel()));
			
		Table localTable = new Table();
		localTable.setBorder("solid");		
		
		addHeaders(table, localTable);		
		addDataCells(table, localTable);
			
		tableParagraph.appendChild(localTable);
		body.appendChild(tableParagraph);
	}		
	
	private void addDataCells(TableIf table, Table localTable) {
		String[][] tableData = table.getData();
		
		for (int i = 0; i < tableData.length; i++) {
			Tr newRow = new Tr();			
			for (int j = 0; j < tableData[0].length; j++) {				
				Td tableCell = new Td().appendChild(new Text(tableData[i][j]));				
				newRow.appendChild(tableCell);				
			}
			localTable.appendChild(newRow);
		}		
	}

	private void addHeaders(TableIf table, Table localTable) {
		String[] headers = table.getHeaders();
		Tr headersRow = new Tr();
		
		for (int i = 0; i < headers.length; i++) {
			Th header = new Th().appendChild(new Text(headers[i]));
			headersRow.appendChild(header);
		}
		
		localTable.appendChild(headersRow);
	}

	private void addTotalConnsParagraph(ServerStats stats, Body body) {
		int totalConnsNumber = stats.getTotalConnectionsNumber();
		body.appendChild(new P().appendChild(new Text(TOTAL_CONNS_LABEL + totalConnsNumber)));
	}
}
