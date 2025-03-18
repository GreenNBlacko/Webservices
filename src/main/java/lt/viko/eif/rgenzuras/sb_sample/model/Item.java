package lt.viko.eif.rgenzuras.sb_sample.model;

import jakarta.persistence.*;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Definition of the item table
 * @author ramunas.genzuras@stud.viko.lt
 */
@Entity
@Table(name = "item")
@XmlRootElement(name = "item")
@XmlType(propOrder = {"ID", "itemName", "itemPrice"})
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int ID;

    private String ItemName;

    private float ItemPrice;

    public Item() {
    }

    public Item(int ID, String itemName, float itemPrice) {
        this.ID = ID;
        ItemName = itemName;
        ItemPrice = itemPrice;
    }

    public int getID() {
        return ID;
    }

    @XmlElement(name = "ID")
    public void setID(int ID) {
        this.ID = ID;
    }

    public String getItemName() {
        return ItemName;
    }

    @XmlElement(name = "Name")
    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public float getItemPrice() {
        return ItemPrice;
    }

    @XmlElement(name = "Price")
    public void setItemPrice(float itemPrice) {
        ItemPrice = itemPrice;
    }
}
