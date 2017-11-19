package com.bulletproof;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.bulletproof.controller.CustomerServiceController;

@Configuration
@Import({ CustomerServiceController.class })
public class TestConfig {

}
