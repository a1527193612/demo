package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

@Configuration
public class SessionConfig {
	
	
	//解决共享子域
	@Bean
	public CookieSerializer getCookieSerializer() {
		DefaultCookieSerializer cookie = new DefaultCookieSerializer();
		//指定父域，别忘了点
		cookie.setDomainName("gulimall.com");
		cookie.setCookieName("ADIHU");
		
		return cookie;
	}

}
