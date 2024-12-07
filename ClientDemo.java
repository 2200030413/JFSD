package com.klef.jfsd.exam;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;

import javax.persistence.criteria.Predicate;
import java.util.List;

public class ClientDemo {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        // 1. Insert Records
        Customer customer1 = new Customer();
        customer1.setName("Alice");
        customer1.setEmail("alice@example.com");
        customer1.setAge(30);
        customer1.setLocation("New York");

        Customer customer2 = new Customer();
        customer2.setName("Bob");
        customer2.setEmail("bob@example.com");
        customer2.setAge(25);
        customer2.setLocation("Los Angeles");

        session.persist(customer1);
        session.persist(customer2);

        transaction.commit();

        // 2. Criteria Queries
        HibernateCriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        // Example: Fetch Customers with Age > 25
        JpaCriteriaQuery<Customer> query1 = criteriaBuilder.createQuery(Customer.class);
        var root1 = query1.from(Customer.class);
        query1.select(root1).where(criteriaBuilder.greaterThan(root1.get("age"), 25));
        List<Customer> result1 = session.createQuery(query1).getResultList();

        System.out.println("Customers with Age > 25:");
        result1.forEach(c -> System.out.println(c.getName()));

        // Example: Fetch Customers from "Los Angeles"
        JpaCriteriaQuery<Customer> query2 = criteriaBuilder.createQuery(Customer.class);
        var root2 = query2.from(Customer.class);
        query2.select(root2).where(criteriaBuilder.equal(root2.get("location"), "Los Angeles"));
        List<Customer> result2 = session.createQuery(query2).getResultList();

        System.out.println("Customers from Los Angeles:");
        result2.forEach(c -> System.out.println(c.getName()));

        session.close();
        sessionFactory.close();
    }
}
