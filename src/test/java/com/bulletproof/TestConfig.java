package com.bulletproof;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.bulletproof.controller.CustomerServiceController;
import com.bulletproof.repository.CustomerRepository;
import com.bulletproof.resource.JsonCustomerParser;

@Configuration
@Import({ CustomerServiceController.class,JsonCustomerParser.class })
@ComponentScan({ "com.bulletproof.service", "com.bulletproof.repository", "com.bulletproof" })
public class TestConfig {

}
