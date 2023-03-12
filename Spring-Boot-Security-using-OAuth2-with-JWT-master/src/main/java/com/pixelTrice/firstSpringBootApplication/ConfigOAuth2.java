package com.pixelTrice.firstSpringBootApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration 
public class ConfigOAuth2 extends AuthorizationServerConfigurerAdapter{
	
	   private String clientId = "rahul";
	   private String clientSecret = "rahul123";
	   
	   private String privateKey = "-----BEGIN RSA PRIVATE KEY-----\n"
	   		+ "MIIEowIBAAKCAQEAsDNoOmSFvFs0/dMSfPvBo+TqgadiST4RsNXk4OMmhjVCYGS+\n"
	   		+ "limNyEYAwkPSx8Upn1DC5cKUBFjHv3ZV4HK3KHbPD1CQ0bj0jEi+rhWNGg+HIwmV\n"
	   		+ "AyfvBbMK1xIixca7I+84noztJQcVDyXYHRCEskIkdQKW5RAryiOG07lWq4/0jGbi\n"
	   		+ "X8PRpkFmYpj9vPJrVHPEFtS1dZ8/6FDDWHXpkE8Z9NqVBP2F/Pdj/3QzImuEYeVP\n"
	   		+ "QGGuLEc06/ujzGejVsOMh+8x/IRAQY2KsKlfIn2vJUiPmrNS4ZlD+yJGcKqTPyDh\n"
	   		+ "ovLIylq6430HCnA8Vui+QpqBuHlq1bj7CcJEbQIDAQABAoIBADd5J9JifXxMlsVm\n"
	   		+ "44Y32MIZuv7wzM/UW8t5u+JVgXZ1ZB1m+l3aXUn1SB3hLoY4e58t1P5zIRixPsn3\n"
	   		+ "npfYZfO2XAAtVDrC6TMVX/r8y/ytcNVGGgTxWJ5JvRrzGcF8jMSGdyPrqGmk7itj\n"
	   		+ "tcOIxM4+Zq30D7+0NKUiRDt6OWAvn4STjebPVq3cknvLFnW1MLCD00fDilqJxkWy\n"
	   		+ "LNkZ3WRThceaJsICXK3vtG9BMJ9PfCWYOi5Y6kjik+SmIA8pakmN0lblpjs/6i4C\n"
	   		+ "dINFYLRqniPgoJcjKrtsbXWC6Bt0xBA2uS/3Cnbc+pHkF6iDQ84GEes220ysVvgK\n"
	   		+ "onakmv0CgYEA2C0UTEP4Az703EjDFIGjWjIZkgCtyQjQqITjsLzLCcBhKUWQF6EN\n"
	   		+ "9Z1/1qaLaQKZqhfMtN/FBM9VHK5LslJxcDBWNm4VKmBJ5unREBdhcOp6M4JtHLbB\n"
	   		+ "zvWuyyUgaFIan0rb9jPIYDbSq8WFO5W/95RMhhX7vG3z1fiIk4Gs1HMCgYEA0KkV\n"
	   		+ "Sc+/raIhnkh3swB6TS1Drc4OLpFCEYt/AcSXIMyhUt/nVqpIo8+8aHkpHE3Zej5U\n"
	   		+ "T05Y4pAwh/DveOk5fdFS8oLRUXW2vUWpz9P03SlLZrYXaFKCCYp6tVwNI2KH9/ZE\n"
	   		+ "+gqgXAVzLBPMUzvgXieFOroHcduXsC4gesWZK58CgYEAuF8iO7WQlnwgbKxmLVwv\n"
	   		+ "zSKDqoGHtrkc9U15wGgUpzl75K5eqbiD5mKxAIrExgggtPBdeDqNl3+ZcL+68djc\n"
	   		+ "xTmEniGfTagX/6iV1SqLwblKBhqMIvxAKdpifQIqNK8p44f+OGqL0HuRjE0HR/19\n"
	   		+ "X5QM/CNNs7T3TRwOp/fmRr8CgYB7mOxHQIvlqfgH7h0ZJttLA9jnBvtXkit0gRDG\n"
	   		+ "V1C92PhmQsKhj1Rf1BliOQKlBWghBPKj12DHM2nL3Ge9U0j+whNEG0Ep43RPQQiJ\n"
	   		+ "Vp/gsSk8ufe7j8P/KsvsyY3uu9qWgoXxJoI+f+vs2/1YAEuy+e3tD2yBgPWlIm2S\n"
	   		+ "y6EjOQKBgH9fF0Dyf25JfE7Y7XtCj7/O1naWSg6hkTfiB28q+otmTvj9GiKe1uxU\n"
	   		+ "crO8xbDclN77gEHGRWP/mWYwoRdOUeGhpv8h5tL6hPVM6//Nhv20ynmbRM0Um9T7\n"
	   		+ "MqRgNpuQZyd2VF658d53JQWOqKrWvgak/Jjeu3AkQSNEtoBk61FO\n"
	   		+ "-----END RSA PRIVATE KEY-----"; //here u need to generate and replace the private key
	  
	   
	   private String publicKey = "-----BEGIN PUBLIC KEY-----\n"
	   		+ "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsDNoOmSFvFs0/dMSfPvB\n"
	   		+ "o+TqgadiST4RsNXk4OMmhjVCYGS+limNyEYAwkPSx8Upn1DC5cKUBFjHv3ZV4HK3\n"
	   		+ "KHbPD1CQ0bj0jEi+rhWNGg+HIwmVAyfvBbMK1xIixca7I+84noztJQcVDyXYHRCE\n"
	   		+ "skIkdQKW5RAryiOG07lWq4/0jGbiX8PRpkFmYpj9vPJrVHPEFtS1dZ8/6FDDWHXp\n"
	   		+ "kE8Z9NqVBP2F/Pdj/3QzImuEYeVPQGGuLEc06/ujzGejVsOMh+8x/IRAQY2KsKlf\n"
	   		+ "In2vJUiPmrNS4ZlD+yJGcKqTPyDhovLIylq6430HCnA8Vui+QpqBuHlq1bj7CcJE\n"
	   		+ "bQIDAQAB\n"
	   		+ "-----END PUBLIC KEY-----";   // //here u need to generate and replace the private key
	   
	   @Autowired
	   @Qualifier("authenticationManagerBean")
	   private AuthenticationManager authenticationManager;
	   
	   @Autowired
	   PasswordEncoder passwordEncoder;
	   
	   @Bean
	   public JwtAccessTokenConverter tokenEnhancer() {
	      JwtAccessTokenConverter converter = new JwtAccessTokenConverter(); 
	      converter.setSigningKey(privateKey);
	      converter.setVerifierKey(publicKey);
	      return converter;
	   }
	   
	   @Bean
	   public JwtTokenStore tokenStore() {
	      return new JwtTokenStore(tokenEnhancer());
	   }
	   
	   @Override
	   public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
	      endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore())
	      .accessTokenConverter(tokenEnhancer());
	   }
	   @Override
	   public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
	      security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	   }
	   @Override
	   public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
	      clients.inMemory().withClient(clientId).secret(passwordEncoder.encode(clientSecret)).scopes("read", "write")
	         .authorizedGrantTypes("password", "refresh_token").accessTokenValiditySeconds(20000)
	         .refreshTokenValiditySeconds(20000);

	   }

}
