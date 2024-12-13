package fsegs.nosql.minisocialmediaapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
//@EnableMongoRepositories
public class MiniSocialMediaAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(MiniSocialMediaAppApplication.class, args);
    }

}
