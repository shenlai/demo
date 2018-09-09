package configtest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

    @Bean
    public Configdemo configdemo(){
        return new Configdemo();
    }

}
