import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.example.hibernate.model.User;
import org.example.hibernate.service.UserService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class UserServiceTest {

    private UserService userService;
    private EntityManagerFactory emf;

    @Before
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("my-persistence-unit");
        userService = new UserService();
        clearDatabase();
    }

    @After
    public void tearDown() {
        emf.close();
    }

    private void clearDatabase() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        // Najpierw usuń rekordy z tabeli recipes
        Query deleteRecipes = em.createQuery("DELETE FROM Recipe");
        deleteRecipes.executeUpdate();
        // Następnie usuń rekordy z tabeli ingredients
        Query deleteIngredients = em.createQuery("DELETE FROM Ingredient");
        deleteIngredients.executeUpdate();
        // Na końcu usuń rekordy z tabeli users
        Query deleteUsers = em.createQuery("DELETE FROM User");
        deleteUsers.executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void testCreateUser() {
        userService.createUser("testuser", "password");
        User user = userService.findAllUsers().get(0);
        assertNotNull(user);
        assertEquals("testuser", user.getUsername());
    }

    @Test
    public void testFindAllUsers() {
        userService.createUser("testuser1", "password1");
        userService.createUser("testuser2", "password2");
        List<User> users = userService.findAllUsers();
        assertEquals(2, users.size());
    }
}
