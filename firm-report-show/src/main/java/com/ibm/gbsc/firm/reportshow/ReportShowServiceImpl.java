/*
 * IBM Corporation.
 * Copyright (c) 2014 All Rights Reserved.
 */

package com.ibm.gbsc.firm.reportshow;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 类作用：
 *
 * @author Johnny@cn.ibm.com 使用说明：
 */
@Service
@Transactional(readOnly = true)
public class ReportShowServiceImpl implements ReportShowService {
	Logger log = LoggerFactory.getLogger(getClass());
	@PersistenceContext
	EntityManager em;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ibm.gbsc.firm.reportshow.ReportShowService#getReportGroupById(java
	 * .lang.String)
	 */
	@Override
	public ReportGroup getReportGroupById(String groupCode) {
		// TODO Auto-generated method stub
		ReportGroup group = em.find(ReportGroup.class, groupCode);
		log.debug("Group code {} has {} ReportLets", group.getGroupName(), group.getReportLets().size());
		return group;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.gbsc.firm.reportshow.ReportShowService#searchReportLets()
	 */
	@Override
	public List<ReportLet> getReportLets() {
		return em.createNamedQuery("ReportLet.all", ReportLet.class).getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ibm.gbsc.firm.reportshow.ReportShowService#getReportContentByGroup
	 * (java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<ReportContent> getReportContentByGroup(String groupCode, String nodeCode, String dataDate) {
		TypedQuery<ReportContent> rcQuery = em.createNamedQuery("ReportContent.byGroup", ReportContent.class);
		rcQuery.setParameter("dataDate", dataDate);
		rcQuery.setParameter("nodeCode", nodeCode);
		rcQuery.setParameter("groupCode", groupCode);
		return rcQuery.getResultList();
	}

	@Override
	public List<ReportCacheInfo> getReportCacheInfoList(String dataDate) {
		List<ReportCacheInfo> rlets = new ArrayList<ReportCacheInfo>(100);
		HashSet<String> reportCodes = new HashSet<String>(200);
		@SuppressWarnings("rawtypes")
		List resultList = em.createNamedQuery("ReportContent.cacheInfoByDate").setParameter("dataDate", dataDate).getResultList();
		for (Object obj : resultList) {
			Object[] rst = (Object[]) obj;
			ReportLet rl = (ReportLet) rst[0];
			if (!reportCodes.contains(rl.getReportCode())) {
				reportCodes.add(rl.getReportCode());
				ReportContent rc = (ReportContent) rst[1];
				rlets.add(new ReportCacheInfo(rl, rc));
			}
		}
		return rlets;
	}
}
