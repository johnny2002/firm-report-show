/*
 * IBM Corporation.
 * Copyright (c) 2014 All Rights Reserved.
 */

package com.ibm.gbsc.firm.web.reportshow;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ibm.gbsc.firm.reportshow.ReportGroup;
import com.ibm.gbsc.firm.reportshow.ReportShowService;

/**
 * 类作用：
 *
 * @author Johnny@cn.ibm.com 使用说明：
 */
@Controller
@RequestMapping("/report-show")
public class ReportShowController {
	@Autowired
	ReportShowService reportShowService;

	@RequestMapping(value = "/groups/{group}", method = RequestMethod.GET)
	public String getReportGroup(@PathVariable String group, Model model, HttpServletRequest request) {
		ReportGroup reportGroup = reportShowService.getReportGroupById(group);
		model.addAttribute(reportGroup);
		return "/reportshow/reportTab.ftl";
	}
}
