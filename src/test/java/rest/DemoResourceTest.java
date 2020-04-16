package rest;

import static com.sun.javafx.fxml.expression.Expression.equalTo;
import dtos.CombinedDTO;
import entities.Role;
import entities.User;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import io.restassured.parsing.Parser;
import java.net.URI;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import utils.EMF_Creator;

public class DemoResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private EntityManager em;
    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    public DemoResourceTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.TEST, EMF_Creator.Strategy.CREATE);

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void tearDownClass() {
        //System.in.read();
        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            //Delete existing users and roles to get a "fresh" database
            em.createQuery("delete from User").executeUpdate();
            em.createQuery("delete from Role").executeUpdate();

            Role userRole = new Role("user");
            Role adminRole = new Role("admin");
            User user = new User("user", "test");
            user.addRole(userRole);
            User admin = new User("admin", "test");
            admin.addRole(adminRole);
            User both = new User("user_admin", "test");
            both.addRole(userRole);
            both.addRole(adminRole);
            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user);
            em.persist(admin);
            em.persist(both);
            System.out.println("Saved test data to database");
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        logOut();
    }

    //This is how we hold on to the token after login, similar to that a client must store the token somewhere
    private static String securityToken;

    //Utility method to login and set the returned securityToken
    private static void login(String role, String password) {
        String json = String.format("{username: \"%s\", password: \"%s\"}", role, password);
        securityToken = given()
                .contentType("application/json")
                .body(json)
                //.when().post("/api/login")
                .when().post("/login")
                .then()
                .extract().path("token");
        System.out.println("TOKEN ---> " + securityToken);
    }

    private void logOut() {
        securityToken = null;
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGetThingsFromMultipleAPIsUserLogin() throws Exception {
        login("user", "test");
        CombinedDTO result =
        with()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .get("/info/test").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("weatherTimezone", equalTo("Europe/Copenhagen"))
                .body("dadJokeURL", equalTo("https://icanhazdadjoke.com"))
                .body("chuckJokeURL", equalTo("https://api.chucknorris.io/jokes/random"))
                .extract()
                .as(CombinedDTO.class);
        //kunne teste på en masse andre ting her
        assertTrue((result.getScanner().getPlaces().stream().anyMatch(item -> item.getPlaceId().equals("STOC-sky"))));

    }
    
    @Test
    public void testGetThingsFromMultipleAPIsAdminLogin() throws Exception {
        login("admin", "test");
        CombinedDTO result =
        with()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .get("/info/test").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("weatherTimezone", equalTo("Europe/Copenhagen"))
                .body("dadJokeURL", equalTo("https://icanhazdadjoke.com"))
                .body("chuckJokeURL", equalTo("https://api.chucknorris.io/jokes/random"))
                .extract()
                .as(CombinedDTO.class);
        //kunne teste på en masse andre ting her
        assertTrue((result.getScanner().getPlaces().stream().anyMatch(item -> item.getPlaceId().equals("STOC-sky"))));

    }
    
    @Test
    public void testGetThingsFromMultipleAPIsNoLogin() throws Exception {
        given()
                .contentType("application/json")
                .get("/info/test").then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN_403.getStatusCode())
                .body("code", equalTo(403))
                .body("message", equalTo("Not authenticated - do login"));
    }

}
