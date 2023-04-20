
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<jstl:choose>
		<jstl:when test="${_command == 'show'}">
			<acme:input-textbox code="lecturer.lecture.form.label.title" path="title" readonly="true"/>
			<acme:input-textarea code="lecturer.lecture.form.label.lectureAbstract" path="lectureAbstract" readonly="true"/>
			<acme:input-textbox code="lecturer.lecture.form.label.nature" path="nature" readonly="true"/>
			<acme:input-textarea code="lecturer.lecture.form.label.retailPrice" path="body" readonly="true"/>
			<acme:input-url code="lecturer.lecture.form.label.moreInfo" path="moreInfo" readonly="true"/>
		</jstl:when>
		<jstl:otherwise>
			<acme:input-textbox code="lecturer.lecture.form.label.title" path="title"/>
			<acme:input-textarea code="lecturer.lecture.form.label.lectureAbstract" path="lectureAbstract"/>
			<acme:input-select code="lecturer.lecture.form.label.nature" path="nature" choices="${natures}"/>
			<acme:input-textarea code="lecturer.lecture.form.label.retailPrice" path="body"/>
			<acme:input-url code="lecturer.lecture.form.label.moreInfo" path="moreInfo"/>
		</jstl:otherwise>
	</jstl:choose>
	<acme:input-textbox code="lecturer.lecture.form.label.lecturer" path="lecturer" readonly="true"/>
	<acme:input-textbox code="lecturer.lecture.form.label.draftMode" path="draftMode" readonly="true"/>
	<acme:submit test="${_command == 'update'}" code="lecturer.lecture.form.button.submitUpdate" 
	action="/lecturer/lecture/update"/>
	<acme:submit test="${_command == 'create'}" code="lecturer.lecture.form.button.create" 
	action="/lecturer/lecture/create"/>
	<jstl:if test="${_command == 'show' && draftMode == true}">
		<acme:button code="lecturer.lecture.form.button.update" action="/lecturer/lecture/update?id=${id}"/>
		<acme:submit code="lecturer.lecture.form.button.publish" action="/lecturer/lecture/publish?id=${id}"/>
		<acme:submit code="lecturer.lecture.form.button.delete" action="/lecturer/lecture/delete?id=${id}"/>
	</jstl:if>
	
	
</acme:form>
