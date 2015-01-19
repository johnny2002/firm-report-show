package com.ibm.gbsc.firm.web.reportshow;

import java.io.IOException;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.gbsc.firm.reportshow.ReportLet;

public class ReportPortletBarTag extends TagSupport {

	/**
	 *
	 */
	private static final long serialVersionUID = -7149063915504895602L;

	private final Logger log = LoggerFactory.getLogger(getClass());

	private ReportLet reportLet;

	@Override
	public int doStartTag() throws JspException {
		StringBuffer sb = new StringBuffer();

		HttpSession session = pageContext.getSession();
		String dataDate = "20141231";
		String nodeCode = "0000";
		// ReportParam reportParam = (ReportParam)
		// session.getAttribute(ReportParam.REPORT_PARAM_VAR);
		// LoginUser loginUser = (LoginUser) SecurityUtil.getCurrentUser();
		// FunctionService funcSer = (FunctionService)
		// BeanFactory.getInstance().getBean(FunctionService.class);
		// Function function = funcSer.getFunctionById("A00101");
		// Collection<Role> userRoles = loginUser.getAuthorities();
		// Collection<Role> funcRoles = function.getRoles();
		// boolean isHaveRight = false;
		// for(Role role : funcRoles){
		// if(userRoles.contains(role)){
		// isHaveRight = true;
		// break;
		// }
		// }
		// here add more biz logic code
		ReportLet item = reportLet;
		sb.append("        <div class='formtitle'>").append("\r\n");
		// 暂时显示报表名称，正式发布时移除
		// if(!"C".equals(item.getReportType())){
		sb.append("            <h4>").append(item.getReportName()).append("</h4>").append("\r\n");
		// }

		sb.append("            <p>").append("\r\n");
		// picc 不用审批
		/*
		 * if ("Y".equals(item.getControld()) && isHaveRight) { if
		 * ("1".equals(item.getAudit().getStatus())) {
		 * sb.append("                <span id='audit_")
		 * .append(item.getReportCode()).append("'>已审批 ")
		 * .append(item.getAudit().getReportDate()).append("\r\n"); sb.append(
		 * "                    <input class='buexamine2' title='未审批' type='button' "
		 * ); sb.append(" onclick=\"rptUpdateAudit('")
		 * .append(item.getReportCode()).append("','")
		 * .append(item.getAudit().getNodeCode()).append("','")
		 * .append(item.getAudit().getReportDate())
		 * .append("','0')\" /> ").append("\r\n");
		 * sb.append("                </span>"); } else {
		 * sb.append("                <span id='audit_")
		 * .append(item.getReportCode()).append("'>未审批") .append("\r\n");
		 * sb.append(
		 * "                    <input class='buexamine1' title='已审批' type='button' "
		 * ); sb.append(" onclick=\"rptUpdateAudit('")
		 * .append(item.getReportCode()).append("','")
		 * .append(item.getAudit().getNodeCode()).append("','")
		 * .append(item.getAudit().getReportDate())
		 * .append("','1')\" /> ").append("\r\n");
		 * sb.append("                </span>"); } sb.append("\r\n"); }
		 */

		if (item.isCommentAllowed()) {
			sb.append("                <input class='buedit' title='文字文件上传' type='button'");
			sb.append(" onclick=\"rptModifyComment('").append(item.getReportCode()).append("','").append(nodeCode).append("','")
			        .append(dataDate).append("')\" /> ").append("\r\n");
		}

		// if ("Y".equals(item.getControlb())) {
		// sb.append("                <input class='busearch' title='查看历史数据' type='button'");
		// sb.append(" onclick=\"rptShowHistory('").append(item.getReportCode()).append("','").append(reportParam.getNodeCode()).append("','")
		// .append(reportParam.getReportDate()).append("')\" /> ").append("\r\n");
		// }

		// if ("Y".equals(item.getControlc())) {
		sb.append("                <input style='margin-top:5px;margin-right:10px' class='bufavorites' title='加入收藏' type='button' ");
		sb.append(" onclick=\"rptAddFavorite('").append(item.getReportCode()).append("')\" /> ").append("\r\n");
		// }

		sb.append("            </p>").append("\r\n");
		sb.append("        </div> <!-- formtitle -->").append("\r\n").append("\r\n");

		try {
			if (sb != null) {
				pageContext.getOut().write(sb.toString());
			}
		} catch (IOException e) {
			log.info("Could not print out value '" + reportLet + "'", e);
		}
		return super.doStartTag();
	}

	@Override
	public void release() {
		reportLet = null;
		super.release();
	}

	public ReportLet getReportLet() {
		return reportLet;
	}

	public void setReportDef(ReportLet reportLet) {
		this.reportLet = reportLet;
	}

}
