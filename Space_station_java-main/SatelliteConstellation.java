package com.example.demo;

import java.util.ArrayList;
import java.util.List;

public class SatelliteConstellation extends Satellite{
    private String constellationName;
    private List<Satellite> satellites;

    SatelliteConstellation() {
        this.satellites = new ArrayList<>();
    }


    public void addSatellite(Satellite enter_sat){
        satellites.add(enter_sat);
    }

    public void executeAllMissions(){
        for (Satellite satellite : satellites) {
            satellite.performMission();
        }
    }

    public List<Satellite> getSatellites(){

        return satellites;
    }

    @Override
    protected void performMission() {

    }
}
