/*
 * IBM Corporation.
 * Copyright (c) 2014 All Rights Reserved.
 */

package com.ibm.gbsc.firm.reportshow;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
	public List<ReportLet> searchReportLets() {
		// TODO Auto-generated method stub
		return null;
	}

}
