
package com.ezhuspace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import com.ezhuspace.configuration.AppUrlService;
import com.ezhuspace.controller.AutodeskController;


/**
 * @author Wang Zhen <A.Hleb.King wangzhenjjcn@gmail.com>
 * @since  2018年1月19日  上午11:45:44
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableTransactionManagement
@EnableJpaRepositories
@EnableJpaAuditing
@SpringBootApplication
//@EnableCaching
public class EzhuApp {
	    private static final Logger LOG = LoggerFactory.getLogger(EzhuApp.class);
	  @Bean
	    public BCryptPasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

	    @Bean
	    public AppUrlService appUrlService() {
	        return new AppUrlService();
	    }
	    @Bean
	    public RestTemplate restTemplate() {
	        return new RestTemplate(clientHttpRequestFactory());
	    }

	    private ClientHttpRequestFactory clientHttpRequestFactory() {
	        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
	        factory.setReadTimeout(10000);
	        factory.setConnectTimeout(10000);
	        return factory;
	    }

	    public static void main(String[] args) {
	        SpringApplication.run(EzhuApp.class, args);
	        AutodeskController autodeskController=new AutodeskController();
	    }
	
}
