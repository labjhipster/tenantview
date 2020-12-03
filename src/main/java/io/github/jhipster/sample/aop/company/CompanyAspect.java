package io.github.jhipster.sample.aop.company;

import io.github.jhipster.sample.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.sample.security.SecurityUtils;
import io.github.jhipster.sample.repository.UserRepository;
import io.github.jhipster.sample.domain.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Filter;
import java.util.Optional;

@Aspect
@Component
public class CompanyAspect {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    private final String fieldName =  "companyId";

    private final Logger log = LoggerFactory.getLogger(CompanyAspect.class);

    /**
     * Run method if User service is hit.
     * Filter users based on which Company the user is associated with.
     * Skip filter if user has no Company
     */
    @Before("execution(* io.github.jhipster.sample.service.UserService.*(..)) "
            + "|| execution(* io.github.jhipster.sample.service.BankAccountService.*(..))"
            // jhipster-needle-add-entity-to-tenant-aspect - Multitenancy will roles here
        )
    public void beforeExecution() throws Throwable {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();

        if(login.isPresent()) {
            Optional<User> optionalUser = userRepository.findOneByLogin(login.get());
            if(!optionalUser.isPresent()) {
                // Self registration.
                throw new BadRequestAlertException("Self registration is disabled", "userRegistration", "selfregistrationdisabled");
            }
            User user = optionalUser.get();

            if (user.getCompany() != null) {
                Filter filter = entityManager.unwrap(Session.class).enableFilter("COMPANY_FILTER");
                filter.setParameter(fieldName, user.getCompany().getId());
            }
        }
    }
}
