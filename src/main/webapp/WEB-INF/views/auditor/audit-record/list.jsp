<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="auditor.audit-record.list.label.subject" path="subject"/>
	<acme:list-column code="auditor.audit-record.list.label.mark" path="mark"/>
	<acme:list-column code="auditor.audit-record.list.label.initDate" path="initDate"/>
	<acme:list-column code="auditor.audit-record.list.label.endDate" path="endDate"/>
	<acme:list-column code="auditor.audit-record.list.label.moreInfo" path="moreInfo"/>
	<acme:list-column code="auditor.audit-record.list.label.assesment" path="assesment"/>
	<acme:list-column code="auditor.audit-record.list.label.correction" path="isCorrection"/>
</acme:list>

<acme:button test="${showCreate}" code="auditor.audit-record.list.button.create" action="/auditor/audit-record/create?masterId=${masterId}"/>