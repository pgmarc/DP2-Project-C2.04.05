
package acme.datatypes;

import javax.persistence.Embeddable;

import acme.framework.components.accounts.DefaultUserIdentity;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class UserIdentity extends DefaultUserIdentity {

	// Serialisation identifier -----------------------------------------------

	protected static final long serialVersionUID = 1L;

}
