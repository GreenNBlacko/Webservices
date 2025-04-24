package lt.viko.eif.rgenzuras.sb_sample.programs.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lt.viko.eif.rgenzuras.sb_sample.db.WebContext;
import lt.viko.eif.rgenzuras.sb_sample.model.rest.CustomerAddRequest;
import lt.viko.eif.rgenzuras.sb_sample.model.rest.ItemAddRequest;
import lt.viko.eif.rgenzuras.sb_sample.model.rest.ModifyResponse;
import lt.viko.eif.rgenzuras.sb_sample.schema.Customer;
import lt.viko.eif.rgenzuras.sb_sample.schema.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @Operation(description = "Get a list of all customers")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    @GetMapping(value = "customers", produces = "application/json")
    public ResponseEntity<CollectionModel<EntityModel<Customer>>> getCustomers() {
        List<EntityModel<Customer>> customers = webContext.getCustomers().stream()
                .map(customer -> EntityModel.of(customer,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RESTEndpoint.class).getCustomer(customer.getID())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RESTEndpoint.class).getCustomers()).withRel("all-customers")))
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(customers,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RESTEndpoint.class).getCustomers()).withSelfRel()));
    }

    @Tag(name = "Customer", description = "Operations related to customers")
    @Operation(description = "Get customer at specific ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer found successfully"),
            @ApiResponse(responseCode = "404", description = "ID does not exist")
    })
    @GetMapping(value = "customers/{id}", produces = "application/json")
    public ResponseEntity<EntityModel<Customer>> getCustomer(@PathVariable @NotNull @NotBlank int id) {
        Customer customer = webContext.getCustomer(id);
        if (customer == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.ok(
                EntityModel.of(customer,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RESTEndpoint.class).getCustomer(id)).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RESTEndpoint.class).getCustomers()).withRel("all-customers")));
    }

    @Tag(name = "Customer", description = "Operations related to customers")
    @Operation(description = "Add a new customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer added successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping(value = "customers", produces = "application/json")
    public ResponseEntity<EntityModel<ModifyResponse>> addCustomer(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Customer object that needs to be added", required = true,
                    content = @Content(schema = @Schema(implementation = CustomerAddRequest.class)))
            @RequestBody CustomerAddRequest customer) {

        try {
            var addedCustomer = webContext.addCustomer(customer);
            var body = EntityModel.of(new ModifyResponse("Customer added"),
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RESTEndpoint.class).getCustomer(addedCustomer.getID())).withRel("view-customer"),
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RESTEndpoint.class).getCustomers()).withRel("all-customers"));

            return ResponseEntity.status(HttpStatus.CREATED).body(body);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(EntityModel.of(new ModifyResponse("Error: " + e.getMessage())));
        }
    }

    @Tag(name = "Customer", description = "Operations related to customers")
    @Operation(description = "Delete a customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer deleted successfully"),
            @ApiResponse(responseCode = "404", description = "ID does not exist")
    })
    @DeleteMapping(value =  "customers/{id}", produces = "application/json")
    public ResponseEntity<EntityModel<ModifyResponse>> deleteCustomer(@PathVariable int id) {
        boolean deleted = webContext.deleteCustomer(id);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(EntityModel.of(new ModifyResponse("Customer not found")));
        }
        return ResponseEntity.ok(
                EntityModel.of(new ModifyResponse("Customer deleted"),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RESTEndpoint.class).getCustomers()).withRel("all-customers")));
    }

    // ======= ITEMS =======
    @Tag(name = "Item", description = "Operations related to items")
    @Operation(description = "Get a list of all items")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    @GetMapping(value = "items", produces = "application/json")
    public ResponseEntity<CollectionModel<EntityModel<Item>>> getItems() {
        List<EntityModel<Item>> items = webContext.getItems().stream()
                .map(item -> EntityModel.of(item,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RESTEndpoint.class).getItem(item.getID())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RESTEndpoint.class).getItems()).withRel("all-items"))).collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(items,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RESTEndpoint.class).getItems()).withSelfRel()));
    }

    @Tag(name = "Item", description = "Operations related to items")
    @Operation(description = "Get an item with a specific ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item found successfully"),
            @ApiResponse(responseCode = "404", description = "ID does not exist")
    })
    @GetMapping(value = "items/{id}", produces = "application/json")
    public ResponseEntity<EntityModel<Item>> getItem(@PathVariable int id) {
        Item item = webContext.getItem(id);
        if (item == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.ok(
                EntityModel.of(item,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RESTEndpoint.class).getItem(id)).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RESTEndpoint.class).getItems()).withRel("all-items")));
    }

    @Tag(name = "Item", description = "Operations related to items")
    @Operation(description = "Add a new item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item added successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping(value = "items", produces = "application/json")
    public ResponseEntity<EntityModel<ModifyResponse>> addItem(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Item object that needs to be added", required = true,
                    content = @Content(schema = @Schema(implementation = ItemAddRequest.class)))
            @RequestBody ItemAddRequest item) {

        try {
            var addedItem = webContext.addItem(item);
            var body = EntityModel.of(new ModifyResponse("Item added"),
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RESTEndpoint.class).getItem(addedItem.getID())).withRel("view-item"),
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RESTEndpoint.class).getItems()).withRel("all-items"));

            return ResponseEntity.status(HttpStatus.CREATED).body(body);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(EntityModel.of(new ModifyResponse("Error: " + e.getMessage())));
        }
    }

    @Tag(name = "Item", description = "Operations related to items")
    @ApiResponse(responseCode = "200", description = "Item deleted successfully")
    @Operation(description = "Delete an item")
    @DeleteMapping(value = "items/{id}", produces = "application/json")
    public ResponseEntity<EntityModel<ModifyResponse>> deleteItem(@PathVariable int id) {
        boolean deleted = webContext.deleteItem(id);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(EntityModel.of(new ModifyResponse("Item not found")));
        }

        return ResponseEntity.ok(
                EntityModel.of(new ModifyResponse("Item deleted"),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RESTEndpoint.class).getItems()).withRel("all-items")));
    }
}
