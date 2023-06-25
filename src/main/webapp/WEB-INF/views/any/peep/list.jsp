<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.peep.list.label.nick" path="nick"/>
	<acme:list-column code="any.peep.list.label.instantiationMoment" path="instantiationMoment"/>
	<acme:list-column code="any.peep.list.label.title" path="title"/>
</acme:list>

<acme:button code="any.peep.form.button.publish" action="/any/peep/publish"/>
