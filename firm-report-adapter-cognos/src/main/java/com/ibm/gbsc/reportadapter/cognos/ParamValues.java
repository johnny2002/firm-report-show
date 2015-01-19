package com.ibm.gbsc.reportadapter.cognos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class ParamValues {
	private String reportId;
	private String reportingDate;
	private HashMap<String, String> reportingDateMap;
	private String node;
	private HashMap<String, String> nodeMap;

	public String getReportingDate() {
		return this.reportingDate;
	}

	public void setReportingDate(String reportingDate) {
		this.reportingDate = reportingDate;
	}

	public HashMap<String, String> getReportingDateMap() {
		return this.reportingDateMap;
	}

	public void setReportingDateMap(HashMap<String, String> reportingDateMap) {
		this.reportingDateMap = reportingDateMap;
	}

	public String getNode() {
		return this.node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public HashMap<String, String> getNodeMap() {
		return this.nodeMap;
	}

	public void setNodeMap(HashMap<String, String> nodeMap) {
		this.nodeMap = nodeMap;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getReportId() {
		return this.reportId;
	}

	public String getRefId() {
		return getReportId() + "_" + getReportingDate() + "_" + (this.node == null ? "ALL" : this.node);
	}

	public ReportParameter[] createReportParameter(String freq, int range) {
		ReportParameter[] params = new ReportParameter[3];
		try {
			SimpleDateFormat dateValue = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat dateOutput = new SimpleDateFormat("yyyyMMdd");
			Date date = dateValue.parse(this.reportingDate);
			Calendar end = Calendar.getInstance();
			end.setTime(date);
			Calendar start = Calendar.getInstance();
			start.setTime(date);

			ReportParameter dbt = new ReportParameter();
			dbt.setName("DBT");
			params[0] = dbt;

			ReportParameter det = new ReportParameter();
			det.setName("DET");
			params[1] = det;

			ReportParameter nd = new ReportParameter();
			nd.setName("ND");
			nd.setValue(this.node);
			params[2] = nd;

			if ("M".equals(freq)) {
				start.add(2, 1 - range);
				dbt.setValue(dateOutput.format(start.getTime()));
				end.add(2, 1);
				end.add(5, -1);
			} else if ("Q".equals(freq)) {
				int q = (start.get(2) + 1) % 3;
				start.add(2, 1 - range * 3 - q);
				end.add(2, 1 - q);
				end.add(5, -1);
			}
			dbt.setValue(dateOutput.format(start.getTime()));
			det.setValue(dateOutput.format(end.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return params;
	}

	public static void main(String[] arg) {
		ParamValues v = new ParamValues();
		v.setReportingDate("2011-5-1");
		v.createReportParameter("Q", 3);
	}
}
