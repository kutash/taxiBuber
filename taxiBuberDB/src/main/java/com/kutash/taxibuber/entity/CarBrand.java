package com.kutash.taxibuber.entity;

public class CarBrand extends AbstractEntity {

    private String name;

    public CarBrand(String name) {
        this.name = name;
    }

    public CarBrand(int id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        CarBrand carBrand = (CarBrand) o;

        return name != null ? name.equals(carBrand.name) : carBrand.name == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CarBrand{" +
                "name='" + name + '\'' +
                "} " + super.toString();
    }
}
