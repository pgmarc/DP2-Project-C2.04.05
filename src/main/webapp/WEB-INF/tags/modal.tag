<%@tag language="java" body-content="empty"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<%@attribute name="buttonName" required="true" type="java.lang.String"%>
<%@attribute name="modalTitle" required="true" type="java.lang.String"%>
<%@attribute name="modalBody" required="true" type="java.lang.String"%>
<%@attribute name="modalButtonSubmitName" required="true" type="java.lang.String"%>
<%@attribute name="modalButtonCancelName" required="true" type="java.lang.String"%>
<%@attribute name="action" required="true" type="java.lang.String"%>

<button type="button" class="btn btn-dark" data-toggle="modal" data-target="#exampleModal"><acme:message code="${buttonName}"/></button>
	
	<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel"><acme:message code="${modalTitle}"/></h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	        <acme:message code="${modalBody}"/>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-dismiss="modal"><acme:message code="${modalButtonCancelName}"/></button>
	        <acme:submit code="${modalButtonSubmitName}" action="${action}"/>
	      </div>
	    </div>
	  </div>
	</div>