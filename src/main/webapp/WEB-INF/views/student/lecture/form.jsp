<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="authenticated.lecture.list.label.title" path="title"/>
	<acme:input-textbox code="authenticated.lecture.list.label.abstract" path="lectureAbstract"/>
	<acme:input-textbox code="authenticated.lecture.list.label.learningTime" path="estimatedLearningTime"/>
	<acme:input-textbox code="authenticated.lecture.list.label.nature" path="nature"/>
	<acme:input-double code="authenticated.lecture.list.label.body" path="body"/>
	<acme:input-url code="authenticated.lecture.list.label.moreInfo" path="moreInfo"/>
	<acme:input-url code="authenticated.lecture.list.label.lecturer" path="lecturer"/>
</acme:form>
