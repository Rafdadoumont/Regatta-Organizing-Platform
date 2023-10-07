package be.ucll.ip.reeks562.boat.domain;

import be.ucll.ip.reeks562.storage.domain.Storage;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "boat", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "name", "email" }),
        @UniqueConstraint(columnNames = { "assuranceNumber" })
})
public class Boat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String email;
    private double length;
    private double width;
    private double height;
    private String assuranceNumber;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "storage_id")
    private Storage storage;

    public Boat() {
    }

    public Boat(String name, String email, double length, double width, double height,
            String assuranceNumber) {
        this.name = name;
        this.email = email;
        this.length = length;
        this.width = width;
        this.height = height;
        this.assuranceNumber = assuranceNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getAssuranceNumber() {
        return assuranceNumber;
    }

    public void setAssuranceNumber(String assuranceNumber) {
        this.assuranceNumber = assuranceNumber;
    }

    public Storage getStorage() {
        return storage;
    }

    public String getStorageName() {
        if (storage != null) {
            return storage.getName();
        }
        return null;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }
}
