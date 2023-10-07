package be.ucll.ip.reeks562;

import be.ucll.ip.reeks562.boat.domain.Boat;

public class BoatBuilder {
    private String name;
    private String email;
    private double length;
    private double width;
    private double height;
    private String assuranceNumber;

    private BoatBuilder() {
    }

    public static BoatBuilder aBoat() {
        return new BoatBuilder();
    }

    public static BoatBuilder aBoat1(){
        return aBoat()
                .withName("Boat1")
                .withEmail("boat1@mail.com")
                .withLength(10.0)
                .withWidth(5.0)
                .withHeight(2.0)
                .withAssuranceNumber("1111111111");
    }

    public static BoatBuilder aBoat2(){
        return aBoat()
                .withName("Boat2")
                .withEmail("boat2@mail.com")
                .withLength(9.0)
                .withWidth(4.0)
                .withHeight(1.0)
                .withAssuranceNumber("2222222222");
    }

    public static BoatBuilder aBoatWithNoName(){
        return aBoat()
        .withName("")
        .withEmail("boat1@mail.com")
        .withLength(10.0)
        .withWidth(5.0)
        .withHeight(2.0)
        .withAssuranceNumber("1111111111");
    }

    public static BoatBuilder aBoatWithNoEmail(){
        return aBoat()
        .withName("Boat1")
        .withEmail("")
        .withLength(10.0)
        .withWidth(5.0)
        .withHeight(2.0)
        .withAssuranceNumber("1111111111");
    }

    public static BoatBuilder aBoatWithNoLength(){
        return aBoat()
        .withName("Boat1")
        .withEmail("boat1@mail.com")
        .withWidth(5.0)
        .withHeight(2.0)
        .withAssuranceNumber("1111111111");
    }

    public static BoatBuilder aBoatWithNoWidth(){
        return aBoat()
        .withName("Boat1")
        .withEmail("")
        .withLength(10.0)
        .withHeight(2.0)
        .withAssuranceNumber("1111111111");
    }

    public static BoatBuilder aBoatWithNoHeight(){
        return aBoat()
        .withName("Boat1")
        .withEmail("")
        .withLength(10.0)
        .withWidth(5.0)
        .withAssuranceNumber("1111111111");
    }

    public static BoatBuilder aBoatWithNoAssuranceNumber(){
        return aBoat()
        .withName("Boat1")
        .withEmail("")
        .withLength(10.0)
        .withWidth(5.0)
        .withHeight(2.0);
    }

    public BoatBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public BoatBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public BoatBuilder withLength(double length) {
        this.length = length;
        return this;
    }

    public BoatBuilder withWidth(double width) {
        this.width = width;
        return this;
    }

    public BoatBuilder withHeight(double height) {
        this.height = height;
        return this;
    }

    public BoatBuilder withAssuranceNumber(String assuranceNumber) {
        this.assuranceNumber = assuranceNumber;
        return this;
    }

    public Boat build(){
        Boat boat = new Boat();
        boat.setName(name);
        boat.setEmail(email);
        boat.setLength(length);
        boat.setWidth(width);
        boat.setHeight(height);
        boat.setAssuranceNumber(assuranceNumber);
        return boat;
    }
}
