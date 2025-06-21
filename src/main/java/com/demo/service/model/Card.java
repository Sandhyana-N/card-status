package com.demo.service.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Card {

    @Id
    public String cardNumber;
    public String cardType;
    public String cardStatus;

}
