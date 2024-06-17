package org.example.hibernate.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.hibernate.model.Recipe;
import org.example.hibernate.util.HttpClientUtil;
import java.io.IOException;
import java.util.List;
import org.example.hibernate.model.User;
import org.example.hibernate.util.JpaUtil;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;


public class RecipeService {

    private static final String API_KEY = "69ffa3a152bd44a5987320f2522aea7f";

    public List<Recipe> findRecipesByIngredients(List<String> ingredients) throws IOException {
        String ingredientsParam = String.join(",", ingredients);
        String url = String.format("https://api.spoonacular.com/recipes/findByIngredients?ingredients=%s&apiKey=%s", ingredientsParam, API_KEY);

        String response = HttpClientUtil.sendGetRequest(url);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response, new TypeReference<List<Recipe>>() {});
    }
    public void createRecipe(Long userId, String title, String image) {
        EntityManager entityManager = JpaUtil.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            User user = entityManager.find(User.class, userId);
            if (user != null) {
                Recipe recipe = new Recipe();
                recipe.setUser(user);
                recipe.setTitle(title);
                recipe.setImage(image);
                entityManager.persist(recipe);
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

    public List<Recipe> findRecipesByUserId(Long userId) {
        EntityManager entityManager = JpaUtil.getEntityManager();
        try {
            return entityManager.createQuery("from Recipe where user.id = :userId", Recipe.class)
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
