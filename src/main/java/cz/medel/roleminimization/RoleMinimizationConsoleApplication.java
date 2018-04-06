/*
 * Copyright (c) 2018 Lukas Medelsky
 */
package cz.medel.roleminimization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import cz.medel.roleminimization.execution.MainService;


/**
 * Application configuration and initialization.
 *
 * @author <a href="mailto:medel11@seznam.cz">Lukas Medelsky</a>
 */
@SpringBootApplication
public class RoleMinimizationConsoleApplication implements CommandLineRunner {

    @Autowired
    private MainService mainService;

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
      mainService.execute();
    }
}
