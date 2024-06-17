import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.example.hibernate.model.Ingredient;
import org.example.hibernate.model.User;
import org.example.hibernate.service.IngredientService;
import org.example.hibernate.service.UserService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class IngredientServiceTest {

    private IngredientService ingredientService;
    private UserService userService;
    private User user;
    private EntityManagerFactory emf;

    @Before
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("my-persistence-unit");
        ingredientService = new IngredientService();
        userService = new UserService();
        clearDatabase();
        userService.createUser("testuser", "password");
        user = userService.findAllUsers().get(0);
    }

    @After
    public void tearDown() {
        emf.close();
    }

    private void clearDatabase() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Query deleteRecipes = em.createQuery("DELETE FROM Recipe");
        deleteRecipes.executeUpdate();
        Query deleteIngredients = em.createQuery("DELETE FROM Ingredient");
        deleteIngredients.executeUpdate();
        Query deleteUsers = em.createQuery("DELETE FROM User");
        deleteUsers.executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void testCreateIngredient() {
        ingredientService.createIngredient(user.getId(), "Tomato");
        List<Ingredient> ingredients = ingredientService.findIngredientsByUserId(user.getId());
        assertEquals(1, ingredients.size());
        assertEquals("Tomato", ingredients.get(0).getIngredientName());
    }

    @Test
    public void testDeleteIngredient() {
        ingredientService.createIngredient(user.getId(), "Tomato");
        Ingredient ingredient = ingredientService.findIngredientsByUserId(user.getId()).get(0);
        ingredientService.deleteIngredient(ingredient.getId());
        List<Ingredient> ingredients = ingredientService.findIngredientsByUserId(user.getId());
        assertTrue(ingredients.isEmpty());
    }
}
