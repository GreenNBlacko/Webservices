package lt.viko.eif.rgenzuras.sb_sample.db;

import jakarta.transaction.Transactional;
import lt.viko.eif.rgenzuras.sb_sample.db.repositories.CustomerRepository;
import lt.viko.eif.rgenzuras.sb_sample.db.repositories.ItemRepository;
import lt.viko.eif.rgenzuras.sb_sample.db.repositories.OrderRepository;
import lt.viko.eif.rgenzuras.sb_sample.model.Customer;
import lt.viko.eif.rgenzuras.sb_sample.model.Item;
import lt.viko.eif.rgenzuras.sb_sample.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PrintStream;
import java.util.List;

@Service
public class DatabaseContext {
	private final CustomerRepository customerRepository;

	private final ItemRepository itemRepository;

	private final OrderRepository orderRepository;

	private final PrintStream Console = System.out;

	@Autowired
	public DatabaseContext(CustomerRepository customerRepository, ItemRepository itemRepository, OrderRepository orderRepository) {
		this.customerRepository = customerRepository;
		this.itemRepository = itemRepository;
		this.orderRepository = orderRepository;
	}

	public List<Order> ListOrders() {
		return orderRepository.findAll();
	}

	public List<Item> ListItems() {
		return itemRepository.findAll();
	}

	public List<Customer> ListCustomers() {
		return customerRepository.findAll();
	}

	public Order createOrder(Customer customer) {
		Console.println("Creating a new order for: " + customer.getName() + " " + customer.getSurname());

		var newOrder = new Order();
		newOrder.setPurchasingCustomer(customer);

		return orderRepository.save(newOrder);
	}

	@Transactional
	public void UpdateOrder(Order order) {
		orderRepository.save(order);
	}
}
