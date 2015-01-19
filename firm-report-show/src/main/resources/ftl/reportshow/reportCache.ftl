<#import "/macros/spring.ftl" as spring>
<#assign sf = JspTaglibs["http://www.springframework.org/tags/form"] >
<#assign c = JspTaglibs["http://java.sun.com/jsp/jstl/core"] >
<#assign fmt = JspTaglibs["http://java.sun.com/jsp/jstl/fmt"] >
<#assign disp = JspTaglibs["http://displaytag.sf.net"] >

<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<#include RscPage> 
<@fmt.setBundle basename="i18n/report-show-messages" />
<title>报表缓存管理</title>
</head>
<body>
	<@disp.table uid="reportCacheInfo" list=reportCacheInfos excludedParams="*" class="resultTable">
		<@disp.caption>报表缓存列表</@disp.caption>
		<@disp.column title="报表代码" property="reportLet.reportCode" />
		<@disp.column title="报表名称" property="reportLet.reportName" />
		<@disp.column title="报表频率"> <@fmt.message key="reportshow.reportlet.frequence."+reportCacheInfo.reportLet.frequence /> </@disp.column>
		<@disp.column title="报表来源类型" ><@fmt.message key="reportshow.reportlet.sourceType."+reportCacheInfo.reportLet.sourceType /> </@disp.column>
		<@disp.column title="报表来源地址" property="reportLet.sourceURL" />
		<@disp.column title="报表所属机型类型"> <@fmt.message key="reportshow.reportlet.ownerType."+reportCacheInfo.reportLet.ownerType /> </@disp.column>
		<@disp.column title="报表缓存" property="reportContent.uri"/>
		<@disp.column title="报表缓存生成人" property="reportContent.createUser"/>
		<@disp.column title="报表缓存生成时间" property="reportContent.createTime"/>
	</@disp.table>
<ul>

</ul>
</body>
</html>