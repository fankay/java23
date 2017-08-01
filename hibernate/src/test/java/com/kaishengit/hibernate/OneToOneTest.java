package com.kaishengit.hibernate;

import com.kaishengit.pojo.Card;
import com.kaishengit.pojo.Person;
import org.junit.Test;

public class OneToOneTest extends BaseTestCase {

    @Test
    public void save() {

        Person person = new Person();
        person.setPersonName("王老五");

        Card card = new Card();
        card.setCardNum("X002");
        card.setPerson(person);

        session.save(person);
        session.save(card);
    }

    @Test
    public void find() {
        Person person = (Person) session.get(Person.class,2);
        System.out.println(person.getPersonName());
        System.out.println(person.getCard().getCardNum());
    }

    @Test
    public void find2() {
        Card card = (Card) session.get(Card.class,1);
        System.out.println(card.getCardNum());
        System.out.println(card.getPerson().getPersonName());
    }

    @Test
    public void delete() {
        Person person = (Person) session.get(Person.class,2);
        session.delete(person);
    }

}
