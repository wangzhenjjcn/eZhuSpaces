package com.ezhuspace.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableCaching
public class AppConfiguration {
	@Value("${server.port}")
	private String serverPort;

	 
	
	
	
	
	public AppConfiguration() {

	}

	public String getServerPort() {
		return serverPort;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}
 
	 

	 
}
