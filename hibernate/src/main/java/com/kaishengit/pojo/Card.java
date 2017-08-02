package com.kaishengit.pojo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "card")
public class Card {

    @Id
    @GenericGenerator(name = "FK",strategy = "foreign",parameters = @org.hibernate.annotations.Parameter(name = "property",value = "person"))
    @GeneratedValue(generator = "FK")
    private int id;

    @Column(name = "card_num")
    private String cardNum;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Person person;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (id != card.id) return false;
        if (cardNum != null ? !cardNum.equals(card.cardNum) : card.cardNum != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (cardNum != null ? cardNum.hashCode() : 0);
        return result;
    }
}
