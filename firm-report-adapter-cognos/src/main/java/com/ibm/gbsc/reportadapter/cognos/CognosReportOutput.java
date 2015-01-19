package com.ibm.gbsc.reportadapter.cognos;

import com.cognos.developer.schemas.bibus._3.AsynchDetailReportOutput;

public class CognosReportOutput {
	public static final int REP_HTML = 0;
	public static final int REP_XML = 1;
	public static final int REP_PDF = 2;
	public static final int REP_CSV = 3;
	public static final int REP_HTML_FRAG = 4;
	public static final int REP_MHT = 5;
	public static final int REP_SINGLEXLS = 6;
	public static final int REP_XLS = 7;
	public static final int REP_XLWA = 8;
	AsynchDetailReportOutput reportOutput;

	public CognosReportOutput(AsynchDetailReportOutput reportOutput) {
		super();
		this.reportOutput = reportOutput;
	} 
	
	public String[] getOutputPages(){
		return reportOutput.getOutputPages();
	}
	
}
