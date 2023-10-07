package be.ucll.ip.reeks562.storage.domain;

import be.ucll.ip.reeks562.boat.domain.Boat;
import be.ucll.ip.reeks562.generic.ServiceException;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "storage", uniqueConstraints = { @UniqueConstraint(columnNames = { "name", "postalCode" })})
public class Storage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private int postalCode;
    private double space;
    private double height;

    @JsonManagedReference
    @OneToMany(mappedBy = "storage")
    private List<Boat> boats;

    public Storage() {

    }

    public Storage(String name, int postalCode, double space, double height) {
        this.name = name;
        this.postalCode = postalCode;
        this.space = space;
        this.height = height;
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

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public double getSpace() {
        return space;
    }

    public void setSpace(double space) {
        this.space = space;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public List<Boat> getBoats() {
        return boats;
    }

    private boolean hasSpaceForBoat(Boat boat) {
        double occupiedArea = 0;
        for (Boat storedBoat : this.getBoats()) {
            occupiedArea += storedBoat.getWidth()* storedBoat.getHeight();
        }
        occupiedArea += boat.getWidth() * boat.getHeight();

        return occupiedArea <= this.getSpace() * 0.8 && boat.getHeight() <= this.getHeight();
    }

    public void addBoat(Boat boat) {
        if (!hasSpaceForBoat(boat)) {
            throw new ServiceException("add.boat.to.storage", "storage.full");
        }
        if (boat.getStorage() != null) {
            throw new ServiceException("add.boat.to.storage", "boat.already.stored");
        }
        if (this.boats.stream().anyMatch((boat1 -> boat1.getEmail().equals(boat.getEmail())))) {
            throw new ServiceException("add.boat.to.storage", "only.one.boat.allowed.per.owner.per.storage");
        }

        boat.setStorage(this);
        this.boats.add(boat);
    }

    public Boat removeBoat(Boat boat) {
        if (!this.boats.remove(boat)) {
            throw new ServiceException("removeBoatFromStorage", "boat.not.in.storage");
        }
        boat.setStorage(null);
        return boat;
    }
}
