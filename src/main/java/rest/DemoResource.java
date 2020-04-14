package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.ChuckDTO;
import dtos.DadDTO;
import dtos.JSONPlaceholderDTO;
import dtos.SkyscannerDTO;
import entities.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import utils.EMF_Creator;
import utils.HttpUtils;

/**
 * @author lam@cphbusiness.dk
 */
@Path("info")
public class DemoResource {

    private static EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);

    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello anonymous\"}";
    }

    //Just to verify if the database is setup
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public String allUsers() {

        EntityManager em = EMF.createEntityManager();
        try {
            List<User> users = em.createQuery("select user from User user").getResultList();
            return "[" + users.size() + "]";
        } finally {
            em.close();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user")
    @RolesAllowed("user")
    public String getFromUser() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to User: " + thisuser + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("admin")
    @RolesAllowed("admin")
    public String getFromAdmin() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to (admin) User: " + thisuser + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"admin", "user"})
    public String getJokes() throws IOException, InterruptedException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        List<String> urls = new ArrayList<>();
//        urls.add("https://api.chucknorris.io/jokes/random");
//        urls.add("https://icanhazdadjoke.com");
//        urls.add("https://jsonplaceholder.typicode.com/todos/1");
//        urls.add("https://api.chucknorris.io/jokes/random");
//        urls.add("https://api.chucknorris.io/jokes/random");

        ChuckDTO chuckDTO;
        DadDTO dadDTO = null;
        JSONPlaceholderDTO jpDTO = null;
        SkyscannerDTO skyscannerDTO = null;

        ExecutorService workingJack = Executors.newFixedThreadPool(5);

         Runnable task  = new Runnable() {
                @Override
                public void run() {
                    try {
                    String chuck = HttpUtils.fetchData("https://api.chucknorris.io/jokes/random");
                    chuckDTO = gson.fromJson(chuck, ChuckDTO.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                }
            };
            workingJack.submit(task);   

//        Runnable task2 = () -> {
//            String dad = HttpUtils.fetchData("https://icanhazdadjoke.com");
//            dadDTO = gson.fromJson(dad, DadDTO.class);
//        };
//        workingJack.submit(task2);
//
//        Runnable task3 = () -> {
//            String ph = HttpUtils.fetchData("https://jsonplaceholder.typicode.com/todos/1");
//            jpDTO = gson.fromJson(ph, JSONPlaceholderDTO.class);
//        };
//        workingJack.submit(task3);

        workingJack.shutdown();
        workingJack.awaitTermination(15, TimeUnit.SECONDS);

//        String chuck = HttpUtils.fetchData("https://api.chucknorris.io/jokes/random");
//        ChuckDTO chuckDTO = gson.fromJson(chuck, ChuckDTO.class);
//        String dad = HttpUtils.fetchData("https://icanhazdadjoke.com");
//        DadDTO dadDTO = gson.fromJson(dad, DadDTO.class);
        //CombinedDTO combinedDTO = new CombinedDTO(dadDTO, chuckDTO);
        //This is what your endpoint should return       
        //String combinedJSON = gson.toJson(combinedDTO);
        //return combinedJSON;
    }

}
