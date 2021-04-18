package com.gwh.multipledatasource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.gwh.multipledatasource.mapper")
public class MultipledatasourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultipledatasourceApplication.class, args);
    }

}
