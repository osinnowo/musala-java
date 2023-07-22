package com.osinnowo.service.drone.app;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppDelegate implements CommandLineRunner {

    private final AppSeeder seeder;

    @Override
    public void run(String... args) throws Exception {
        seeder.seed();
    }
}
