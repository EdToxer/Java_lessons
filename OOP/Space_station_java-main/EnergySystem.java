package com.example.demo;

public class EnergySystem {
    private double batteryLevel;

    protected double getBatteryLevel(){
        return batteryLevel;
    }

    protected void set_battery(double power){
        if (1.0 >= power && power > 0){
            batteryLevel = power;
        }
    }

    protected boolean consume(double spend_power){
        if (getBatteryLevel() > 0.2) {
            if (getBatteryLevel() < spend_power) {
                set_battery(0.2);
                return false;
            } else {
                batteryLevel -= spend_power;
                return true;
            }
        } else {
            return false;
        }
    }
}
