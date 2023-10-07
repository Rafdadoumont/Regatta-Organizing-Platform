package be.ucll.ip.reeks562;

import be.ucll.ip.reeks562.boat.web.BoatDto;

public class BoatDtoBuilder {
    private String name;
    private String email;
    private double length;
    private double width;
    private double height;
    private String assuranceNumber;

    private BoatDtoBuilder() {
    }

    public static BoatDtoBuilder aBoat() {
        return new BoatDtoBuilder();
    }

    public static BoatDtoBuilder aBoat1(){
        return aBoat()
                .withName("Boat1")
                .withEmail("boat1@mail.com")
                .withLength(10.0)
                .withWidth(5.0)
                .withHeight(2.0)
                .withAssuranceNumber("1111111111");
    }

    public static BoatDtoBuilder aBoat2(){
        return aBoat()
                .withName("Boat2")
                .withEmail("boat2@mail.com")
                .withLength(9.0)
                .withWidth(4.0)
                .withHeight(1.0)
                .withAssuranceNumber("2222222222");
    }

    public static BoatDtoBuilder aBoatWithNoName(){
        return aBoat()
        .withName("")
        .withEmail("boat1@mail.com")
        .withLength(10.0)
        .withWidth(5.0)
        .withHeight(2.0)
        .withAssuranceNumber("1111111111");
    }

    public static BoatDtoBuilder aBoatWithNoEmail(){
        return aBoat()
        .withName("Boat1")
        .withEmail("")
        .withLength(10.0)
        .withWidth(5.0)
        .withHeight(2.0)
        .withAssuranceNumber("1111111111");
    }

    public static BoatDtoBuilder aBoatWithNoLength(){
        return aBoat()
        .withName("Boat1")
        .withEmail("boat1@mail.com")
        .withWidth(5.0)
        .withHeight(2.0)
        .withAssuranceNumber("1111111111");
    }

    public static BoatDtoBuilder aBoatWithNoWidth(){
        return aBoat()
        .withName("Boat1")
        .withEmail("")
        .withLength(10.0)
        .withHeight(2.0)
        .withAssuranceNumber("1111111111");
    }

    public static BoatDtoBuilder aBoatWithNoHeight(){
        return aBoat()
        .withName("Boat1")
        .withEmail("")
        .withLength(10.0)
        .withWidth(5.0)
        .withAssuranceNumber("1111111111");
    }

    public static BoatDtoBuilder aBoatWithNoAssuranceNumber(){
        return aBoat()
        .withName("Boat1")
        .withEmail("")
        .withLength(10.0)
        .withWidth(5.0)
        .withHeight(2.0);
    }

    public BoatDtoBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public BoatDtoBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public BoatDtoBuilder withLength(double length) {
        this.length = length;
        return this;
    }

    public BoatDtoBuilder withWidth(double width) {
        this.width = width;
        return this;
    }

    public BoatDtoBuilder withHeight(double height) {
        this.height = height;
        return this;
    }

    public BoatDtoBuilder withAssuranceNumber(String assuranceNumber) {
        this.assuranceNumber = assuranceNumber;
        return this;
    }

    public BoatDto build(){
        BoatDto boat = new BoatDto();
        boat.setName(name);
        boat.setEmail(email);
        boat.setLength(length);
        boat.setWidth(width);
        boat.setHeight(height);
        boat.setAssuranceNumber(assuranceNumber);
        return boat;
    }
}
