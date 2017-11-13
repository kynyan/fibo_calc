package config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


@Configuration
@PropertySource("classpath:application.properties")
//@RequiredArgsConstructor
public class PersistenceConfig {
    private final Environment env;
    private final ApplicationContext appContext;

    public PersistenceConfig(Environment env, ApplicationContext appContext) {
        this.env = env;
        this.appContext = appContext;
    }

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);
        vendorAdapter.setShowSql(false);

        Map<String, Object> props = new HashMap<>();
        props.put("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
        props.put("hibernate.hbm2ddl.auto", "create-drop");

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("dao");
        factory.setDataSource(dataSource());
        factory.setJpaPropertyMap(props);
        return factory;
    }

    @Bean
    PlatformTransactionManager transactionManager() {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(appContext.getBean(EntityManagerFactory.class));
        return txManager;
    }

    @Bean
    DataSource dataSource() {
        ComboPooledDataSource actualDataSource = new com.mchange.v2.c3p0.ComboPooledDataSource();
        actualDataSource.setJdbcUrl(env.getProperty("db.url"));
        actualDataSource.setUser(env.getProperty("db.username"));
        actualDataSource.setPassword(env.getProperty("db.password"));
        actualDataSource.setMinPoolSize(env.getProperty("db.pool.min_size", int.class));
        actualDataSource.setMaxPoolSize(env.getProperty("db.pool.max_size", int.class));
        actualDataSource.setIdleConnectionTestPeriod(env.getProperty("db.pool.idle_connections_test_period_sec", int.class));
        actualDataSource.setUnreturnedConnectionTimeout(env.getProperty("db.pool.unreturned_connection_timeout_sec", int.class));
        actualDataSource.setCheckoutTimeout(env.getProperty("db.pool.checkout_timeout_millisec", int.class));
        actualDataSource.setAcquireRetryAttempts(env.getProperty("db.pool.acquire_attempts", int.class));
        actualDataSource.setTestConnectionOnCheckin(true);
        return actualDataSource;
    }
}
