<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form> 
	<acme:input-textbox code="auditor.audit-record.form.label.subject" path="subject"/>
	<acme:input-textarea code="auditor.audit-record.form.label.assesment" path="assesment"/>
	<acme:input-checkbox code="auditor.audit-record.form.label.mark" path="mark"/>
	<acme:input-moment code="auditor.audit-record.form.label.initDate" path="startDate"/>
	<acme:input-moment code="auditor.audit-record.form.label.endDate" path="endDate"/>
	<acme:input-moment code="auditor.audit-record.form.label.moreInfo" path="moreInfo"/>
	
	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode == true}">
			<acme:submit code="auditor.audit-record.form.button.update" action="/auditor/audit-record/update"/>
			<acme:submit code="auditor.audit-record.form.button.delete" action="/auditor/audit-record/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="auditor.audit-record.form.button.create" action="/auditor/audit-record/create?masterId=${masterId}"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>
