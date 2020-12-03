package io.github.jhipster.sample.aop.company;

import io.github.jhipster.sample.repository.BankAccountRepository;
import io.github.jhipster.sample.security.SecurityUtils;
import io.github.jhipster.sample.repository.UserRepository;
import io.github.jhipster.sample.domain.User;
import io.github.jhipster.sample.domain.BankAccount;
import org.aspectj.lang.JoinPoint;
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
import java.util.NoSuchElementException;
import java.util.List;
import org.springframework.data.domain.Example;
import org.aspectj.lang.annotation.AfterReturning;

@Aspect
@Component
public class BankAccountAspect {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    /**
     * Run method if BankAccount repository save is hit.
     * Adds tenant information to entity.
     */
    @Before(value = "execution(* io.github.jhipster.sample.repository.BankAccountRepository.save(..)) && args(bankAccount, ..)")
    public void onSave(JoinPoint joinPoint, BankAccount bankAccount) {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();

        if(login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();

            if (loggedInUser.getCompany() != null) {
                bankAccount.setCompany(loggedInUser.getCompany());
            }
        }
    }

    /**
     * Run method if BankAccount repository deleteById is hit.
     * Verify if tenant owns the bankAccount before delete.
     */
    @Before(value = "execution(* io.github.jhipster.sample.repository.BankAccountRepository.deleteById(..)) && args(id, ..)")
    public void onDelete(JoinPoint joinPoint, Long id) {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();

        if(login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();

            if (loggedInUser.getCompany() != null) {
                BankAccount bankAccount = bankAccountRepository.findById(id).get();
                if(bankAccount.getCompany() != loggedInUser.getCompany()){
                    throw new NoSuchElementException();
                }
            }
        }
    }

    /**
     * Run method if BankAccount repository findById is returning.
     * Adds filtering to prevent display of information from another tenant.
     */
    @Around("execution(* io.github.jhipster.sample.repository.BankAccountRepository.findById(..)) && args(id, ..)")
    public Object onFindById(ProceedingJoinPoint pjp, Long id) throws Throwable {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();

        Optional<BankAccount> optional = (Optional<BankAccount>) pjp.proceed();
        if(login.isPresent())
        {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();

            if (loggedInUser.getCompany() != null) {
                if(optional.isPresent() && optional.get().getCompany() != loggedInUser.getCompany()){
                    throw new NoSuchElementException();
                }
            }
        }
        return optional;
    }

    /**
     * Run method around BankAccount service findAll.
     * Adds filtering to prevent display of information from another tenant before database query (less performance hit).
     */
    @Around("execution(* io.github.jhipster.sample.service.BankAccountService.findAll())")
    public Object onFindAll(ProceedingJoinPoint pjp) throws Throwable {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();

        if(login.isPresent())
        {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();

            if (loggedInUser.getCompany() != null) {
                BankAccount example = new BankAccount();
                example.setCompany(loggedInUser.getCompany());
                List<BankAccount> bankAccounts = bankAccountRepository.findAll(Example.of(example));
                return bankAccounts;
            }
        }
        return pjp.proceed();
    }
}
