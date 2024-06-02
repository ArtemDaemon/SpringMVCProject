package ru.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;


@SpringBootApplication
public class SpringApp {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {

        SpringApplication.run(SpringApp.class, args);

    }
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
    public void run(String... args) throws Exception {
        initializeTable();
    }

}