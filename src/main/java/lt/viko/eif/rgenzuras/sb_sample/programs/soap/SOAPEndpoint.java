package lt.viko.eif.rgenzuras.sb_sample.programs.soap;

import lt.viko.eif.rgenzuras.sb_sample.db.SOAPContext;
import lt.viko.eif.rgenzuras.sb_sample.schema.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class SOAPEndpoint {
    private static final String NAMESPACE_URI = "http://schema.sb-sample.rgenzuras.eif.viko.lt";

    @Autowired
    private SOAPContext context;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCustomerRequest")
    @ResponsePayload
    public GetCustomerResponse getCustomer(@RequestPayload GetCustomerRequest request) {
        GetCustomerResponse response = new GetCustomerResponse();

        response.setCustomer(context.getCustomer(request.getID()));

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getItemRequest")
    @ResponsePayload
    public GetItemResponse getItem(@RequestPayload GetItemRequest request) {
        var response = new GetItemResponse();

        response.setItem(context.getItem(request.getID()));

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addCustomerRequest")
    @ResponsePayload
    public ModifyResponse addCustomer(@RequestPayload AddCustomerRequest request) {
        var response = new ModifyResponse();

        var customer = request.getCustomer();

        try {
            context.saveCustomer(customer);

            response.setResult(true);
        } catch (Exception e) {
            response.setResult(false);
        }

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addItemRequest")
    @ResponsePayload
    public ModifyResponse addItem(@RequestPayload AddItemRequest request) {
        var response = new ModifyResponse();

        var item = request.getItem();

        try {
            context.saveItem(item);

            response.setResult(true);
        } catch (Exception e) {
            response.setResult(false);
        }

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "removeCustomerRequest")
    @ResponsePayload
    public ModifyResponse removeCustomer(@RequestPayload RemoveCustomerRequest request) {
        var response = new ModifyResponse();

        try {
            context.deleteCustomer(request.getID());

            response.setResult(true);
        } catch (Exception e) {
            response.setResult(false);
        }

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "removeItemRequest")
    @ResponsePayload
    public ModifyResponse removeItem(@RequestPayload RemoveItemRequest request) {
        var response = new ModifyResponse();

        try {
            context.deleteItem(request.getID());

            response.setResult(true);
        } catch (Exception e) {
            response.setResult(false);
        }

        return response;
    }
}
