package com.ibm.gbsc.reportadapter.cognos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.testng.annotations.Test;

import com.ibm.gbsc.reportadapter.cognos.CMBean;
import com.ibm.gbsc.reportadapter.cognos.ReportBean;
import com.ibm.gbsc.reportadapter.cognos.ReportParameter;

@ContextConfiguration(locations = { "classpath:test-irmp-report-dao.xml", "classpath:test-irmp-report-res.xml" })
@TransactionConfiguration(defaultRollback = false)
public class ReportBeanTest extends AbstractTestNGSpringContextTests{
	@Autowired ReportBean reportBean;
	@Autowired CMBean cmBean;
	
	@Test
	public void testReportRun() throws Exception{
		String reportPath = "/content/folder[@name='02信用风险管理']/folder[@name='0201信用风险概览']"
				+ "/report[@name='信用风险概览']";
		ReportParameter[] param = new ReportParameter[0];
		reportBean.runReportAsHTMLFrag(reportPath, param );
	}
}
