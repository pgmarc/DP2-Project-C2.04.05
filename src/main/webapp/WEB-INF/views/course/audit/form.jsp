<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form> 
	<acme:input-textbox code="auditor.audit.form.label.code" path="code"/>
	<acme:input-textbox code="auditor.audit.form.label.conclusion" path="conclusion"/>
	<acme:input-textarea code="auditor.audit.form.label.strongPoints" path="strongPoints"/>
	<acme:input-textbox code="auditor.audit.form.label.weakPoints" path="weakPoints"/>	 
	<jstl:when test="${_command == 'create'}">
		<acme:submit code="auditor.audit.form.button.create" action="/auditor/audit/create?courseId=${courseId}"/>
	</jstl:when>		
</acme:form>
