<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:input-textbox code="auditor.audit-record.list.label.subject" path="subject" width="20%"/>
	<acme:input-textarea code="auditor.audit-record.list.label.assesment" path="assesment"  width="40%"/>
	<acme:input-checkbox code="auditor.audit-record.list.label.mark" path="mark" width="20%"/>
	<acme:input-moment code="auditor.audit-record.list.label.initDate" path="startDate" width="20%"/>
	<acme:input-moment code="auditor.audit-record.list.label.endDate" path="endDate" width="20%"/>
	<acme:input-moment code="auditor.audit-record.list.label.moreInfo" path="moreInfo" width=40%"/>
</acme:list>

<acme:button test="${showCreate}" code="auditor.audit-record.list.button.create" action="/auditor/audit-record/create?masterId=${masterId}"/>