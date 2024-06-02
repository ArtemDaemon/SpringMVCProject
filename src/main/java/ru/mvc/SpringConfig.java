package ru.mvc;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

/**
 * Spring configuration class for setting up the application's context, JPA repositories, and web MVC configuration.
 */
@Configuration
@EnableJpaRepositories(basePackages = "ru.mvc")
@EnableWebMvc
public class SpringConfig implements WebMvcConfigurer {
    /**
     * Configures the entity manager factory bean for JPA, including the data source and Hibernate properties.
     *
     * @param dataSource the data source to be used by the entity manager factory
     * @return the configured LocalContainerEntityManagerFactoryBean
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("ru.mvc");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.getJpaPropertyMap().put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        return em;
    }

    /**
     * Configures the view resolver for Thymeleaf templates.
     *
     * @param templateEngine the SpringTemplateEngine to be used by the view resolver
     * @return the configured ThymeleafViewResolver
     */
    @Bean
    public ViewResolver viewResolver(SpringTemplateEngine templateEngine) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine);
        return resolver;
    }

    /**
     * Configures the Thymeleaf template engine.
     *
     * @return the configured SpringTemplateEngine
     */
    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        return templateEngine;
    }

    /**
     * Configures the template resolver for Thymeleaf, setting the prefix, suffix, and template mode.
     *
     * @return the configured ClassLoaderTemplateResolver
     */
    private ClassLoaderTemplateResolver templateResolver() {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("/templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        return resolver;
    }
}