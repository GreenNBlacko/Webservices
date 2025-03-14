package lt.viko.eif.rgenzuras.sb_sample.model;

import jakarta.persistence.*;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@XmlRootElement(name = "order")
@XmlType(propOrder = {"ID", "purchasedItems", "purchasingCustomer"})
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int ID;

    @ManyToMany(targetEntity = Item.class, fetch = FetchType.EAGER)
    @JoinTable(name = "order_purchased_items", joinColumns = { @JoinColumn(name = "order_id") }, inverseJoinColumns = { @JoinColumn(name = "purchased_items_id")})
    private List<Item> PurchasedItems = new ArrayList<>();

    @ManyToOne(targetEntity = Customer.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "purchasing_customer_id")
    private Customer PurchasingCustomer;

    public Order() {
    }

    public Order(int ID, List<Item> purchasedItems, Customer purchasingCustomer) {
        this.ID = ID;
        PurchasedItems = purchasedItems;
        PurchasingCustomer = purchasingCustomer;
    }

    public int getID() {
        return ID;
    }

    @XmlElement(name = "ID")
    public void setID(int ID) {
        this.ID = ID;
    }

    public List<Item> getPurchasedItems() {
        return PurchasedItems;
    }

    @XmlElementWrapper(name = "Items")
    @XmlElement(name = "Item")
    public void setPurchasedItems(List<Item> purchasedItems) {
        PurchasedItems = purchasedItems;
    }
    
    public void addPurchasedItem(Item purchasedItem) {
        PurchasedItems.add(purchasedItem);
    }

    public Customer getPurchasingCustomer() {
        return PurchasingCustomer;
    }

    @XmlElement(name = "Customer")
    public void setPurchasingCustomer(Customer purchasingCustomer) {
        PurchasingCustomer = purchasingCustomer;
    }

    public float getTotalPrice() {
        float TotalPrice = 0;

        for (int i = 0; i < PurchasedItems.size(); i++) {
            Item item = PurchasedItems.get(i);

            TotalPrice += item.getItemPrice();
        }
        
        return TotalPrice;
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
        for (var item : PurchasedItems) {
            var label = item.getItemName();
            var price = String.format("$%.2f", item.getItemPrice());
            totalPrice += item.getItemPrice();

            output.append(label).append(price.indent(receiptWidth - label.length() - price.length()));
        }

        var dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        var totalLabel = "Total";
        var totalPriceLabel = String.format("$%.2f", totalPrice);
        var purchasingCustomerLabel = "Purchasing customer";
        var purchasingCustomerName = PurchasingCustomer.toString();

        output.append("\n");
        output.append(totalLabel).append(totalPriceLabel.indent(receiptWidth - totalLabel.length() - totalPriceLabel.length()));
        output.append(spacer).append("\n");
        output.append(purchasingCustomerLabel).append(purchasingCustomerName.indent(receiptWidth - purchasingCustomerLabel.length() - purchasingCustomerName.length()));

        return output.toString();
    }
}
