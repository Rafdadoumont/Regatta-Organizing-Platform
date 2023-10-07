package be.ucll.ip.reeks562.vacation.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "vacation", uniqueConstraints = { @UniqueConstraint(columnNames = { "plaats", "begindatum" })})
public class Vacation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String naam;

    private String plaats;

    private LocalDate begindatum;

    private Integer overnachtingen;

    public Vacation() {}

    public Vacation(String naam, String plaats, LocalDate begindatum, Integer overnachtingen) {
        this.naam = naam;
        this.plaats = plaats;
        this.begindatum = begindatum;
        this.overnachtingen = overnachtingen;
    }

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public String getPlaats() {
        return plaats;
    }

    public LocalDate getBegindatum() {
        return begindatum;
    }

    public Integer getOvernachtingen() {
        return overnachtingen;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public void setPlaats(String plaats) {
        this.plaats = plaats;
    }

    public void setBegindatum(LocalDate begindatum) {
        this.begindatum = begindatum;
    }

    public void setOvernachtingen(Integer overnachtingen) {
        this.overnachtingen = overnachtingen;
    }
}
