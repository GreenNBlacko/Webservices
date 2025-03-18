package lt.viko.eif.rgenzuras.sb_sample.db;

import lt.viko.eif.rgenzuras.sb_sample.db.repositories.CustomerRepository;
import lt.viko.eif.rgenzuras.sb_sample.db.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lt.viko.eif.rgenzuras.sb_sample.schema.Customer;
import lt.viko.eif.rgenzuras.sb_sample.schema.Item;

import java.util.List;

@Service
public class SOAPContext {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ItemRepository itemRepository;

    public List<Customer> getCustomers() {
        return customerRepository.findAll().stream().map(customer -> {
            var mappedCustomer = new Customer();
            mappedCustomer.setID(customer.getID());
            mappedCustomer.setName(customer.getName());
            mappedCustomer.setSurname(customer.getSurname());
            mappedCustomer.setEmail(customer.getEmail());
            return mappedCustomer;
        }).toList();
    }

    public Customer getCustomer(int id) {
        for (var customer : getCustomers()) {
            if (customer.getID() == id) return customer;
        }

        return new Customer();
    }

    public void saveCustomer(Customer customer) {
        var mappedCustomer = new lt.viko.eif.rgenzuras.sb_sample.model.Customer();

        mappedCustomer.setID(customer.getID());
        mappedCustomer.setName(customer.getName());
        mappedCustomer.setSurname(customer.getSurname());
        mappedCustomer.setEmail(customer.getEmail());

        customerRepository.save(mappedCustomer);
    }

    public void deleteCustomer(int id) {
        customerRepository.deleteById(id);
    }

    public List<Item> getItems() {
        return itemRepository.findAll().stream().map(item -> {
            var mappedCustomer = new Item();
            mappedCustomer.setID(item.getID());
            mappedCustomer.setName(item.getItemName());
            mappedCustomer.setPrice(item.getItemPrice());
            return mappedCustomer;
        }).toList();
    }

    public Item getItem(int id) {
        for (var item : getItems()) {
            if (item.getID() == id) return item;
        }

        return new Item();
    }

    public void saveItem(Item item) {
        var mappedItem = new lt.viko.eif.rgenzuras.sb_sample.model.Item();

        mappedItem.setID(item.getID());
        mappedItem.setItemName(item.getName());
        mappedItem.setItemPrice(item.getPrice());

        itemRepository.save(mappedItem);
    }

    public void deleteItem(int id) {
        itemRepository.deleteById(id);
    }
}
