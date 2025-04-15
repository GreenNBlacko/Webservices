package lt.viko.eif.rgenzuras.sb_sample.programs.rest;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lt.viko.eif.rgenzuras.sb_sample.db.WebContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lt.viko.eif.rgenzuras.sb_sample.schema.Item;
import lt.viko.eif.rgenzuras.sb_sample.schema.Customer;

import java.util.List;

@RestController
@RequestMapping("/api/store")
public class RESTEndpoint {
    @Autowired
    private WebContext webContext;

    @Tag(name = "Customer", description = "Operations related to customers")
    @GetMapping("customers")
    public List<Customer> getCustomers() {
        return webContext.getCustomers();
    }

    @Tag(name = "Customer", description = "Operations related to customers")
    @GetMapping("customers/{id}")
    public Customer getCustomer(@PathVariable int id) {
        return webContext.getCustomer(id);
    }

    @Tag(name = "Customer", description = "Operations related to customers")
    @PostMapping("customers")
    public boolean addCustomer(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Customer object that needs to be added", required = true, content = @Content(schema = @Schema(implementation = Customer.class))) @RequestBody Customer customer) {
        webContext.saveCustomer(customer);
        return true;
    }

    @Tag(name = "Customer", description = "Operations related to customers")
    @DeleteMapping("customers/{id}")
    public boolean deleteCustomer(@PathVariable int id) {
        webContext.deleteCustomer(id);
        return true;
    }

    @Tag(name = "Item", description = "Operations related to items")
    @GetMapping("items")
    public List<Item> getItems() {
        return webContext.getItems();
    }

    @Tag(name = "Item", description = "Operations related to items")
    @GetMapping("items/{id}")
    public Item getItem(@PathVariable int id) {
        return webContext.getItem(id);
    }

    @Tag(name = "Item", description = "Operations related to items")
    @PostMapping("items")
    public boolean addItem(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Item object that needs to be added", required = true, content = @Content(schema = @Schema(implementation = Item.class))) @RequestBody Item item) {
        webContext.saveItem(item);
        return true;
    }

    @Tag(name = "Item", description = "Operations related to items")
    @DeleteMapping("items/{id}")
    public boolean deleteItem(@PathVariable int id) {
        webContext.deleteCustomer(id);
        return true;
    }
}
