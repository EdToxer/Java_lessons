package com.example.demo;

public abstract class Satellite {
    protected String name;
    SatelliteState state = new SatelliteState();
    EnergySystem energy = new EnergySystem();

    public void set_battery(double charge){
        energy.set_battery(charge);
    }

    public double get_battery(){
        return energy.getBatteryLevel();
    }

    public void set_status(boolean status){
        state.set_to(status);
    }

    public String getName(){return name;}

    public boolean activate(){
        if (energy.getBatteryLevel() > 0.2) {
            state.turn_up();
            return true;
        }
        return false;
    }

    public void consumeBattery(double tax){
        if (!energy.consume(tax)) {
            state.turn_down();
        }
    }

    protected abstract void performMission();
}

