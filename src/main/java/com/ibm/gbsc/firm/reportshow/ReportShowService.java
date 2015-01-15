/*
 * IBM Corporation.
 * Copyright (c) 2014 All Rights Reserved.
 */

package com.ibm.gbsc.firm.reportshow;

import java.util.List;

/**
 * 类作用：
 *
 * @author Johnny@cn.ibm.com 使用说明：
 */
public interface ReportShowService {
	ReportGroup getReportGroupById(String groupCode);

	List<ReportLet> searchReportLets();

}
