package de.dlopes.stocks.facilitator.config;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import de.dlopes.stocks.facilitator.StockFacilitatorApplication;


@Configuration
public class MvcConfig extends SpringBootServletInitializer {
    
    /** 
      * configure the application for packaging as deployable war
      * 
      * @author Dominique Lopes
      */ 
 	@Override 
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) { 
         return builder.sources(StockFacilitatorApplication.class); 
    } 

 	/** 
 	 * enable access to web resources of the Spring JS package
 	 * 
 	 * @author Dominique Lopes
 	 *
 	 */
 	@Configuration
 	public class MyWebConfig extends WebMvcConfigurerAdapter {
 		
 		@Override
 		public void addResourceHandlers(ResourceHandlerRegistry registry) {
 			registry.addResourceHandler("/resources/**").addResourceLocations("/", "classpath:/META-INF/web-resources/");
 		}
 		
 	}
 	
}