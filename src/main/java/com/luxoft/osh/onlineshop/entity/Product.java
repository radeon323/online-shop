package com.luxoft.osh.onlineshop.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@Entity
public class Product {
    private int id;
    private String name;
    private double price;
    private LocalDateTime creationDate;
}
