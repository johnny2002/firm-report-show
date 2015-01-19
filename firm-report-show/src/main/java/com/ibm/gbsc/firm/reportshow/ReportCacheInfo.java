/*
 * IBM Corporation.
 * Copyright (c) 2014 All Rights Reserved.
 */

package com.ibm.gbsc.firm.reportshow;

/**
 * 类作用：
 *
 * @author Johnny@cn.ibm.com 使用说明：
 */
public class ReportCacheInfo {
	private ReportLet reportLet;
	private ReportContent reportContent;

	/**
	 * @param reportLet
	 * @param reportContent
	 */
	public ReportCacheInfo(ReportLet reportLet, ReportContent reportContent) {
		super();
		this.reportLet = reportLet;
		this.reportContent = reportContent;
	}

	/**
	 * @return the reportLet
	 */
	public ReportLet getReportLet() {
		return reportLet;
	}

	/**
	 * @param reportLet
	 *            the reportLet to set
	 */
	public void setReportLet(ReportLet reportLet) {
		this.reportLet = reportLet;
	}

	/**
	 * @return the reportContent
	 */
	public ReportContent getReportContent() {
		return reportContent;
	}

	/**
	 * @param reportContent
	 *            the reportContent to set
	 */
	public void setReportContent(ReportContent reportContent) {
		this.reportContent = reportContent;
	}

}
