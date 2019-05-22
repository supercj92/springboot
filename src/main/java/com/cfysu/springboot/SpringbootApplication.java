package com.cfysu.springboot;

import com.cfysu.springboot.config.JavaConfig;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;

@SpringBootApplication
@MapperScan("com.cfysu.springboot.mapper")
public class SpringbootApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(new Object[]{SpringbootApplication.class, JavaConfig.class}, args);
		ConfigurableEnvironment environment = applicationContext.getEnvironment();
		String property = environment.getProperty("test.key.name");
		System.out.println(property);
	}

	/**
	 * it's for set http url auto change to https
	 */
	@Bean
	public EmbeddedServletContainerFactory servletContainer(){
		TomcatEmbeddedServletContainerFactory tomcat=new TomcatEmbeddedServletContainerFactory(){
			@Override
			protected void postProcessContext(Context context) {
				SecurityConstraint securityConstraint=new SecurityConstraint();
				securityConstraint.setUserConstraint("CONFIDENTIAL");//confidential
				SecurityCollection collection=new SecurityCollection();
				collection.addPattern("/*");
				securityConstraint.addCollection(collection);
				context.addConstraint(securityConstraint);
			}
		};
		tomcat.addAdditionalTomcatConnectors(httpConnector());
		return tomcat;
	}

	@Bean
	public Connector httpConnector(){
		Connector connector=new Connector("org.apache.coyote.http11.Http11NioProtocol");
		connector.setScheme("http");
		connector.setPort(8080);
		connector.setSecure(false);
		connector.setRedirectPort(443);
		return connector;
	}
}
