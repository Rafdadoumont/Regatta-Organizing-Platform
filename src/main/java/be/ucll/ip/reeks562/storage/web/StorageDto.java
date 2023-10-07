package be.ucll.ip.reeks562.storage.web;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class StorageDto {
    private long id;

    @NotBlank(message = "storage.name.missing")
    @Size(min = 5, message = "storage.name.size")
    private String name;

    @NotNull(message = "storage.postalCode.invalid")
    @Positive(message = "storage.postalCode.negative")
    @Min(value = 1000, message = "storage.postalCode.invalid")
    @Max(value = 9999, message = "storage.postalCode.invalid")
    private Integer postalCode;

    @NotNull(message = "storage.space.missing")
    @Positive(message = "storage.space.negative")
    private Double space;

    @NotNull(message = "storage.height.missing")
    @Positive(message = "storage.height.negative")
    private Double height;

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

    public Integer getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(Integer postalCode) {
        this.postalCode = postalCode;
    }

    public Double getSpace() {
        return space;
    }

    public void setSpace(Double space) {
        this.space = space;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }
}
