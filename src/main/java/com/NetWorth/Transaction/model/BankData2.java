package com.NetWorth.Transaction.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="")
public class BankData2 {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id2;

    private String txn2;

    private double amount2;

    private LocalDate date2;

}
