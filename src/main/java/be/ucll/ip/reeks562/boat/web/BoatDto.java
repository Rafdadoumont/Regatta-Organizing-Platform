package be.ucll.ip.reeks562.boat.web;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class BoatDto {
    private long id;

    @NotEmpty(message = "boat.name.missing")
    @Size(min = 5, message = "boat.5.characters")
    private String name;

    @NotEmpty(message = "boat.email.missing")
    private String email;

    @NotNull(message = "boat.length.missing")
    private Double length;

    @NotNull(message = "boat.width.missing")
    private Double width;

    @NotNull(message = "boat.height.missing")
    private Double height;

    @NotNull(message = "boat.assuranceNumber.missing")
    @Pattern(regexp = "^[0-9a-zA-Z]{10}$", message = "boat.assuranceNumber.not.valid")
    private String assuranceNumber;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
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

    public Double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public Double getHeight() {
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
}
