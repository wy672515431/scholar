package org.buaa.scholar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy = true)
public class ScholarApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScholarApplication.class, args);
    }

}
