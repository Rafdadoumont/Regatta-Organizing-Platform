package be.ucll.ip.reeks562.vacation.web;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.*;

public class VacationDto {
    @NotNull
    private long id;

    @NotEmpty(message = "vacation.naam.missing")
    @Size(min = 5, message = "vacation.naam.size")
    private String naam;

    @NotEmpty(message = "vacation.plaats.missing")
    private String plaats;

    @NotNull(message = "vacation.begindatum.missing")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate begindatum;

    @NotNull(message = "vacation.overnachtingen.missing")
    @Min(value = 1, message = "vacation.overnachtingen.min")
    private Integer overnachtingen;

    public void setId(long id) {
        this.id = id;
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
}
