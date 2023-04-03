<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.peep.list.label.instantiationMoment" path="instantiationMoment"/>
	<acme:list-column code="any.peep.list.label.title" path="title"/>
	<acme:list-column code="any.peep.list.label.nick" path="nick"/>
	<acme:list-column code="any.peep.list.label.message" path="message"/>
	<acme:list-column code="any.peep.list.label.email" path="email"/>
	<acme:list-column code="any.peep.list.label.moreInfo" path="moreInfo"/>
</acme:list>