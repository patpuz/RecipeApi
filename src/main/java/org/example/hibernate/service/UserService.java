package org.example.hibernate.service;

import org.example.hibernate.model.User;
import org.example.hibernate.util.JpaUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class UserService {
    public void createUser(String username, String password) {
        EntityManager entityManager = JpaUtil.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            entityManager.persist(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    public List<User> findAllUsers() {
        EntityManager entityManager = JpaUtil.getEntityManager();
        try {
            return entityManager.createQuery("from User", User.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }
    }


}
