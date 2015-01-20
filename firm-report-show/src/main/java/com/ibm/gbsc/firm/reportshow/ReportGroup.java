/*
 * IBM Corporation.
 * Copyright (c) 2014 All Rights Reserved.
 */

package com.ibm.gbsc.firm.reportshow;

import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * 类作用：报表组
 *
 * @author Johnny@cn.ibm.com 使用说明：
 */
@Entity
@Table(name = "FR_T_RPT_SHOW_GROUP")
@Cacheable
public class ReportGroup {
	private String groupCode;
	private String groupName;
	private List<ReportLet> reportLets;

	/**
	 * @return the groupCode
	 */
	@Id
	public String getGroupCode() {
		return groupCode;
	}

	/**
	 * @param groupCode
	 *            the groupCode to set
	 */
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName
	 *            the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return the reportLets
	 */
	@ManyToMany
	@JoinTable(name = "FR_V_RPT_SHOW_GROUP_LET", joinColumns = { @JoinColumn(name = "GROUP_CODE") }, inverseJoinColumns = { @JoinColumn(name = "REPORT_CODE") })
	public List<ReportLet> getReportLets() {
		return reportLets;
	}

	/**
	 * @param reportLets
	 *            the reportLets to set
	 */
	public void setReportLets(List<ReportLet> reportLets) {
		this.reportLets = reportLets;
	}

}
