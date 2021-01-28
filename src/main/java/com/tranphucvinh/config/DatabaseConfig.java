package com.tranphucvinh.config;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.tranphucvinh.common.FieldMap;
import com.tranphucvinh.interceptor.MyBatisQueryIntercept;
import com.tranphucvinh.interceptor.MyBatisUpdateIntercept;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;


/**
 * Created by vinhtp on 2019-4-4.
 */
@Configuration
@ConfigurationProperties
@EnableConfigurationProperties(DatabaseConfig.class)
@EnableTransactionManagement
@MapperScan(basePackages = "com.tranphucvinh.mybatis.mapper", sqlSessionFactoryRef = "sqlSessionFactory")
public class DatabaseConfig {

    private org.apache.ibatis.session.Configuration configuration;

    @Value("${server.timezone}")
    protected String severTimezone;
    @Value("${datasource.host}")
    protected String host;
    @Value("${datasource.port}")
    protected String port;
    @Value("${datasource.database}")
    protected String database;
    @Value("${datasource.username}")
    protected String username;
    @Value("${datasource.password}")
    protected String password;

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(new String[]{"com.tranphucvinh.jpa.entity"});
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());
        return em;
    }

    @Bean
    public DataSource dataSource() {
        final String URL = "jdbc:mysql://%s:%s/%s?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=%s&zeroDateTimeBehavior=convertToNull";
        String formattedUrl = String.format(URL, host, port, database, severTimezone);
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(formattedUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    private Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
        return properties;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(ApplicationContext applicationContext) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();

        // mybatis config
        configuration = new org.apache.ibatis.session.Configuration();
        configuration.setCacheEnabled(true);
        configuration.setCallSettersOnNulls(true);
        configuration.setUseGeneratedKeys(true);
        configuration.setDefaultExecutorType(ExecutorType.REUSE);
        //configuration.setMapUnderscoreToCamelCase(true);
        registTypeAlias("com.tranphucvinh.mybatis.model", FieldMap.class);

        sessionFactory.setConfiguration(configuration);

        //mybatis mapper config
        sessionFactory.setMapperLocations(applicationContext.getResources("classpath*:/mybatis/*Mapper.xml"));

        //data source
        sessionFactory.setDataSource(dataSource());

        MyBatisUpdateIntercept myBatisUpdateIntercept = new MyBatisUpdateIntercept();
        MyBatisQueryIntercept myBatisQueryIntercept = new MyBatisQueryIntercept();
        sessionFactory.setPlugins(new Interceptor[]{myBatisUpdateIntercept});
        sessionFactory.setPlugins(new Interceptor[]{myBatisQueryIntercept});
        return sessionFactory.getObject();
    }

    private void registTypeAlias(final String packageScan, Class<?>... classes) throws ClassNotFoundException {
        final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile(".*")));
        final Set<BeanDefinition> packageScanClasses = provider.findCandidateComponents(packageScan);

        // model bean
        for (BeanDefinition bean : packageScanClasses) {
            Class<?> clazz = Class.forName(bean.getBeanClassName());
            configuration.getTypeAliasRegistry().registerAlias(clazz);
        }
        for (Class<?> clazz : classes) {
            // register with lowerCase field
            configuration.getTypeAliasRegistry().registerAlias(clazz);
        }
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
}
