package io.github.jhipster.sample.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import io.github.jhipster.config.cache.PrefixedKeyGenerator;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {
    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, io.github.jhipster.sample.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, io.github.jhipster.sample.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, io.github.jhipster.sample.domain.User.class.getName());
            createCache(cm, io.github.jhipster.sample.domain.Authority.class.getName());
            createCache(cm, io.github.jhipster.sample.domain.User.class.getName() + ".authorities");
            createCache(cm, io.github.jhipster.sample.domain.BankAccount.class.getName());
            createCache(cm, io.github.jhipster.sample.domain.BankAccount.class.getName() + ".operations");
            createCache(cm, io.github.jhipster.sample.domain.TheLabel.class.getName());
            createCache(cm, io.github.jhipster.sample.domain.TheLabel.class.getName() + ".operations");
            createCache(cm, io.github.jhipster.sample.domain.Operation.class.getName());
            createCache(cm, io.github.jhipster.sample.domain.Operation.class.getName() + ".theLabels");
            createCache(cm, io.github.jhipster.sample.domain.Department.class.getName());
            createCache(cm, io.github.jhipster.sample.domain.Department.class.getName() + ".employees");
            createCache(cm, io.github.jhipster.sample.domain.Department.class.getName() + ".histories");
            createCache(cm, io.github.jhipster.sample.domain.JobHistory.class.getName());
            createCache(cm, io.github.jhipster.sample.domain.JobHistory.class.getName() + ".departments");
            createCache(cm, io.github.jhipster.sample.domain.JobHistory.class.getName() + ".jobs");
            createCache(cm, io.github.jhipster.sample.domain.JobHistory.class.getName() + ".emps");
            createCache(cm, io.github.jhipster.sample.domain.Job.class.getName());
            createCache(cm, io.github.jhipster.sample.domain.Job.class.getName() + ".chores");
            createCache(cm, io.github.jhipster.sample.domain.Job.class.getName() + ".histories");
            createCache(cm, io.github.jhipster.sample.domain.Employee.class.getName());
            createCache(cm, io.github.jhipster.sample.domain.Employee.class.getName() + ".jobs");
            createCache(cm, io.github.jhipster.sample.domain.Employee.class.getName() + ".histories");
            createCache(cm, io.github.jhipster.sample.domain.Location.class.getName());
            createCache(cm, io.github.jhipster.sample.domain.Location.class.getName() + ".countries");
            createCache(cm, io.github.jhipster.sample.domain.Task.class.getName());
            createCache(cm, io.github.jhipster.sample.domain.Task.class.getName() + ".linkedJobs");
            createCache(cm, io.github.jhipster.sample.domain.Company.class.getName());
            createCache(cm, io.github.jhipster.sample.domain.Company.class.getName() + ".users");
            createCache(cm, io.github.jhipster.sample.domain.GoldenBadge.class.getName());
            createCache(cm, io.github.jhipster.sample.domain.SilverBadge.class.getName());
            createCache(cm, io.github.jhipster.sample.domain.Identifier.class.getName());
            createCache(cm, io.github.jhipster.sample.domain.Country.class.getName());
            createCache(cm, io.github.jhipster.sample.domain.Country.class.getName() + ".areas");
            createCache(cm, io.github.jhipster.sample.domain.Region.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
