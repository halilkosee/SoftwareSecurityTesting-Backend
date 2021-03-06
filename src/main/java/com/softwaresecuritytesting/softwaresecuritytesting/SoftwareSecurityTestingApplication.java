package com.softwaresecuritytesting.softwaresecuritytesting;

import javax.annotation.Resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.softwaresecuritytesting.softwaresecuritytesting.service.FilesStorageService;
import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class SoftwareSecurityTestingApplication implements CommandLineRunner {
    @Resource
    FilesStorageService storageService;

    public static void main(String[] args) {
        SpringApplication.run(SoftwareSecurityTestingApplication.class, args);
    }

    @Override
    public void run(String... arg) throws Exception {
        storageService.deleteAll();
        storageService.init();
    }
}
