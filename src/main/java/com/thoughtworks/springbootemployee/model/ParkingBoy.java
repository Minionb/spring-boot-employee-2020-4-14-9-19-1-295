package com.thoughtworks.springbootemployee.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "parking_boy")
public class ParkingBoy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nickname;
    private Integer employeeId;

    @OneToOne
    @JoinColumn(name = "employeeId", insertable = false, updatable = false)
    private Employee employee;

    public ParkingBoy(Integer id, String nickname, Integer employeeId) {
        this.id = id;
        this.nickname = nickname;
        this. employeeId = employeeId;
    }
}
