//package spring.demo.config;
//
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.support.PropertiesLoaderUtils;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//import spring.demo.user.CrmUser;
//
//import javax.persistence.EntityManagerFactory;
//import javax.sql.DataSource;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Properties;
//import java.util.stream.Collectors;
//
//@Configuration
//@EnableWebSecurity
//@EnableTransactionManagement
//@EnableJpaRepositories(
//        entityManagerFactoryRef = "userEntityManager",
//        transactionManagerRef = "userTransactionManager",
//        basePackages = "spring.demo.dao"
//)
//public class UserDBConfig {
//
//    @Bean
//    @ConfigurationProperties(prefix = "security.datasource")
//    public DataSource sqlDataSource() {
//        return DataSourceBuilder
//                .create()
//                .build();
//    }
//
//    @Bean(name = "userEntityManager")
//    public LocalContainerEntityManagerFactoryBean sqlEntityManagerFactory(EntityManagerFactoryBuilder builder) {
//        return builder
//                .dataSource(sqlDataSource())
//                .properties(hibernateProperties())
//                .packages(CrmUser.class)
//                .persistenceUnit("userPU")
//                .build();
//    }
//
//    @Bean(name = "userTransactionManager")
//    public PlatformTransactionManager sqlTransactionManager(@Qualifier("userEntityManager") EntityManagerFactory entityManagerFactory) {
//        return new JpaTransactionManager(entityManagerFactory);
//    }
//
//    private Map hibernateProperties() {
//
//        Resource resource = new ClassPathResource("application.properties");
//
//        try {
//            Properties properties = PropertiesLoaderUtils.loadProperties(resource);
//
//            return properties.entrySet().stream()
//                    .collect(Collectors.toMap(
//                            e -> e.getKey().toString(),
//                            e -> e.getValue())
//                    );
//        } catch (IOException e) {
//            return new HashMap();
//        }
//    }
//}