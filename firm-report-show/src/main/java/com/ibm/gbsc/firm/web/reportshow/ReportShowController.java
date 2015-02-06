/*
 * IBM Corporation.
 * Copyright (c) 2014 All Rights Reserved.
 */

package com.ibm.gbsc.firm.web.reportshow;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ibm.gbsc.firm.reportshow.ReportCacheInfo;
import com.ibm.gbsc.firm.reportshow.ReportContent;
import com.ibm.gbsc.firm.reportshow.ReportShowService;

/**
 * 类作用：
 *
 * @author Johnny@cn.ibm.com 使用说明：
 */
@Controller
@RequestMapping("/report-show")
public class ReportShowController {
	@Inject
	ReportShowService reportShowService;

	@RequestMapping(value = "/groups/{group}", method = RequestMethod.GET)
	public String showReportGroup(@PathVariable String group, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String dataDate = "20141231";// (String)session.getAttribute(ReportConstants.PARAM_DATA_DATE);
		String nodeCode = "0000";// (String)session.getAttribute(ReportConstants.PARAM_NODE_CODE);
		List<ReportContent> reportContents = reportShowService.getReportContentByGroup(group, nodeCode, dataDate);
		model.addAttribute("reportContents", reportContents);
		return "/reportshow/reportGroupTab.ftl";
	}

	@RequestMapping(value = "/report-caches", method = RequestMethod.GET)
	public String getReportCacheInfos(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String dataDate = "20141231";// (String)session.getAttribute(ReportConstants.PARAM_DATA_DATE);
		List<ReportCacheInfo> reportCacheInfos = reportShowService.getReportCacheInfoList(dataDate);
		model.addAttribute("reportCacheInfos", reportCacheInfos);
		return "/reportshow/reportCache.ftl";
	}
}
