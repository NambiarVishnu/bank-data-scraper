package com.NetWorth.Transaction.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@Entity
@Table(name="data")
public class BankData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="transactiondetails")
    private String txndetails;

    @Column(name="amount")
    private Double amount;

    @Column(name="date")
    private LocalDate date;



}
