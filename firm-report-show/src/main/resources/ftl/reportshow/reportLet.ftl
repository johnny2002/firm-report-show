<#assign sf = JspTaglibs["http://www.springframework.org/tags/form"] >
<#assign c = JspTaglibs["http://java.sun.com/jsp/jstl/core"] >
<#assign fmt = JspTaglibs["http://java.sun.com/jsp/jstl/fmt"] >
<#assign disp = JspTaglibs["http://displaytag.sf.net"] >
<@fmt.setBundle basename="i18n/report-show-messages" />
<#-- 展示Report小窗口 -->
<#macro let reportContent>
<div class="formfloat">
	<div class="palette">
		<div class="formtitle">
            <h4>${reportContent.reportLet.reportName}</h4>
            <p>
				<#if reportContent.reportLet.approvalRequired>
                <span id="${reportContent.reportLet.reportCode}">
                	<#if reportContent.approved>
					已审批
                    <input class="buexamine1" title="未审批" onclick="rptUpdateAudit('${reportContent.reportLet.reportCode}','${reportContent.nodeCode}','${reportContent.dataDate}','0')" type="button"> 
					<#else>
					未审批
                    <input class="buexamine1" title="已审批" onclick="rptUpdateAudit('${reportContent.reportLet.reportCode}','${reportContent.nodeCode}','${reportContent.dataDate}','1')" type="button"> 
					</#if>
                </span>
                </#if>
				<#if reportContent.reportLet.commentAllowed>
                <input class="buedit" title="文字文件上传" onclick="rptModifyComment('${reportContent.reportLet.reportCode}','${reportContent.nodeCode}','${reportContent.dataDate}')" type="button">
                </#if>
                <input class="busearch" title="查看历史数据" onclick="rptShowHistory('${reportContent.reportLet.reportCode}','${reportContent.nodeCode}','${reportContent.dataDate}')" type="button"> 
                <input class="bufavorites" title="加入收藏" onclick="rptAddFavorite('${reportContent.reportLet.reportCode}')" type="button"> 
            </p>
		</div> <#-- formtitle -->
		<a onclick="loadAttribute('${reportContent.reportLet.reportCode}');" href="####">${reportContent.reportLet.reportName}</a>
	</div>
	<div style="width:446px;height:290px" class="formimg">
		<a target="indicator" href="/riskdashboard/report/showIndicator.htm?reportId=${reportContent.reportLet.reportCode}">
		<img src="/attachment/report/cognos-cache/RTP0030040040.png" height="290px" width="392px">
		</a>
	</div>
</div>
</#macro>