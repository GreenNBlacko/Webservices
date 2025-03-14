package lt.viko.eif.rgenzuras.sb_sample.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Class for storing order receipt data
 * @author ramunas.genzuras@stud.viko.lt
 * @see Order
 */
@XmlRootElement(name = "receipt")
@XmlType(propOrder = {"customer", "items", "transactionDate"})
public class Receipt {
    private Customer customer;
    private List<Item> items;
    private Date transactionDate;

    public Receipt() {
        this.transactionDate = Calendar.getInstance().getTime();
    }

    public Receipt(Customer customer, List<Item> items) {
        this.customer = customer;
        this.items = items;
        this.transactionDate = Calendar.getInstance().getTime();
    }

    public Receipt(Customer customer, ArrayList<Item> items, Date transactionDate) {
        this.customer = customer;
        this.items = items;
        this.transactionDate = transactionDate;
    }

    @XmlElement(name = "customer")
    public Customer getCustomer() {
        return customer;
    }


    @XmlElementWrapper(name = "items")
    @XmlElement(name = "item")
    public List<Item> getItems() {
        return items;
    }

    @XmlElement(name = "date")
    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();

        String spacer = "---------------------------------------------";
        String storeName = "Grayscale's store";
        int receiptWidth = spacer.length();
        int labelSpacing = (spacer.length() - storeName.length()) / 2;
        float totalPrice = 0;

        output.append(storeName.indent(labelSpacing));
        output.append(spacer).append('\n');
        for (var item : items) {
            var label = item.getItemName();
            var price = String.format("$%.2f", item.getItemPrice());
            totalPrice += item.getItemPrice();

            output.append(label).append(price.indent(receiptWidth - label.length() - price.length()));
        }

        var dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        var totalLabel = "Total";
        var totalPriceLabel = String.format("$%.2f", totalPrice);
        var purchasingCustomerLabel = "Purchasing customer";
        var purchasingCustomerName = customer.toString();
        var dateLabel = "Date of purchase";
        var dateDisplay = dateFormat.format(transactionDate);

        output.append("\n");
        output.append(totalLabel).append(totalPriceLabel.indent(receiptWidth - totalLabel.length() - totalPriceLabel.length()));
        output.append(spacer).append("\n");
        output.append(purchasingCustomerLabel).append(purchasingCustomerName.indent(receiptWidth - purchasingCustomerLabel.length() - purchasingCustomerName.length()));
        output.append(dateLabel).append(dateDisplay.indent(receiptWidth - dateLabel.length() - dateDisplay.length()));

        return output.toString();
    }
}
