package com.ibm.gbsc.reportadapter.cognos;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cognos.developer.schemas.bibus._3.AsynchDetailParameters;
import com.cognos.developer.schemas.bibus._3.AsynchDetailReportOutput;
import com.cognos.developer.schemas.bibus._3.AsynchOptionBoolean;
import com.cognos.developer.schemas.bibus._3.AsynchOptionEnum;
import com.cognos.developer.schemas.bibus._3.AsynchOptionInt;
import com.cognos.developer.schemas.bibus._3.AsynchReply;
import com.cognos.developer.schemas.bibus._3.AsynchReplyStatusEnum;
import com.cognos.developer.schemas.bibus._3.BaseParameter;
import com.cognos.developer.schemas.bibus._3.MultilingualString;
import com.cognos.developer.schemas.bibus._3.Option;
import com.cognos.developer.schemas.bibus._3.OutputEncapsulationEnum;
import com.cognos.developer.schemas.bibus._3.Parameter;
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

@Component
public class ReportBean extends BaseBean {
	private static Logger log = LoggerFactory.getLogger(ReportBean.class);
	private final ReportService_ServiceLocator reportLocator = new ReportService_ServiceLocator();;

	// private ReportService_PortType reportService;
	// private AsynchDetailReportOutput reportOutput = null;

	public ReportBean() {
	}

	public ReportParameter[] getParameterList(String reportPath) throws RemoteException, MalformedURLException, ServiceException {
		ReportService_PortType reportService = reportLocator.getreportService(new URL(getCognosAppUrl()));
		((ReportServiceStub) reportService).setHeader("http://developer.cognos.com/schemas/bibus/3/", "biBusHeader", getBIBusHeader());

		BaseParameter[] params = new Parameter[0];

		AsynchReply response = reportService.getParameters(new SearchPathSingleObject(reportPath), new ParameterValue[0], new Option[0]);

		if (!response.getStatus().equals(AsynchReplyStatusEnum.conversationComplete)) {
			while (!response.getStatus().equals(AsynchReplyStatusEnum.conversationComplete)) {
				response = reportService.wait(response.getPrimaryRequest(), new ParameterValue[0], new Option[0]);
			}

		}

		for (int i = 0; i < response.getDetails().length; i++) {
			if (!(response.getDetails()[i] instanceof AsynchDetailParameters)) {
				continue;
			}
			params = ((AsynchDetailParameters) response.getDetails()[i]).getParameters();
		}

		if ((params != null) && (params.length > 0)) {
			ReportParameter[] rp = new ReportParameter[params.length];
			for (int i = 0; i < params.length; i++) {
				ReportParameter p = new ReportParameter();
				p.setName(params[i].getName());
				p.setType(params[i].getType().getValue());
				p.setRequired(params[i].getCapabilities().length <= 0);
				System.out.println(p.getName() + "," + p.getType() + "," + p.isRequired());

				rp[i] = p;
			}
			return rp;
		}
		return null;
	}

	public String runReportAsHTML(String reportPath, ReportParameter[] params) throws Exception {
		String[] result = runReport(reportPath, params, 0).getOutputPages();
		String output = "";
		for (int idx = 0; idx < result.length; idx++) {
			output = output + replaceSubstring(result[idx], "../", getCognosApp());
		}
		output = replaceSubstring(output, "?SM", getCognosAppUrl() + "?SM");
		output = replaceSubstring(output, "strOldImg.replace(/\\x28/g, \"%28\")", "strOldImg.replace(/\\x5cx28/g, \"%28\")");
		output = replaceSubstring(output, "strOldImg.replace(/\\x29/g, \"%29\")", "strOldImg.replace(/\\x5cx29/g, \"%29\")");
		return output;
	}

	public CognosReportOutput runReportAsHTMLFrag(String reportPath, ReportParameter[] reportParams) throws Exception {
		return new CognosReportOutput(runReport(reportPath, reportParams, 4));
	}

	public String[] runReportAsXML(String reportPath, ReportParameter[] params) throws Exception {
		return runReport(reportPath, params, 1).getOutputPages();
	}

	public AsynchDetailReportOutput runReport(String reportPath, ReportParameter[] params, int outputType) throws Exception {
		log.debug("getCognosAppUrl()=" + getCognosAppUrl());
		ReportService_PortType reportService = reportLocator.getreportService(new URL(getCognosAppUrl()));
		if (getUserName() != null && getUserName().length() > 0) {
			((ReportServiceStub) reportService).setHeader("http://developer.cognos.com/schemas/bibus/3/", "biBusHeader", getBIBusHeader());
		}
		ParameterValue[] parameters = null;

		if ((params != null) && (params.length > 0)) {
			parameters = new ParameterValue[params.length];
			for (int i = 0; i < parameters.length; i++) {
				ParameterValue param = new ParameterValue();
				param.setName(params[i].getName());
				SimpleParmValueItem item = new SimpleParmValueItem();
				item.setUse(params[i].getValue());
				ParmValueItem[] pvi = new ParmValueItem[1];
				pvi[0] = item;
				param.setValue(pvi);
				parameters[i] = param;
			}
		}
		// outputType=0;
		Option[] runOptions = setOptions(outputType);
		log.debug("cognos report path: " + reportPath);
		// log.debug("aaaaaaaaaaa11111111111=" + parameters.length);

		AsynchReply res = reportService.run(new SearchPathSingleObject(reportPath), parameters, runOptions);
		log.debug("aaaaaaaaaaa333333333" + (res == null));
		if (res.getStatus() == AsynchReplyStatusEnum.complete) {
			for (int i = 0; i < res.getDetails().length; i++) {
				if (!(res.getDetails()[i] instanceof AsynchDetailReportOutput)) {
					continue;
				}
				System.out.println("aaaaaaaaaaa444444");
				return (AsynchDetailReportOutput) res.getDetails()[i];
			}
		}
		throw new Exception("Report not completed");
	}

	public boolean hasNextPage(AsynchReply rsr) throws RemoteException {
		if (rsr == null) {
			return false;
		}

		int numSecondaryRequests = rsr.getSecondaryRequests().length;

		for (int i = 0; i < numSecondaryRequests; i++) {
			if (rsr.getSecondaryRequests()[i].getName().toString().compareTo("nextPage") == 0) {
				return true;
			}
		}
		return false;
	}

	public static String replaceSubstring(String str, String pattern, String replace) {
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

	public Option[] setOptions(int outputType) {
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
		String[] reportFormat = setFormatByType(outputType);

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

	public static void saveToFile(String outputPath, String output) throws IOException {
		String outputText = output;
		outputText = replaceSubstring(outputText, "strOldImg.replace(/\\x5cx28/g, \"%28\")", "strOldImg.replace(/\\x28/g, \"%28\")");
		outputText = replaceSubstring(outputText, "strOldImg.replace(/\\x5cx29/g, \"%29\")", "strOldImg.replace(/\\x29/g, \"%29\")");

		if (outputPath == null) {
			outputPath = "d:/reportrunner.html";
		}

		FileOutputStream fs = new FileOutputStream(outputPath);
		fs.write(outputText.getBytes());
		fs.close();
	}

	private static String[] setFormatByType(int fileType) {
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

}