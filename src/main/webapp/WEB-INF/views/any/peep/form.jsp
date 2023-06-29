<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-moment code="any.peep.form.label.instantiationMoment" path="instantiationMoment" readonly="true"/>
	<acme:input-textbox code="any.peep.form.label.title" path="title"/>
	<acme:input-textbox code="any.peep.form.label.nick" path="nick"/>
	<acme:input-textbox code="any.peep.form.label.message" path="message"/>
	<acme:input-email code="any.peep.form.label.email" path="email"/>
	<acme:input-url code="any.peep.form.label.moreInfo" path="moreInfo"/>
	
	<acme:submit test="${ _command == 'publish' }" code="any.peep.form.button.publish" action="/any/peep/publish"/>
</acme:form>


