<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form> 
	<acme:input-textbox code="auditor.audit-record.form.label.subject" path="subject"/>
	<acme:input-textarea code="auditor.audit-record.form.label.assesment" path="assesment"/>
	<acme:input-textbox code="auditor.audit-record.form.label.mark" path="mark"/>
	<acme:input-textbox code="auditor.audit-record.form.label.moreInfo" path="moreInfo"/>
	<acme:input-moment code="auditor.audit-record.form.label.initDate" path="initDate"/>
	<acme:input-moment code="auditor.audit-record.form.label.endDate" path="endDate"/>
	
	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete')}">
			<acme:submit code="auditor.audit-record.form.button.update" action="/auditor/audit-record/update"/>
			<acme:submit code="auditor.audit-record.form.button.delete" action="/auditor/audit-record/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="auditor.audit-record.form.button.create" action="/auditor/audit-record/create?masterId=${masterId}"/>
		</jstl:when>
		
			<jstl:if test="${acme:anyOf(_command, 'update|delete' && draftMode == false}">
		<tags:modal modalBody="authenticated.bulletin.form.button.create.modal.body" 
		modalTitle="authenticated.bulletin.form.button.create.modal.title" 
		buttonName="authenticated.bulletin.form.button.create" 
		action="/administrator/bulletin/create"  
		modalButtonCancelName="authenticated.bulletin.form.button.close" 
		modalButtonSubmitName="authenticated.bulletin.form.button.create"/>
	</jstl:if>
				
	</jstl:choose>
</acme:form>
