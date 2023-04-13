<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<jstl:choose>
		<jstl:when test="${_command == 'show'}">
			<acme:input-textbox code="authenticated.activity.list.label.title" path="title" readonly="true"/>
			<acme:input-textarea code="authenticated.activity.list.label.abstract" path="abstract$" readonly="true"/>
			<acme:input-url code="authenticated.activity.list.label.moreInfo" path="moreInfo" readonly="true"/>
			<acme:input-textbox code="authenticated.activity.list.label.type" path="type" readonly="true"/>
			<acme:input-moment code="authenticated.activity.list.label.startDate" path="startDate" readonly="true"/>
			<acme:input-moment code="authenticated.activity.list.label.endDate" path="endDate" readonly="true"/>
		</jstl:when>
		<jstl:otherwise>
			<acme:input-textbox code="authenticated.activity.list.label.title" path="title"/>
			<acme:input-textarea code="authenticated.activity.list.label.abstract" path="abstract$"/>
			<acme:input-url code="authenticated.activity.list.label.moreInfo" path="moreInfo"/>
			<acme:input-select code="authenticated.activity.list.label.type" path="type" choices="${types}"/>
			<acme:input-moment code="authenticated.activity.list.label.startDate" path="startDate"/>
			<acme:input-moment code="authenticated.activity.list.label.endDate" path="endDate"/>
		</jstl:otherwise>
	</jstl:choose>
	
	<acme:submit test="${_command == 'create'}" code="authenticated.activity.form.button.create" 
	action="/student/activity/create?enrolmentId=${param.enrolmentId}"/>
	<acme:submit test="${_command == 'update'}" code="authenticated.activity.form.button.update" 
	action="/student/activity/update"/>
	<acme:button test="${_command == 'show'}" code="authenticated.activity.form.button.update" 
	action="/student/activity/update?id=${id}"/>
	<acme:submit test="${_command == 'show'}" code="authenticated.enrolment.form.button.delete" 
	action="/student/activity/delete?id=${id}"/>

</acme:form>