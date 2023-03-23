
package acme.features.authenticated.auditor;

import org.springframework.stereotype.Service;

import acme.entities.audit.Audit;
import acme.framework.components.accounts.Authenticated;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedAuditorListByCourseService extends AbstractService<Authenticated, Audit> {

}
