package model.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;


public class Seller implements Serializable {
    private Integer id;
    private String name, email;
    private LocalDate birthDate;
    private Double baseSalary;
    private Departament departament;

    public Seller() {
    }

    public Seller(Integer id, String name, String email, LocalDate birthDate, Double baseSalary, Departament departament) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.baseSalary = baseSalary;
        this.departament = departament;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(Double baseSalary) {
        this.baseSalary = baseSalary;
    }

    public Departament getDepartament() {
        return departament;
    }

    public void setDepartament(Departament departament) {
        this.departament = departament;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Seller seller)) return false;
        return Objects.equals(getId(), seller.getId()) && Objects.equals(getName(), seller.getName()) && Objects.equals(getEmail(), seller.getEmail()) && Objects.equals(getBirthDate(), seller.getBirthDate()) && Objects.equals(getBaseSalary(), seller.getBaseSalary()) && Objects.equals(getDepartament(), seller.getDepartament());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getEmail(), getBirthDate(), getBaseSalary(), getDepartament());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Seller{   id = "  + id );
        sb.append(", name='" + name + '\'');
        sb.append(", email='" + email + '\'');
        sb.append(", birthDate=" + birthDate);
        sb.append(", baseSalary=" + baseSalary);
        sb.append("departament - " + departament + '}');
        return sb.toString();
}}
