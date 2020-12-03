package io.github.jhipster.sample.aop.company;

import io.github.jhipster.sample.security.SecurityUtils;
import io.github.jhipster.sample.repository.UserRepository;
import io.github.jhipster.sample.domain.User;
import io.github.jhipster.sample.service.dto.UserDTO;
import io.github.jhipster.sample.domain.CompanyParameter;
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

@Aspect
@Component
public class UserAspect {

@Autowired
private UserRepository userRepository;

@Autowired
private CompanyParameter companyParameter;

private final String fieldName =  "companyId";

private final Logger log = LoggerFactory.getLogger(UserAspect.class);

    /**
     * Run method if User service createUser is hit.
     * Stores tenant information from DTO.
     */
    @Before(value = "execution(* io.github.jhipster.sample.service.UserService.createUser(..)) && args(userDTO, ..)")
    public void onCreateUser(JoinPoint joinPoint, UserDTO userDTO) throws Throwable {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();

        if(login.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();

            if (loggedInUser.getCompany() != null) {
                companyParameter.setCompany(loggedInUser.getCompany());
            }
            else{
                companyParameter.setCompany(userDTO.getCompany());
            }
        }
    }

    /**
     * Run method if User service updateUser is hit.
     * Adds tenant information to DTO.
     */
    @Before(value = "execution(*  io.github.jhipster.sample.service.UserService.updateUser(..)) && args(userDTO, ..)")
    public void onUpdateUser(JoinPoint joinPoint, UserDTO userDTO)
    {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();

        if (login.isPresent())
        {
            User loggedInUser = userRepository.findOneByLogin(login.get()).get();
            User user = userRepository.findById(userDTO.getId()).get();

            if (loggedInUser.getCompany() != null)
            {
                user.setCompany(loggedInUser.getCompany());
            }
            else
            {
                user.setCompany(userDTO.getCompany());
            }

            log.debug("Changed Company for User: {}", user);
        }
    }

    /**
     * Run method if User repository save is hit.
     * Adds tenant information to DTO.
     */
    @Before(value = "execution(* io.github.jhipster.sample.repository.UserRepository.save(..)) && args(user, ..)")
    public void onSave(JoinPoint joinPoint, User user) {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();

        if(companyParameter.getCompany() != null) {
            user.setCompany(companyParameter.getCompany());
        }
    }

    /**
     * Run method if User service getUserWithAuthoritiesByLogin is hit.
     * Adds filtering to prevent display of information from another tenant
     */
    @Before(value = "execution(* io.github.jhipster.sample.service.UserService.getUserWithAuthoritiesByLogin(..)) && args(login, ..)")
    public void onGetUserWithAuthoritiesByLogin(JoinPoint joinPoint, String login) throws Exception {
        Optional<String> currentLogin = SecurityUtils.getCurrentUserLogin();

        if(currentLogin.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(currentLogin.get()).get();

            if (loggedInUser.getCompany() != null) {
                User user = userRepository.findOneWithAuthoritiesByLogin(login).get();

                if(!user.getCompany().equals(loggedInUser.getCompany())){
                    throw new NoSuchElementException();
                }
            }
        }
    }

    /**
     * Run method if User service getUserWithAuthorities is hit.
     * Adds filtering to prevent display of information from another tenant
     */
    /*
    @Before(value = "execution(* io.github.jhipster.sample.service.UserService.getUserWithAuthorities(..)) && args(id, ..)")
    public void onGetUserWithAuthorities(JoinPoint joinPoint, Long id) throws Exception {
        Optional<String> currentLogin = SecurityUtils.getCurrentUserLogin();

        if(currentLogin.isPresent()) {
            User loggedInUser = userRepository.findOneByLogin(currentLogin.get()).get();

            if (loggedInUser.getCompany() != null) {
                User user = userRepository.findOneWithAuthoritiesById(id).get();

                if(!user.getCompany().equals(loggedInUser.getCompany())){
                    throw new NoSuchElementException();
                }
            }
        }
    }
    */
}
