package ru.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;


/**
 * Main application class for starting the Spring Boot application.
 */
@SpringBootApplication
public class SpringApp {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * The main method which serves as the entry point of the Spring Boot application.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {

        SpringApplication.run(SpringApp.class, args);

    }

    /**
     * Initializes the database table for storing books.
     * Creates the table if it does not exist and truncates the table.
     */
    public void initializeTable(){
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS books (" +
                "id INTEGER PRIMARY KEY, " +
                "title VARCHAR(255), " +
                "author VARCHAR(255), " +
                "genre VARCHAR(255), " +
                "pages INTEGER, " +
                "price INTEGER)");
        jdbcTemplate.execute("TRUNCATE books");
    }

    /**
     * Runs the initialization of the table after the application context is loaded.
     *
     * @param args command-line arguments (not used)
     * @throws Exception if an error occurs during table initialization
     */
    public void run(String... args) throws Exception {
        initializeTable();
    }

}