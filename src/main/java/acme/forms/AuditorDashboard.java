
package forms;

import javax.validation.constraints.NotNull;

import acme.framework.data.AbstractForm;
import helpers.Statistic;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditorDashboard extends AbstractForm {
	
	protected static final long serialVersionUID = 1L;
	
	protected int theoryAudits;
	
	protected int handOnAudits;
	
	@NotNull
	protected Statistic auditTheoryStats;
	
	@NotNull
	protected Statistic auditRecordTheoryStats;
	
	@NotNull
	protected Statistic auditHandOnStats;
	
	protected Statistic auditRecordHandOnStats;
	
}
