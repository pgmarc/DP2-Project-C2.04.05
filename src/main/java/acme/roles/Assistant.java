
package acme.roles;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.entities.Tutorial;
import acme.framework.components.accounts.DefaultUserIdentity;
import acme.framework.components.accounts.UserAccount;
import acme.framework.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Assistant extends AbstractRole {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Length(min = 1, max = 75)
	protected String			supervisor;

	@NotBlank
	@Length(min = 1, max = 100)
	protected String			exepertiseFields;

	@NotBlank
	@Length(min = 1, max = 100)
	protected String			resume;

	@URL
	protected String			moreInfo;

	// Derived attributes -----------------------------------------------------


	@Override
	@Transient
	public DefaultUserIdentity getIdentity() {
		DefaultUserIdentity result;

		assert this.userAccount.getIdentity() instanceof DefaultUserIdentity;
		result = this.userAccount.getIdentity();

		return result;
	}

	// Relationships ----------------------------------------------------------


	@NotNull
	@OneToMany
	protected Tutorial		tutorial;

	@NotNull
	@Valid
	@OneToMany
	protected UserAccount	userAccount;

}
