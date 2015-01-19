/*
 * IBM Corporation.
 * Copyright (c) 2014 All Rights Reserved.
 */

package com.ibm.gbsc.firm.reportshow;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;
import javax.persistence.Table;

/**
 * 类作用：报表小窗口展示所需要的属性
 *
 * @author Johnny@cn.ibm.com 使用说明：
 */
@Entity
@Table(name = "FR_T_RPT_SHOW_LET")
@NamedQuery(name = "ReportLet.all", query = "from ReportLet tl", hints = { @QueryHint(name = "org.hibernate.readOnly", value = "true"),
        @QueryHint(name = "org.hibernate.cacheable", value = "true") })
public class ReportLet {
	/**
	 * 报表唯一编码
	 */
	private String reportCode;
	/**
	 * 报表名称
	 */
	private String reportName;
	/**
	 * 报表频率,如每日，每周，每月，每季度等
	 */
	private ReportFreqence frequence;
	/**
	 * ReportLet展示的数据期数，1期代表时点报表（如，某个时间点RWA分布，多期则表示是时间段报表（如，变化趋势类报表）
	 */
	private Integer dataPeriods;
	/**
	 * 报表来源类型，如cognos或其它报表自动生成报表(A)，手工上传报表(M)等
	 *
	 */
	private String sourceType;
	/**
	 * 报表在源系统中的URL，用于3级页面的内容。 其中参数值域用${参数名称}来表示，生成时用实际值替换，可用的参数：<br/>
	 * <ul>
	 * <li>${nodeCode}：机构代码</li>
	 * <li>${dateFrom}：报表数据起始日期(YYYYMMDD)，如20140930</li>
	 * <li>${dateTo}：报表数据结束日期(YYYYMMDD)，如201401231</li>
	 * </ul>
	 */
	private String sourceURL;
	/**
	 * 报表在源系统中的URL，用来生成报表内容缓存。 其中参数值域用${参数名称}来表示，生成时用实际值替换，可用的参数：<br/>
	 * <ul>
	 * <li>${nodeCode}：机构代码</li>
	 * <li>${dateFrom}：报表数据起始日期(YYYYMMDD)，如20140930</li>
	 * <li>${dateTo}：报表数据结束日期(YYYYMMDD)，如201401231</li>
	 * </ul>
	 */
	private String letSourceURL;
	/**
	 * 报表显示类型，如(P)icture, (H)tml, (U)rl
	 *
	 */
	private String displayType;

	/**
	 * ReportLet宽度（如：300px, 50%等）
	 */
	private String width;
	/**
	 * ReportLet高度度（如：300px, 50%等）
	 */
	private String height;
	/**
	 * 报表的CSS名称，如柱状，饼状，折线等
	 */
	private String styleType;
	/**
	 * 数据值的单位，如亿元
	 */
	private String valueUnit;
	/**
	 * 报表所属机构类型，(H)ead:总行, (N)ode:分行，(A)ll:全行(总行和分行都有）
	 */
	private String ownerType;
	/**
	 * 是否需要审批
	 */
	private boolean approvalRequired;
	/**
	 * 是否允许评论
	 */
	private boolean commentAllowed;
	/**
	 * 排序，一般在查询和展示多个报表时用来排序
	 */
	private int seq;

	/**
	 * @return the reportCode
	 */
	@Id
	public String getReportCode() {
		return reportCode;
	}

	/**
	 * @param reportCode
	 *            the reportCode to set
	 */
	public void setReportCode(String reportCode) {
		this.reportCode = reportCode;
	}

	/**
	 * @return the reportName
	 */
	public String getReportName() {
		return reportName;
	}

	/**
	 * @param reportName
	 *            the reportName to set
	 */
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	/**
	 * @return the frequence
	 */
	@Enumerated(EnumType.STRING)
	public ReportFreqence getFrequence() {
		return frequence;
	}

	/**
	 * @param frequence
	 *            the frequence to set
	 */
	public void setFrequence(ReportFreqence frequence) {
		this.frequence = frequence;
	}

	/**
	 * @return the dataPeriods
	 */
	public Integer getDataPeriods() {
		return dataPeriods;
	}

	/**
	 * @param dataPeriods
	 *            the dataPeriods to set
	 */
	public void setDataPeriods(Integer dataPeriods) {
		this.dataPeriods = dataPeriods;
	}

	/**
	 * @return the sourceType
	 */
	public String getSourceType() {
		return sourceType;
	}

	/**
	 * @param sourceType
	 *            the sourceType to set
	 */
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	/**
	 * @return the sourceURL
	 */
	public String getSourceURL() {
		return sourceURL;
	}

	/**
	 * @param sourceURL
	 *            the sourceURL to set
	 */
	public void setSourceURL(String sourceURL) {
		this.sourceURL = sourceURL;
	}

	/**
	 * @return the letSourceURL
	 */
	public String getLetSourceURL() {
		return letSourceURL;
	}

	/**
	 * @param letSourceURL
	 *            the letSourceURL to set
	 */
	public void setLetSourceURL(String letSourceURL) {
		this.letSourceURL = letSourceURL;
	}

	/**
	 * @return the displayType
	 */
	public String getDisplayType() {
		return displayType;
	}

	/**
	 * @param displayType
	 *            the displayType to set
	 */
	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}

	/**
	 * @return the width
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public String getHeight() {
		return height;
	}

	/**
	 * @param height
	 *            the height to set
	 */
	public void setHeight(String height) {
		this.height = height;
	}

	/**
	 * @return the styleType
	 */
	public String getStyleType() {
		return styleType;
	}

	/**
	 * @param styleType
	 *            the styleType to set
	 */
	public void setStyleType(String styleType) {
		this.styleType = styleType;
	}

	/**
	 * @return the valueUnit
	 */
	public String getValueUnit() {
		return valueUnit;
	}

	/**
	 * @param valueUnit
	 *            the valueUnit to set
	 */
	public void setValueUnit(String valueUnit) {
		this.valueUnit = valueUnit;
	}

	/**
	 * @return the ownerType
	 */
	public String getOwnerType() {
		return ownerType;
	}

	/**
	 * @param ownerType
	 *            the ownerType to set
	 */
	public void setOwnerType(String ownerType) {
		this.ownerType = ownerType;
	}

	/**
	 * @return the approveRequired
	 */
	public boolean isApprovalRequired() {
		return approvalRequired;
	}

	/**
	 * @param approveRequired
	 *            the approveRequired to set
	 */
	public void setApprovalRequired(boolean approveRequired) {
		this.approvalRequired = approveRequired;
	}

	/**
	 * @return the commentAllowed
	 */
	public boolean isCommentAllowed() {
		return commentAllowed;
	}

	/**
	 * @param commentAllowed
	 *            the commentAllowed to set
	 */
	public void setCommentAllowed(boolean commentAllowed) {
		this.commentAllowed = commentAllowed;
	}

	/**
	 * @return the order
	 */
	public int getSeq() {
		return seq;
	}

	/**
	 * @param seq
	 *            the seq to set
	 */
	public void setSeq(int seq) {
		this.seq = seq;
	}

}
