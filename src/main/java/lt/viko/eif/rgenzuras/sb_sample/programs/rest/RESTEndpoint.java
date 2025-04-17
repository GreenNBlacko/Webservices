package lt.viko.eif.rgenzuras.sb_sample.programs.rest;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lt.viko.eif.rgenzuras.sb_sample.db.WebContext;
import lt.viko.eif.rgenzuras.sb_sample.model.rest.CustomerAddRequest;
import lt.viko.eif.rgenzuras.sb_sample.model.rest.ItemAddRequest;
import lt.viko.eif.rgenzuras.sb_sample.model.rest.ModifyResponse;
import lt.viko.eif.rgenzuras.sb_sample.schema.Customer;
import lt.viko.eif.rgenzuras.sb_sample.schema.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/store")
public class RESTEndpoint {
    @Autowired
    private WebContext webContext;

    // ======= CUSTOMERS =======
    @Tag(name = "Customer", description = "Operations related to customers")
    @GetMapping("customers")
    public CollectionModel<EntityModel<Customer>> getCustomers() {
        List<EntityModel<Customer>> customers = webContext.getCustomers().stream()
                .map(customer -> EntityModel.of(customer,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RESTEndpoint.class).getCustomer(customer.getID())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RESTEndpoint.class).getCustomers()).withRel("all-customers")))
                .collect(Collectors.toList());

        return CollectionModel.of(customers,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RESTEndpoint.class).getCustomers()).withSelfRel());
    }

    @Tag(name = "Customer", description = "Operations related to customers")
    @GetMapping("customers/{id}")
    public EntityModel<Customer> getCustomer(@PathVariable int id) {
        Customer customer = webContext.getCustomer(id);
        return EntityModel.of(customer,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RESTEndpoint.class).getCustomer(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RESTEndpoint.class).getCustomers()).withRel("all-customers"));
    }

    @Tag(name = "Customer", description = "Operations related to customers")
    @PostMapping("customers")
    public EntityModel<ModifyResponse> addCustomer(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Customer object that needs to be added", required = true,
                    content = @Content(schema = @Schema(implementation = CustomerAddRequest.class)))
            @RequestBody CustomerAddRequest customer) {
        var addedCustomer = webContext.addCustomer(customer);
        return EntityModel.of(new ModifyResponse("Customer added"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RESTEndpoint.class).getCustomer(addedCustomer.getID())).withRel("view-customer"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RESTEndpoint.class).getCustomers()).withRel("all-customers"));
    }

    @Tag(name = "Customer", description = "Operations related to customers")
    @DeleteMapping("customers/{id}")
    public EntityModel<ModifyResponse> deleteCustomer(@PathVariable int id) {
        webContext.deleteCustomer(id);
        return EntityModel.of(new ModifyResponse("Customer deleted"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RESTEndpoint.class).getCustomers()).withRel("all-customers"));
    }

    // ======= ITEMS =======
    @Tag(name = "Item", description = "Operations related to items")
    @GetMapping("items")
    public CollectionModel<EntityModel<Item>> getItems() {
        List<EntityModel<Item>> items = webContext.getItems().stream()
                .map(item -> EntityModel.of(item,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RESTEndpoint.class).getItem(item.getID())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RESTEndpoint.class).getItems()).withRel("all-items")))
                .collect(Collectors.toList());

        return CollectionModel.of(items,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RESTEndpoint.class).getItems()).withSelfRel());
    }

    @Tag(name = "Item", description = "Operations related to items")
    @GetMapping("items/{id}")
    public EntityModel<Item> getItem(@PathVariable int id) {
        Item item = webContext.getItem(id);
        return EntityModel.of(item,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RESTEndpoint.class).getItem(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RESTEndpoint.class).getItems()).withRel("all-items"));
    }

    @Tag(name = "Item", description = "Operations related to items")
    @PostMapping("items")
    public EntityModel<ModifyResponse> addItem(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Item object that needs to be added", required = true,
                    content = @Content(schema = @Schema(implementation = ItemAddRequest.class)))
            @RequestBody ItemAddRequest item) {

        var addedItem = webContext.addItem(item);
        return EntityModel.of(new ModifyResponse("Item added"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RESTEndpoint.class).getItem(addedItem.getID())).withRel("view-item"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RESTEndpoint.class).getItems()).withRel("all-items"));
    }

    @Tag(name = "Item", description = "Operations related to items")
    @DeleteMapping("items/{id}")
    public EntityModel<ModifyResponse> deleteItem(@PathVariable int id) {
        webContext.deleteItem(id);
        return EntityModel.of(new ModifyResponse("Item deleted"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RESTEndpoint.class).getItems()).withRel("all-items"));
    }
}
