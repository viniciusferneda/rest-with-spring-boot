package com.vferneda.restwithspringboot;

import com.vferneda.restwithspringboot.config.FileStorageConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
@EnableConfigurationProperties({
        FileStorageConfig.class
})
public class RestWithSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestWithSpringBootApplication.class, args);

        /*
        final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(16);
        String result = bCryptPasswordEncoder.encode("admin123");
        System.out.println(result);
        */
    }

}
