package com.angelfg.gadget_plus;

import com.angelfg.gadget_plus.entities.*;
import com.angelfg.gadget_plus.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@SpringBootApplication
public class GadgetPlusApplication implements CommandLineRunner {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private BillRepository billRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductCatalogRepository productCatalogRepository;

	@Autowired
	private CategoryRepository categoryRepository;

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
		// oneToMany();
		// orephanRemovalAndCascadeDelete();

		// relacionesProductsOrdenesYCatalogos();

		manyToMany();

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

	private void oneToMany() {
		OrderEntity order = this.orderRepository.findById(1L).orElseThrow();

		ProductEntity product1 = ProductEntity.builder()
				.quantity(BigInteger.ONE)
				.build();

		ProductEntity product2 = ProductEntity.builder()
				.quantity(BigInteger.TWO)
				.build();

		ProductEntity product3 = ProductEntity.builder()
				.quantity(BigInteger.TEN)
				.build();

		Set<ProductEntity> products = Set.of(product1, product2, product3);
		order.setProducts(products);

		// HAsta este punto no me generaria las relaciones, ya que debemos
		// mostrarle a los products que orden va a tener con un forEach

		products.forEach(product -> product.setOrder(order)); // relacion inversa
		this.orderRepository.save(order);
	}

	private void orephanRemovalAndCascadeDelete() {
		OrderEntity order = this.orderRepository.findById(1L).orElseThrow();

		// Eliminar el primer elemento
		//  @OneToMany(
		//        mappedBy = "order",
		//        fetch = FetchType.EAGER,
		//        cascade = CascadeType.ALL,
		//        orphanRemoval = true
		//    )
		// Aqui lo que pasa es que elimina todos los elementos por el
		// cascade = CascadeType.ALL y el orphanRemoval = true
		ProductEntity productEntity = order.getProducts().stream().findFirst().orElseThrow();
		order.getProducts().remove(productEntity);

		// La soluciones es que en ProductEntity quitemos el Cascade de tipo ALL

		this.orderRepository.save(order);
	}

	private void relacionesProductsOrdenesYCatalogos() {
		 // this.productCatalogRepository.findAll().forEach(System.out::println);
		ProductCatalogEntity productCatalog1 = this.productCatalogRepository.findAll().get(0);
		ProductCatalogEntity productCatalog2 = this.productCatalogRepository.findAll().get(4);
		ProductCatalogEntity productCatalog3 = this.productCatalogRepository.findAll().get(7);

		OrderEntity order = this.orderRepository.findById(1L).orElseThrow();

		ProductEntity product1 = ProductEntity.builder().quantity(BigInteger.ONE).build();
		ProductEntity product2 = ProductEntity.builder().quantity(BigInteger.TWO).build();
		ProductEntity product3 = ProductEntity.builder().quantity(BigInteger.TEN).build();

		Set<ProductEntity> products = Set.of(product1, product2, product3);

		product1.setCatalog(productCatalog1);
		product2.setCatalog(productCatalog2);
		product3.setCatalog(productCatalog3);

		order.setProducts(products);

		products.forEach(product -> product.setOrder(order));

		this.orderRepository.save(order);
	}

	private void manyToMany() {
		final CategoryEntity HOME = this.categoryRepository.findById(1L).orElseThrow();
		final CategoryEntity OFFICE = this.categoryRepository.findById(2L).orElseThrow();

		this.productCatalogRepository.findAll().forEach(product -> {

			if (product.getDescription().contains("home")) {
				product.addCategory(HOME);
			}

			if (product.getDescription().contains("office")) {
				product.addCategory(OFFICE);
			}

			this.productCatalogRepository.save(product);
		});

		// -- Muchos a muchos tabla de rompimiento
		//select * from product_join_category pjc;
		//select * from categories c;
		//select * from products_catalog pc;
		//
		//select * from products_catalog pc
		//	inner join product_join_category pjc on pc.id  = pjc.id_product
		//	inner join categories c on c.id = pjc.id_category;
	}

}
