package lt.viko.eif.rgenzuras.sb_sample.db;

import lt.viko.eif.rgenzuras.sb_sample.db.mappers.CustomerMapper;
import lt.viko.eif.rgenzuras.sb_sample.db.mappers.ItemMapper;
import lt.viko.eif.rgenzuras.sb_sample.db.repositories.CustomerRepository;
import lt.viko.eif.rgenzuras.sb_sample.db.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lt.viko.eif.rgenzuras.sb_sample.schema.Customer;
import lt.viko.eif.rgenzuras.sb_sample.schema.Item;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WebContext {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private ItemMapper itemMapper;

    public List<Customer> getCustomers() {
        return customerRepository.findAll().stream().map(customerMapper::toDTO).collect(Collectors.toList());
    }

    public Customer getCustomer(int id) {
        for (var customer : getCustomers()) {
            if (customer.getID() == id) return customer;
        }

        return new Customer();
    }

    public void saveCustomer(Customer customer) {
        customerRepository.save(customerMapper.toModel(customer));
    }

    public void deleteCustomer(int id) {
        customerRepository.deleteById(id);
    }

    public List<Item> getItems() {
        return itemRepository.findAll().stream().map(itemMapper::toDTO).collect(Collectors.toList());
    }

    public Item getItem(int id) {
        for (var item : getItems()) {
            if (item.getID() == id) return item;
        }

        return new Item();
    }

    public void saveItem(Item item) {
        itemRepository.save(itemMapper.toModel(item));
    }

    public void deleteItem(int id) {
        itemRepository.deleteById(id);
    }
}
