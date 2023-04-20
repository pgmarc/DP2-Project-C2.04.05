<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<acme:form> 
	<acme:input-textbox code="auditor.audit-record.form.label.subject" path="subject"/>
	<acme:input-textarea code="auditor.audit-record.form.label.assesment" path="assesment"/>
	<acme:input-textbox code="auditor.audit-record.form.label.mark" path="mark"/>
	<acme:input-url code="auditor.audit-record.form.label.moreInfo" path="moreInfo"/>
	<acme:input-moment code="auditor.audit-record.form.label.initDate" path="initDate"/>
	<acme:input-moment code="auditor.audit-record.form.label.endDate" path="endDate"/>
	
	<jstl:if test="${_command == 'create' && draftMode }">
		<acme:submit code="auditor.audit-record.form.button.create" action="/auditor/audit-record/create?masterId=${masterId}"/>
	</jstl:if>
	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode}">
			<acme:submit code="auditor.audit-record.form.button.update" action="/auditor/audit-record/update"/>
			<acme:submit code="auditor.audit-record.form.button.delete" action="/auditor/audit-record/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create' && !draftMode}">
			<tags:modal modalBody="auditor.audit-record.form.button.create.modal.body" 
				modalTitle="auditor.audit-record.form.button.create.modal.title" 
				buttonName="auditor.audit-record.form.button.create" 
				action="/auditor/audit-record/create?masterId=${masterId}"  
				modalButtonCancelName="auditor.audit-record.form.button.close" 
				modalButtonSubmitName="auditor.audit-record.form.button.create"/>
	</jstl:when>
				
	</jstl:choose>
</acme:form>
