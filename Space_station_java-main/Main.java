package com.example.demo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);

        ConstellationRepository repository = context.getBean(ConstellationRepository.class);
        SpaceOperationCenterService socService = context.getBean(SpaceOperationCenterService.class);

        System.out.println("Attempting to access Satellites...");

        String groupName = "Starlink-Alpha";
        socService.createAndSaveConstellation(groupName);

        SatelliteConstellation Sat_group = new SatelliteConstellation();

        socService.addSatelliteToConstellation(groupName, new CommunicationSatellite("Svyaz-1", false, 0.5, 500.0));
        socService.addSatelliteToConstellation(groupName, new CommunicationSatellite("Svyaz-2", false, 1.0, 1000.0));

        socService.addSatelliteToConstellation(groupName, new ImagingSatellite("DZZ-1", false, 0.7, 1.0, 0));
        socService.addSatelliteToConstellation(groupName, new ImagingSatellite("DZZ-2", false, 0.3, 2.0, 0));
        socService.addSatelliteToConstellation(groupName, new ImagingSatellite("DZZ-3", false, 0.1, 1.5, 0));


        System.out.println("Successfully accessed the Helper class!");
        System.out.println("\n[Initial Status Check]");
        socService.showConstellationStatus(groupName);

        System.out.println("\n[Activating Constellation...]");
        socService.activateAllSatellites(groupName);

        System.out.println("\n[Executing Missions...]");
        socService.executeConstellationMission(groupName);

        System.out.println("\n[Final Status Check]");
        socService.showConstellationStatus(groupName);


        System.out.println("\n--- Repository Dump ---");
        repository.findAll().forEach((name, constellation) -> {
            System.out.println("Constellation in Repo: " + name + " with " +
                    constellation.getSatellites().size() + " satellites.");
        });
    }

}