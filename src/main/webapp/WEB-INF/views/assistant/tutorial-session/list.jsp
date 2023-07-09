<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="assistant.tutorialsession.list.label.title" path="title"/>
	<acme:list-column code="assistant.tutorialsession.list.label.abstrac" path="abstrac"/>
</acme:list>

<acme:button test="${showCreate}" code="assistant.tutorialsession.list.button.create" action="/assistant/tutorial-session/create?tutorialId=${tId}"/>


