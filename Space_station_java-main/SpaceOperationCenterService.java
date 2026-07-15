package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class SpaceOperationCenterService {
    @Autowired
    private ConstellationRepository repository;

    public SpaceOperationCenterService() {
    }

    public void createAndSaveConstellation(String name) {
        repository.save(name, new SatelliteConstellation());
        System.out.println("Group '" + name + "' was created.");
    }

    public void addSatelliteToConstellation(String constellationName, Satellite satellite) {
        SatelliteConstellation constellation = repository.findByName(constellationName);
        if (constellation != null) {
            constellation.addSatellite(satellite);
        } else {
            System.out.println("Error: Group " + constellationName + " was not found.");
        }
    }

    public void activateAllSatellites(String constellationName) {
        SatelliteConstellation constellation = repository.findByName(constellationName);
        if (constellation != null) {
            constellation.getSatellites().forEach(Satellite::activate);
        }
    }

    public void executeConstellationMission(String constellationName) {
        SatelliteConstellation constellation = repository.findByName(constellationName);
        if (constellation != null) {
            constellation.getSatellites().forEach(Satellite::performMission);
        }
    }

    public void showConstellationStatus(String constellationName) {
        SatelliteConstellation constellation = repository.findByName(constellationName);
        if (constellation != null) {
            System.out.println("Group status: " + constellationName);
            constellation.getSatellites().forEach(s ->
                    System.out.println("Satelite: " + s.getName() + " | Status: " + s.state.isActive()));
        }
    }

    public SatelliteConstellation getConstellation(String name) {
        return repository.findByName(name);
    }
}
