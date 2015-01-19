/*
 * IBM Corporation.
 * Copyright (c) 2014 All Rights Reserved.
 */

package com.ibm.gbsc.firm.reportshow;

import java.util.Map;

/**
 * 类作用：
 *
 * @author Johnny@cn.ibm.com 使用说明：
 */
public interface ReportContentGenerator {
	public String runAsHTML(String url, Map<String, String> params);

	public byte[] runAsPicture(String url, Map<String, String> params);
}
