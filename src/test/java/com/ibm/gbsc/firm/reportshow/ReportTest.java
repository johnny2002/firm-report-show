package com.ibm.gbsc.firm.reportshow;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.testng.annotations.Test;

/**
 * Unit test for simple App.
 */
@ContextConfiguration(locations = { "classpath:conf/spring/test-dao.xml", "classpath:conf/spring/test-res.xml",
        "classpath:conf/spring/test-biz.xml" })
@TransactionConfiguration(defaultRollback = false)
public class ReportTest extends AbstractTransactionalTestNGSpringContextTests {
	@PersistenceContext
	private EntityManager em;

	/**
	 * Rigourous Test :-)
	 */
	@Test
	public void testApp() {
		ReportGroup grp = em.find(ReportGroup.class, "G_CREDIT");
		for (ReportLet let : grp.getReportLets()) {
			System.out.println(let);
			System.out.println(let == null ? "null" : let.getReportCode());
		}
	}
}
