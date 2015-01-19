package com.ibm.gbsc.reportadapter.cognos;

public class ReportDefinition {
	private String reportId;
	private String reportName;
	private String viewName;
	private String packageName;
	private String package2Name;
	private String paramList;
	private String frequency;
	private int range;
	private String rangeType;
	private long textId;

	public String getReportId() {
		return this.reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getReportName() {
		return this.reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getViewName() {
		return this.viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public String getPackageName() {
		return this.packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getPackage2Name() {
		return this.package2Name;
	}

	public void setPackage2Name(String package2Name) {
		this.package2Name = package2Name;
	}

	public String getParamList() {
		return this.paramList;
	}

	public void setParamList(String paramList) {
		this.paramList = paramList;
	}

	public String getFrequency() {
		return this.frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public int getRange() {
		return this.range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public String getRangeType() {
		return this.rangeType;
	}

	public void setRangeType(String rangeType) {
		this.rangeType = rangeType;
	}

	public String createReportPath() {
		StringBuffer sb = new StringBuffer("/content");

		if (this.packageName != null)
			sb.append("/folder[@name='").append(this.packageName).append("']");
		if (this.package2Name != null)
			sb.append("/folder[@name='").append(this.package2Name).append("']");
		if (this.reportName != null)
			sb.append("/report[@name='").append(this.reportName).append("']");
		return sb.toString();
	}

	public void setTextId(long textId) {
		this.textId = textId;
	}

	public long getTextId() {
		return this.textId;
	}
}
