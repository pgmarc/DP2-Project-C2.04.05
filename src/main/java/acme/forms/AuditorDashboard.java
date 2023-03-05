
package acme.forms;

import javax.validation.constraints.NotNull;

import acme.framework.data.AbstractForm;
import acme.helpers.Statistic;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditorDashboard extends AbstractForm {

	protected static final long	serialVersionUID	= 1L;

	protected int				theoryAudits;

	protected int				handOnAudits;

	// "Make ? transient or serializable, ommit error for now"
	@NotNull
	protected Statistic<?, ?>	auditTheoryStats;

	@NotNull
	protected Statistic<?, ?>	auditRecordTheoryStats;

	@NotNull
	protected Statistic<?, ?>	auditHandOnStats;

	protected Statistic<?, ?>	auditRecordHandOnStats;

}
