package lt.viko.eif.rgenzuras.sb_sample.programs.rest;

import lt.viko.eif.rgenzuras.sb_sample.db.WebContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lt.viko.eif.rgenzuras.sb_sample.schema.Item;
import lt.viko.eif.rgenzuras.sb_sample.schema.Customer;
import java.util.List;

@RestController
public class RESTEndpoint {
    @Autowired
    private WebContext webContext;

    @GetMapping("customers")
    public List<Customer> getCustomers() {
        return webContext.getCustomers();
    }

    @GetMapping("customers/{id}")
    public Customer getCustomer(@PathVariable int id) {
        return webContext.getCustomer(id);
    }

    @PostMapping("customers")
    public boolean addCustomer(@RequestBody Customer customer) {
        webContext.saveCustomer(customer);
        return true;
    }

    @DeleteMapping("customers/{id}")
    public boolean deleteCustomer(@PathVariable int id) {
        webContext.deleteCustomer(id);
        return true;
    }

    @GetMapping("items")
    public List<Item> getItems() {
        return webContext.getItems();
    }

    @GetMapping("items/{id}")
    public Item getItem(@PathVariable int id) {
        return webContext.getItem(id);
    }

    @PostMapping("items")
    public boolean addItem(@RequestBody Item item) {
        webContext.saveItem(item);
        return true;
    }

    @DeleteMapping("items/{id}")
    public boolean deleteItem(@PathVariable int id) {
        webContext.deleteCustomer(id);
        return true;
    }
}
