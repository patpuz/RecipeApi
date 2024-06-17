package org.example.hibernate.service;

import org.example.hibernate.model.Ingredient;
import org.example.hibernate.model.User;
import org.example.hibernate.util.JpaUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class IngredientService {
    public void createIngredient(Long userId, String ingredientName) {
        EntityManager entityManager = JpaUtil.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            User user = entityManager.find(User.class, userId);
            if (user != null) {
                Ingredient ingredient = new Ingredient();
                ingredient.setUser(user);
                ingredient.setIngredientName(ingredientName);
                entityManager.persist(ingredient);
            }
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

    public void deleteIngredient(Long id) {
        EntityManager entityManager = JpaUtil.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Ingredient ingredient = entityManager.find(Ingredient.class, id);
            if (ingredient != null) {
                entityManager.remove(ingredient);
            }
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
    public List<Ingredient> findIngredientsByUserId(Long userId) {
        EntityManager entityManager = JpaUtil.getEntityManager();
        try {
            return entityManager.createQuery("from Ingredient where user.id = :userId", Ingredient.class)
                    .setParameter("userId", userId)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }
    }
}
