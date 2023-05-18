<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<h2>
	<acme:message code="auditor.dashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm" >
 <caption>Audit stats by an Auditor</caption>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.theory"/>
		</th>
		<td>
			<acme:print value="${theoryAudits}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.handon"/>
		</th>
		<td>
			<acme:print value="${handOnAudits}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.balanced"/>
		</th>
		<td>
			<acme:print value="${balancedAudits}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.audit-record-max"/>
		</th>
		<td>
			<acme:print value="${auditStatsMax}"/>
		</td>
	</tr>	
 	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.audit-record-min"/>
		</th>
		<td>
			<acme:print value="${auditStatsMin}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.audit-record-average"/>
		</th>
		<td>
			<acme:print value="${auditStatsAvg}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.audit-record-deviation"/>
		</th>
		<td>
			<acme:print value="${auditStatsDesv}"/>
		</td>
	</tr>	
		<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.audit-record-time-max"/>
		</th>
		<td>
			<acme:print value="${auditDurationStatsMax}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.audit-record-time-min"/>
		</th>
		<td>
			<acme:print value="${auditDurationStatsMin}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.audit-record-time-average"/>
		</th>
		<td>
			<acme:print value="${auditDurationStatsAvg}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.audit-record-time-deviation"/>
		</th>
		<td>
			<acme:print value="${auditDurationStatsDesv}"/>
		</td>
	</tr>
</table>