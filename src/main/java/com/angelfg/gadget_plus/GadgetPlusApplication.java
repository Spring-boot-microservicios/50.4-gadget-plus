package com.angelfg.gadget_plus;

import com.angelfg.gadget_plus.entities.BillEntity;
import com.angelfg.gadget_plus.entities.OrderEntity;
import com.angelfg.gadget_plus.repositories.BillRepository;
import com.angelfg.gadget_plus.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

		// this.orderRepository.findAll().forEach(System.out::println);

		// Si mando a llamar con el toString, me arroja un error
		// Para evitar se puede poner un atributo propio de la entity bill
		// this.billRepository.findAll().forEach(System.out::println);

		// cascadePersist();
		// actualizacionConCascadeMerge();
		// eliminarCascadeDetachOrRemove();

	}

	private void cascadePersist() {
		// CASCADE PERSIST
		// Genera un error:  Not-null property references a transient value
		// porque aun no existe el registro guardado de bill
		BillEntity bill = BillEntity.builder()
				.id("b-17")
				.rfc("ASCSASD897")
				.totalAmount(BigDecimal.TEN)
				.build();

		// La solucion del error anterior seria guardarlo primero
		// this.billRepository.save(bill);
		// Otra soluciones es poner en el order PERSIST: @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)

		OrderEntity order = OrderEntity.builder()
				.createdAt(LocalDateTime.now())
				.clientName("Alex Pereira")
				.bill(bill)
				.build();

		this.orderRepository.save(order);
	}

	private void actualizacionConCascadeMerge() {
		// Se hace la prueba con: @OneToOne(fetch = FetchType.EAGER)
		// En el order si hacemos una actualizacion si lo realizara pero solo de OrderEntity
		OrderEntity order = this.orderRepository.findById(17L).get();
		System.out.println("Pre persist: " + order.getClientName());
		order.setClientName("Michael Pereira 2");

		// Prueba 2
		// En este caso con: @OneToOne(fetch = FetchType.EAGER) no realiza la modificacion
		// Solucion es: @OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
		// para actualizar en ambas tablas

		// CascadeType.PERSIST => es para los save
		// CascadeType.MERGE => es para los updates
		order.getBill().setRfc("AAACCC");

		this.orderRepository.save(order);

		OrderEntity orderUpdated = this.orderRepository.findById(17L).get();
		System.out.println("POst persist: " + orderUpdated.getClientName());
	}

	private void eliminarCascadeDetachOrRemove() {
		// Integramos: @OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH, CascadeType.REMOVE })
		// Elimina tanto al hijo como el padre osea: orderEntity y su hijo billEntity
		OrderEntity order = this.orderRepository.findById(17L).get();
		this.orderRepository.delete(order);
	}

}
