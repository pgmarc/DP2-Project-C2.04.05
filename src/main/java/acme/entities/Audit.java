package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Audit extends AbstractEntity {
	
	protected static final long serialVersionUID = 1L;

	@NotNull
	@NotBlank
	@Pattern( regexp = "[A-Z]{1,3}[0-9][0-9]{3}" )
	protected String code;
	
	@NotNull
	@NotBlank
	@Length( max = 100 )
	protected String conclusion;
	
	@NotNull
	@NotBlank
	@Length( max = 100 )
	@Pattern( regexp = "[\\W\\w]+(?:(?:\\r\\n|\\n)[\\W\\w]+)" )
	protected String strongPoints;
	
	@NotNull
	@NotBlank
	@Length( max = 100 )
	@Pattern( regexp = "[\\W\\w]+(?:(?:\\r\\n|\\n)[\\W\\w]+)" )
	protected String weakPoints;
	
	@NotNull
	@Transient
	protected String mark;
	
	
}
