import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.example.hibernate.model.Recipe;
import org.example.hibernate.model.User;
import org.example.hibernate.service.RecipeService;
import org.example.hibernate.service.UserService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class RecipeServiceTest {

    private RecipeService recipeService;
    private UserService userService;
    private User user;
    private EntityManagerFactory emf;

    @Before
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("my-persistence-unit");
        recipeService = new RecipeService();
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
    public void testCreateRecipe() {
        recipeService.createRecipe(user.getId(), "Pasta", "image_url");
        List<Recipe> recipes = recipeService.findRecipesByUserId(user.getId());
        assertEquals(1, recipes.size());
        assertEquals("Pasta", recipes.get(0).getTitle());
    }

    @Test
    public void testFindRecipesByUserId() {
        recipeService.createRecipe(user.getId(), "Pasta", "image_url");
        recipeService.createRecipe(user.getId(), "Salad", "image_url");
        List<Recipe> recipes = recipeService.findRecipesByUserId(user.getId());
        assertEquals(2, recipes.size());
    }
}
