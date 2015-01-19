/*
 * IBM Corporation.
 * Copyright (c) 2014 All Rights Reserved.
 */

package com.ibm.gbsc.firm.reportshow;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;
import javax.persistence.Table;

import com.ibm.gbsc.utils.vo.AuditVO;

/**
 * 类作用：报表展示内容
 *
 * @author Johnny@cn.ibm.com 使用说明：
 */
@Entity
@Table(name = "FR_T_RPT_SHOW_LET_CONTENT")
@NamedQueries({
        @NamedQuery(name = "ReportContent.byGroup", query = "select rc from ReportContent rc where rc.dataDate= :dataDate and "
                + "rc.nodeCode = :nodeCode and rc.reportLet in "
                + "(SELECT rl.reportCode FROM  ReportGroup rg, IN (rg.reportLets) rl WHERE rg.groupCode = :groupCode)", hints = {
                @QueryHint(name = "org.hibernate.readOnly", value = "true"), @QueryHint(name = "org.hibernate.cacheable", value = "true") }),
        @NamedQuery(name = "ReportContent.cacheInfoByDate", query = "SELECT rl, rc FROM ReportContent rc RIGHT JOIN rc.reportLet rl "
                + "on rc.dataDate = :dataDate order by rl.seq", hints = { @QueryHint(name = "org.hibernate.readOnly", value = "true"),
                @QueryHint(name = "org.hibernate.cacheable", value = "true") }) })
public class ReportContent extends AuditVO {
	/**
	 *
	 */
	private static final long serialVersionUID = -6907496568557668441L;
	private Long id;
	/**
	 * 报表数据日期，如20140930
	 */
	private String dataDate;
	/**
	 * 机构代码
	 */
	private String nodeCode;
	/**
	 * 内容的URI,如图片URI（包括生成和上传）、HTML页面URI、或是其它URL调用
	 */
	private String uri;
	/**
	 * 是否通过审核了，null代表未审核，f代表失败，t代表审批通过
	 */
	private Boolean approved;

	/**
	 * 备注内容，格式为:[UserCode@YYYY-MM-DD]内容<br/>
	 * 如：A009008@2014-09-30 EL值应该是0.1%,但报表中是0.2%
	 */
	private String comment;
	private ReportLet reportLet;

	/**
	 * @return the id
	 */
	@Override
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the reportDate
	 */
	public String getDataDate() {
		return dataDate;
	}

	/**
	 * @param reportDate
	 *            the reportDate to set
	 */
	public void setDataDate(String reportDate) {
		this.dataDate = reportDate;
	}

	/**
	 * @return the nodeCode
	 */
	public String getNodeCode() {
		return nodeCode;
	}

	/**
	 * @param nodeCode
	 *            the nodeCode to set
	 */
	public void setNodeCode(String nodeCode) {
		this.nodeCode = nodeCode;
	}

	/**
	 * @return the uri
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * @param uri
	 *            the uri to set
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}

	/**
	 * @return the approved
	 */
	public Boolean getApproved() {
		return approved;
	}

	/**
	 * @param approved
	 *            the approved to set
	 */
	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	/**
	 * @return the comment
	 */
	@Column(name = "CMNT")
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment
	 *            the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return the reportLet
	 */
	@ManyToOne
	@JoinColumn(name = "REPORT_CODE", nullable = false)
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
}
