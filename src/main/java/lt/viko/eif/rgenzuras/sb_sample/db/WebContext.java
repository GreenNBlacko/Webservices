package lt.viko.eif.rgenzuras.sb_sample.db;

import lt.viko.eif.rgenzuras.sb_sample.db.mappers.CustomerMapper;
import lt.viko.eif.rgenzuras.sb_sample.db.mappers.ItemMapper;
import lt.viko.eif.rgenzuras.sb_sample.db.repositories.CustomerRepository;
import lt.viko.eif.rgenzuras.sb_sample.db.repositories.ItemRepository;
import lt.viko.eif.rgenzuras.sb_sample.model.rest.CustomerAddRequest;
import lt.viko.eif.rgenzuras.sb_sample.model.rest.ItemAddRequest;
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

        return null;
    }

    public Customer addCustomer(CustomerAddRequest customer) {
        var newCustomer = new lt.viko.eif.rgenzuras.sb_sample.model.Customer();
        newCustomer.setName(customer.getName());
        newCustomer.setSurname(customer.getSurname());
        newCustomer.setEmail(customer.getEmail());

        return customerMapper.toDTO(customerRepository.save(newCustomer));
    }

    public void saveCustomer(Customer customer) {
        customerRepository.save(customerMapper.toModel(customer));
    }

    public boolean deleteCustomer(int id) {
        if(!customerRepository.existsById(id))
            return false;

        customerRepository.deleteById(id);
        return true;
    }

    public List<Item> getItems() {
        return itemRepository.findAll().stream().map(itemMapper::toDTO).collect(Collectors.toList());
    }

    public Item getItem(int id) {
        for (var item : getItems()) {
            if (item.getID() == id) return item;
        }

        return null;
    }

    public Item addItem(ItemAddRequest item) {
        var newItem = new lt.viko.eif.rgenzuras.sb_sample.model.Item();
        newItem.setItemName(item.getName());
        newItem.setItemPrice(item.getPrice());
        return itemMapper.toDTO(itemRepository.save(newItem));
    }

    public void saveItem(Item item) {
        itemRepository.save(itemMapper.toModel(item));
    }

    public boolean deleteItem(int id) {
        if(!itemRepository.existsById(id))
            return false;

        itemRepository.deleteById(id);
        return true;
    }
}
