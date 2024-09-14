package com.angelfg.gadget_plus;

import com.angelfg.gadget_plus.repositories.BillRepository;
import com.angelfg.gadget_plus.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GadgetPlusApplication implements CommandLineRunner {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private BillRepository billRepository;

	public static void main(String[] args) {
		SpringApplication.run(GadgetPlusApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		 this.orderRepository.findAll().forEach(System.out::println);

		// Si mando a llamar con el toString, me arroja un error
		// Para evitar se puede poner un atributo propio de la entity bill
		// this.billRepository.findAll().forEach(System.out::println);

	}

}
