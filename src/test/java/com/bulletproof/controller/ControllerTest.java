package com.bulletproof.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.bulletproof.TestConfig;
import com.bulletproof.model.Customer;
import com.bulletproof.resource.JsonCustomerParser;
import com.bulletproof.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes = TestConfig.class)
@EnableAutoConfiguration
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService service;

    @MockBean
    private JsonCustomerParser parser;

    @Autowired
    ObjectMapper objectMapper;

    Customer[] customerArray = new Customer[2];
    Customer c1 = new Customer("john", "tester", "sydney");
	Customer c2 = new Customer("john", "developer", "bangalore");
	
    @Before
    public void setUp() throws Exception {
	MockitoAnnotations.initMocks(this);

	customerArray[0] = c1;
	customerArray[1] = c2;

    }

    @Test
    public void getAllUsersTest() throws Exception {

	mockMvc.perform(MockMvcRequestBuilders.get("/rest/showallcustomers")).andExpect(status().isOk());
    }

    @Test
    public void addCustomerTestBadJson() throws Exception {

	mockMvc.perform(MockMvcRequestBuilders.post("/rest/addacustomer").param("data", "badly formed json "))
		.andExpect(status().isBadRequest());
    }

    @Test
    public void addCustomerTest() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.post("/rest/addacustomer").contentType(MediaType.APPLICATION_JSON)
		.content(objectMapper.writeValueAsString(c1))).andExpect(status().isOk());
    }

    @Test
    public void addmanyCustomerTest() throws Exception {

	mockMvc.perform(MockMvcRequestBuilders.post("/rest/addallcustomer").contentType(MediaType.APPLICATION_JSON)
		.content(objectMapper.writeValueAsString(customerArray))).andExpect(status().isOk());
    }

    @Test
    public void searchACustomerbyIDTest() throws Exception {

	Mockito.when(service.findOne(Matchers.any())).thenReturn(c1);

	mockMvc.perform(MockMvcRequestBuilders.post("/rest/searchacustomerbyID").contentType(MediaType.ALL_VALUE)
		.content(objectMapper.writeValueAsString(4))).andDo(print()).andExpect(status().isOk())
		.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(jsonPath("firstname").value("john"));

    }

    @Test
    public void searchACustomerbyFirstName() throws Exception {

	Mockito.when(service.findCustomerByFirstname(Matchers.anyString())).thenReturn(customerArray);

	mockMvc.perform(MockMvcRequestBuilders.post("/rest/searchacustomerbyfirstname").contentType(MediaType.ALL_VALUE)
		.content(objectMapper.writeValueAsString("john"))).andDo(print()).andExpect(status().isOk())
		.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(jsonPath("$[0].lastname").value("tester"))
		.andExpect(jsonPath("$[1].lastname").value("developer"));

    }

}
