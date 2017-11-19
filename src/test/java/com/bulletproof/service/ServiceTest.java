package com.bulletproof.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

import com.bulletproof.TestConfig;
import com.bulletproof.controller.CustomerServiceController;
import com.bulletproof.model.Customer;
import com.bulletproof.repository.CustomerRepository;

@EnableAutoConfiguration
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@SpringBootTest(classes = TestConfig.class)
@AutoConfigureMockMvc

public class ServiceTest {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CustomerRepository customerRepository;

	Customer c1 = new Customer("john", "tester", "sydney");
	Customer c2 = new Customer("john", "developer", "bangalore");

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		// save now to test fetches and findall.
		customerService.save(c1);
		customerService.save(c2);
	}

	@Test
	public void whenFindAllTest() {

		Iterable<Customer> customers = customerService.findAll();

		assertEquals(customers.iterator().next().getCity(), c1.getCity());
		assertEquals(customers.iterator().next().getLastname(), c1.getLastname());
	}

	@Test
	public void findByFirstNameTest() {

		Customer[] customers = customerService.findCustomerByFirstname("john");

		assertEquals(customers[0].getCity(), c1.getCity());
	}

	@Test
	public void batchSaveTest() {

		Customer c3 = new Customer("john", "tester", "sydney");
		Customer c4 = new Customer("john", "developer", "bangalore");
		Customer[] customers = new Customer[2];
		
		customers[0] = c3;
		customers[1] = c4;

		customerService.batchSave(customers);

	}

}
