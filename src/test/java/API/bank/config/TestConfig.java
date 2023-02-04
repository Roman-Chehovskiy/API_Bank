package API.bank.config;

import API.bank.entity.History;
import API.bank.entity.UserBalance;
import API.bank.services.impl.HistoryServiсesImpl;
import API.bank.services.impl.UserBalanceServicesImpl;
import org.flywaydb.core.Flyway;
import org.hibernate.SessionFactory;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@SpringBootTest
@ComponentScan(basePackageClasses = HistoryServiсesImpl.class)
public class TestConfig {

    public SessionFactory sessionFactory;


    @Bean
    public SessionFactory createSessionFactory() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().
                configure().build();
        try {
            sessionFactory = new MetadataSources(registry).addAnnotatedClass(UserBalance.class).addAnnotatedClass(History.class)
                    .buildMetadata().buildSessionFactory();

        } catch (Exception e) {
            System.out.println("jvnfjn");
        }
         Flyway flyway = Flyway.configure().dataSource("jdbc:h2:mem:myDb-1",
                 "as","09").schemas("PUBLIC").load();

        flyway.migrate();
        return sessionFactory;
    }
}
