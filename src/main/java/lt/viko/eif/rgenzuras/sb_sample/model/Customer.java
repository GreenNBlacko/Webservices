package lt.viko.eif.rgenzuras.sb_sample.model;

import jakarta.persistence.*;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Definition of the customer table
 * @author ramunas.genzuras@stud.viko.lt
 */
@Entity
@Table(name = "customer")
@XmlRootElement(name = "customer")
@XmlType(propOrder = {"ID", "name", "surname", "email"})
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int ID;

    private String Name;

    private String Surname;

    private String Email;

    public Customer() {

    }

    public Customer(int id, String name, String surname, String email) {
        ID = id;
        Name = name;
        Surname = surname;
        Email = email;
    }

    public int getID() {
        return ID;
    }

    @XmlElement(name = "ID")
    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    @XmlElement(name = "Name")
    public void setName(String name) {
        Name = name;
    }

    public String getSurname() {
        return Surname;
    }

    @XmlElement(name = "Surname")
    public void setSurname(String surname) {
        Surname = surname;
    }

    public String getEmail() {
        return Email;
    }

    @XmlElement(name = "Email")
    public void setEmail(String email) {
        Email = email;
    }

    @Override
    public String toString() {
        return String.format("%s %s", Name, Surname);
    }
}
