<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form> 
	<jstl:if test="${_command == 'show'}">
		<acme:input-textbox code="auditor.audit.form.label.auditor" path="auditor"/>
		<acme:input-textbox code="auditor.audit.form.label.code" path="code"/>
		<acme:input-textbox code="auditor.audit.form.label.conclusion" path="conclusion"/>
		<acme:input-textarea code="auditor.audit.form.label.strongPoints" path="strongPoints"/>
		<acme:input-textbox code="auditor.audit.form.label.weakPoints" path="weakPoints"/>
		<acme:input-checkbox code="auditor.audit.form.label.draftMode" path="draftMode"/>
		<acme:input-textbox code="auditor.audit.form.label.mark" path="mark"/>	 
	</jstl:if>		
</acme:form>
