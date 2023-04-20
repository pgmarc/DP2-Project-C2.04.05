
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
<jstl:choose>
		<jstl:when test="${_command == 'show'}">
			<acme:input-textbox code="lecturer.course.form.label.code" path="code" readonly="true"/>
		<acme:input-textbox code="lecturer.course.form.label.title" path="title" readonly="true"/>
		<acme:input-textarea code="lecturer.course.form.label.courseAbstract" path="courseAbstract" readonly="true"/>
		<acme:input-textbox code="lecturer.course.form.label.nature" path="nature" readonly="true"/>
		<acme:input-money code="lecturer.course.form.label.retailPrice" path="retailPrice" readonly="true"/>
		<acme:input-url code="lecturer.course.form.label.moreInfo" path="moreInfo" readonly="true"/>
		</jstl:when>
		<jstl:otherwise>
			<acme:input-textbox code="lecturer.course.form.label.code" path="code" />
			<acme:input-textbox code="lecturer.course.form.label.title" path="title" />
			<acme:input-textarea code="lecturer.course.form.label.courseAbstract" path="courseAbstract" />
			<acme:input-money code="lecturer.course.form.label.retailPrice" path="retailPrice" />
			<acme:input-url code="lecturer.course.form.label.moreInfo" path="moreInfo" />
		</jstl:otherwise>
	</jstl:choose>
	<acme:input-textbox code="lecturer.course.form.label.lecturer" path="lecturer" readonly="true"/>
	<acme:input-textbox code="lecturer.course.form.label.draftMode" path="draftMode" readonly="true"/>
	<jstl:if test="${_command == 'show'}">
		<acme:button code="lecturer.course.form.button.lectures" action="/lecturer/lecture/list-from-course?masterId=${id}"/>
	</jstl:if>
	<acme:submit test="${_command == 'update'}" code="lecturer.course.form.button.submitUpdate" 
	action="/lecturer/course/update"/>
	<acme:submit test="${_command == 'create'}" code="lecturer.course.form.button.create" 
	action="/lecturer/course/create"/>
	<jstl:if test="${_command == 'show' && draftMode == true}">
		<acme:button code="lecturer.course.form.button.update" action="/lecturer/course/update?id=${id}"/>
		<acme:submit code="lecturer.course.form.button.publish" action="/lecturer/course/publish?id=${id}"/>
		<acme:submit code="lecturer.course.form.button.delete" action="/lecturer/course/delete?id=${id}"/>
	</jstl:if>
</acme:form>
