package yile.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;


@Configuration
public class MyComponentConfig {
	
	@Bean
	public ObjectMapper objectMapperBean() {
		return new ObjectMapper();
	}

}
