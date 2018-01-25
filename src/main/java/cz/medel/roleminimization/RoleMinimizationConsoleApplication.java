/*
 * Copyright (c) 2018 Lukas Medelsky
 */
package cz.medel.roleminimization;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Application configuration and initialization.
 *
 * @author <a href="mailto:medel11@seznam.cz">Lukas Medelsky</a>
 */
@SpringBootApplication
public class RoleMinimizationConsoleApplication implements CommandLineRunner {

    /**
     * Main.
     *
     * @param args args
     */
    public static void main(String... args) {
        SpringApplication.run(RoleMinimizationConsoleApplication.class, args);
    }

    @Override
    public void run(String... args) {
        //do something
    }
}
