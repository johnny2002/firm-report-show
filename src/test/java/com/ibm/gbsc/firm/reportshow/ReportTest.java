package com.ibm.gbsc.firm.reportshow;

import java.util.List;

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

	@Test
	public void testReportContent() {
		List resultList = em.createNamedQuery("ReportContent.byGroup").setParameter("dataDate", "20141231")
		        .setParameter("nodeCode", "0000").setParameter("groupCode", "G_CREDIT").getResultList();
		System.out.println("size:" + resultList.size());
	}

	@Test
	public void testReportCacheInfo() {
		String jql = "SELECT rc, rl FROM ReportContent rc RIGHT JOIN rc.reportLet rl on rc.dataDate = '20141231' order by rl.seq";
		List resultList = em.createQuery(jql).getResultList();
		System.out.println("size:" + resultList.size());
	}
}
