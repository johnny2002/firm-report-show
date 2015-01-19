/*
 * IBM Corporation.
 * Copyright (c) 2014 All Rights Reserved.
 */

package com.ibm.gbsc.firm.reportshow.cognos;

import java.net.URL;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cognos.developer.schemas.bibus._3.AsynchDetailReportOutput;
import com.cognos.developer.schemas.bibus._3.AsynchOptionBoolean;
import com.cognos.developer.schemas.bibus._3.AsynchOptionEnum;
import com.cognos.developer.schemas.bibus._3.AsynchOptionInt;
import com.cognos.developer.schemas.bibus._3.AsynchReply;
import com.cognos.developer.schemas.bibus._3.AsynchReplyStatusEnum;
import com.cognos.developer.schemas.bibus._3.BiBusHeader;
import com.cognos.developer.schemas.bibus._3.CAM;
import com.cognos.developer.schemas.bibus._3.FormFieldVar;
import com.cognos.developer.schemas.bibus._3.FormatEnum;
import com.cognos.developer.schemas.bibus._3.HdrSession;
import com.cognos.developer.schemas.bibus._3.MultilingualString;
import com.cognos.developer.schemas.bibus._3.Option;
import com.cognos.developer.schemas.bibus._3.OutputEncapsulationEnum;
import com.cognos.developer.schemas.bibus._3.ParameterValue;
import com.cognos.developer.schemas.bibus._3.ParmValueItem;
import com.cognos.developer.schemas.bibus._3.ReportServiceStub;
import com.cognos.developer.schemas.bibus._3.ReportService_PortType;
import com.cognos.developer.schemas.bibus._3.ReportService_ServiceLocator;
import com.cognos.developer.schemas.bibus._3.RunOptionBoolean;
import com.cognos.developer.schemas.bibus._3.RunOptionEnum;
import com.cognos.developer.schemas.bibus._3.RunOptionMultilingualString;
import com.cognos.developer.schemas.bibus._3.RunOptionOutputEncapsulation;
import com.cognos.developer.schemas.bibus._3.RunOptionStringArray;
import com.cognos.developer.schemas.bibus._3.SearchPathSingleObject;
import com.cognos.developer.schemas.bibus._3.SimpleParmValueItem;
import com.ibm.gbsc.firm.reportshow.ReportContentGenerator;

/**
 * 类作用：
 *
 * @author Johnny@cn.ibm.com 使用说明：
 */

public class ReportContentGeneratorCognos implements ReportContentGenerator {
	Logger log = LoggerFactory.getLogger(getClass());

	private final ReportService_ServiceLocator reportLocator = new ReportService_ServiceLocator();

	private String cognosServletRoot;

	private String userName;

	private String password;

	private String namespace;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ibm.gbsc.firm.reportshow.ReportContentGenerator#runAsHTML(java.lang
	 * .String, java.util.Map)
	 */
	@Override
	public String runAsHTML(String url, Map<String, String> params) {
		String[] result = runReport(url, params, 0).getOutputPages();
		String output = "";
		for (int idx = 0; idx < result.length; idx++) {
			output = output + replaceSubstring(result[idx], "../", cognosServletRoot);
		}
		output = replaceSubstring(output, "?SM", cognosServletRoot + "?SM");
		output = replaceSubstring(output, "strOldImg.replace(/\\x28/g, \"%28\")", "strOldImg.replace(/\\x5cx28/g, \"%28\")");
		output = replaceSubstring(output, "strOldImg.replace(/\\x29/g, \"%29\")", "strOldImg.replace(/\\x5cx29/g, \"%29\")");
		return output;
	}

	String replaceSubstring(String str, String pattern, String replace) {
		int strLen = str.length();
		int patternLen = pattern.length();
		int start = 0;
		int end = 0;
		StringBuffer result = new StringBuffer(strLen);
		char[] chars = new char[strLen];

		while ((end = str.indexOf(pattern, start)) >= 0) {
			str.getChars(start, end, chars, 0);
			result.append(chars, 0, end - start).append(replace);
			start = end + patternLen;
		}

		str.getChars(start, strLen, chars, 0);
		result.append(chars, 0, strLen - start);

		return result.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ibm.gbsc.firm.reportshow.ReportContentGenerator#runAsPicture(java
	 * .lang.String, java.util.Map)
	 */
	@Override
	public byte[] runAsPicture(String url, Map<String, String> params) {
		// TODO Auto-generated method stub
		return null;
	}

	public AsynchDetailReportOutput runReport(String reportURL, Map<String, String> params, int outputType) {
		try {
			ReportService_PortType reportService = reportLocator.getreportService(new URL(cognosServletRoot));
			if (userName != null && userName.length() > 0) {
				((ReportServiceStub) reportService).setHeader("http://developer.cognos.com/schemas/bibus/3/", "biBusHeader",
				        getBIBusHeader());
			}
			ParameterValue[] parameters = null;
			parameters = new ParameterValue[params.size()];
			for (String key : params.keySet()) {
				for (int i = 0; i < parameters.length; i++) {
					ParameterValue param = new ParameterValue();
					param.setName(key);
					SimpleParmValueItem item = new SimpleParmValueItem();
					item.setUse(params.get(key));
					ParmValueItem[] pvi = new ParmValueItem[1];
					pvi[0] = item;
					param.setValue(pvi);
					parameters[i] = param;
				}
			}
			// outputType=0;
			Option[] runOptions = getRunOptions(outputType);
			log.debug("cognos report path: " + reportURL);
			// log.debug("aaaaaaaaaaa11111111111=" + parameters.length);

			AsynchReply res = reportService.run(new SearchPathSingleObject(reportURL), parameters, runOptions);
			log.debug("run result {}", res);
			if (res.getStatus() == AsynchReplyStatusEnum.complete) {
				for (int i = 0; i < res.getDetails().length; i++) {
					if (!(res.getDetails()[i] instanceof AsynchDetailReportOutput)) {
						continue;
					}
					log.debug("Got result detail[{}]", i);
					return (AsynchDetailReportOutput) res.getDetails()[i];
				}
			}
		} catch (Exception e) {
			log.error("", e);
			throw new RuntimeException("运行报表出错", e);
		}
		throw new RuntimeException("Report not completed");
	}

	BiBusHeader getBIBusHeader() {
		BiBusHeader bibus = new BiBusHeader();
		bibus.setCAM(new CAM());
		bibus.getCAM().setAction("logonAs");
		bibus.setHdrSession(new HdrSession());

		FormFieldVar[] ffs = new FormFieldVar[3];

		ffs[0] = new FormFieldVar();
		ffs[0].setName("CAMUsername");
		ffs[0].setValue(this.userName);
		ffs[0].setFormat(FormatEnum.not_encrypted);

		ffs[1] = new FormFieldVar();
		ffs[1].setName("CAMPassword");
		ffs[1].setValue(this.password);
		ffs[1].setFormat(FormatEnum.not_encrypted);

		ffs[2] = new FormFieldVar();
		ffs[2].setName("CAMNamespace");
		ffs[2].setValue(this.namespace);
		ffs[2].setFormat(FormatEnum.not_encrypted);

		bibus.getHdrSession().setFormFieldVars(ffs);
		return bibus;
	}

	Option[] getRunOptions(int outputType) {
		Option[] execReportRunOptions = new Option[6];
		RunOptionBoolean saveOutputRunOption = new RunOptionBoolean();
		RunOptionBoolean burstRunOption = new RunOptionBoolean();
		RunOptionBoolean sendByEmailRunOption = new RunOptionBoolean();
		RunOptionMultilingualString emailSubjectRunOption = new RunOptionMultilingualString();
		RunOptionBoolean emailAsAttachmentRunOption = new RunOptionBoolean();
		RunOptionStringArray outputFormat = new RunOptionStringArray();
		RunOptionBoolean promptFlag = new RunOptionBoolean();
		AsynchOptionBoolean includePrimaryRequest = new AsynchOptionBoolean();

		saveOutputRunOption.setName(RunOptionEnum.saveOutput);
		saveOutputRunOption.setValue(false);

		RunOptionOutputEncapsulation outputEncapsulation = new RunOptionOutputEncapsulation();
		outputEncapsulation.setName(RunOptionEnum.outputEncapsulation);
		outputEncapsulation.setValue(OutputEncapsulationEnum.none);

		outputFormat.setName(RunOptionEnum.outputFormat);
		String[] reportFormat = getFormatByType(outputType);

		outputFormat.setValue(reportFormat);

		promptFlag.setName(RunOptionEnum.prompt);
		promptFlag.setValue(false);

		AsynchOptionInt primaryWait = new AsynchOptionInt();
		primaryWait.setName(AsynchOptionEnum.primaryWaitThreshold);
		primaryWait.setValue(0);

		AsynchOptionInt secondaryWait = new AsynchOptionInt();
		secondaryWait.setName(AsynchOptionEnum.secondaryWaitThreshold);
		secondaryWait.setValue(0);

		includePrimaryRequest.setName(AsynchOptionEnum.alwaysIncludePrimaryRequest);
		includePrimaryRequest.setValue(true);

		burstRunOption.setName(RunOptionEnum.burst);
		burstRunOption.setValue(true);

		sendByEmailRunOption.setName(RunOptionEnum.email);
		sendByEmailRunOption.setValue(true);

		emailSubjectRunOption.setName(RunOptionEnum.emailSubject);
		MultilingualString[] emailSubject = new MultilingualString[1];
		MultilingualString subjectOfEmail = new MultilingualString();
		subjectOfEmail.setValue("Report: ");
		emailSubject[0] = subjectOfEmail;
		emailSubjectRunOption.setValue(emailSubject);

		emailAsAttachmentRunOption.setName(RunOptionEnum.emailAsAttachment);
		emailAsAttachmentRunOption.setValue(true);

		execReportRunOptions[0] = saveOutputRunOption;
		execReportRunOptions[1] = outputEncapsulation;
		execReportRunOptions[2] = promptFlag;
		execReportRunOptions[3] = primaryWait;
		execReportRunOptions[4] = secondaryWait;
		execReportRunOptions[5] = outputFormat;

		// execReportRunOptions[0] = saveOutputRunOption;
		// execReportRunOptions[1] = outputEncapsulation;
		// execReportRunOptions[2] = primaryWait;
		// execReportRunOptions[3] = secondaryWait;
		// execReportRunOptions[4] = outputFormat;
		// execReportRunOptions[5] = outputFormat;

		return execReportRunOptions;
	}

	private static String[] getFormatByType(int fileType) {
		switch (fileType) {
		case 0:
			return new String[] { "HTML" };
		case 1:
			return new String[] { "XML" };
		case 4:
			return new String[] { "HTMLFragment" };
		case 5:
			return new String[] { "MHT" };
		case 6:
			return new String[] { "singleXLS" };
		case 7:
			return new String[] { "XLS" };
		case 8:
			return new String[] { "XLWA" };
		case 2:
			return new String[] { "PDF" };
		case 3:
			return new String[] { "CSV" };
		}

		return null;
	}

	/**
	 * @param cognosServletRoot
	 *            the cognosServletRoot to set
	 */
	public void setCognosServletRoot(String cognosServletRoot) {
		this.cognosServletRoot = cognosServletRoot;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param namespace
	 *            the namespace to set
	 */
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

}
