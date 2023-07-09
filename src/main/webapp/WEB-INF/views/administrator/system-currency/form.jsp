<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="administrator.system-currency.form.label.current-currency" path="currentCurrency"/>
	<acme:input-textbox code="administrator.system-currency.form.label.supported-currencies" path="supportedCurrencies"/>
	<acme:button test="${_command=='show'}" code="administrator.system-currency.form.button.update" action="/administrator/system-currency/update"/>
	<acme:submit test="${_command=='update'}" code="administrator.system-currency.form.button.submitUpdate" action="/administrator/system-currency/update"/>
</acme:form>
