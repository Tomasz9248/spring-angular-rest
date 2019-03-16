package com.tomek.model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String team;
    private Integer number;

    Player() {}

    public Player(String firstName, String lastName, String team, Integer number) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.team = team;
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(getId(), player.getId()) &&
                Objects.equals(getFirstName(), player.getFirstName()) &&
                Objects.equals(getLastName(), player.getLastName()) &&
                Objects.equals(getTeam(), player.getTeam()) &&
                Objects.equals(getNumber(), player.getNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getTeam(), getNumber());
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", team='" + team + '\'' +
                ", number=" + number +
                '}';
    }
}