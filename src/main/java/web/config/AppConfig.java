package web.config;



import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;


@Configuration
@PropertySource("classpath:db.properties")
@EnableTransactionManagement
@ComponentScan("web")
@EnableJpaRepositories("web.repositories")

public class AppConfig {

   @Resource
   private Environment env;

   @Bean
   public LocalContainerEntityManagerFactoryBean getEntityManager() {
      final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
      em.setDataSource(getDataSource());
      em.setPackagesToScan("web.models");

      final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
      em.setJpaVendorAdapter(vendorAdapter);
      em.setJpaProperties(getHibernateProp());

      return em;
   }

   @Bean
   public DataSource getDataSource() {
      BasicDataSource dataSource = new BasicDataSource();
      dataSource.setDriverClassName(env.getProperty("db.driver"));
      dataSource.setUrl(env.getProperty("db.url"));
      dataSource.setUsername(env.getProperty("db.username"));
      dataSource.setPassword(env.getProperty("db.password"));

      dataSource.setInitialSize(Integer.valueOf(env.getRequiredProperty("db.initialSize")));
      dataSource.setMinIdle(Integer.valueOf(env.getRequiredProperty("db.minIdle")));
      dataSource.setMaxIdle(Integer.valueOf(env.getRequiredProperty("db.maxIdle")));
      dataSource.setTimeBetweenEvictionRunsMillis(Long.valueOf(env.getRequiredProperty("db.timeBetweenEvictionMillis")));
      dataSource.setMinEvictableIdleTimeMillis(Long.valueOf(env.getProperty("db.minEvictableIdleTimeMillis")));
      dataSource.setTestOnBorrow(Boolean.valueOf(env.getRequiredProperty("dv.testOnBorrow")));
      dataSource.setValidationQuery(env.getRequiredProperty("db.validation"));

      return dataSource;
   }
   @Bean
   public PlatformTransactionManager platformTransactionManager() {
      JpaTransactionManager manager = new JpaTransactionManager();
      manager.setEntityManagerFactory(getEntityManager().getObject());
      return manager;
   }

   public Properties getHibernateProp() {
         Properties props = new Properties();
         props.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
         props.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
         return props;
   }

}
