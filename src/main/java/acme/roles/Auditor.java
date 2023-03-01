
package acme.roles;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity 
public class Auditor extends AbstractRole {

	protected static final long serialVersionUID = 1L;
	
	@NotNull
	@NotBlank
	@Length( max = 25 )
	protected String professionalId;
	
	@NotNull
	@NotBlank
	@Length( max = 75 )
	protected String firm;
	
	@NotNull
	@NotBlank
	@Length( max = 100 )
	protected String certifications;
	
	@NotBlank
	@URL
	protected String moreInfo;
	
}
