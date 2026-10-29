package com.example.demo;

public class CommunicationSatellite extends Satellite{
    protected double bandwidth;


    CommunicationSatellite(String name, boolean status, double charge, double bandwidth){
        this.name = name;
        this.state.set_to(status);
        this.energy.set_battery(charge);
        this.bandwidth = bandwidth;
    }

    public double getBandwidth() {
        return bandwidth;
    }

    @Override
    public void performMission(){
        if (this.state.isActive()) {
            sendData();
            consumeBattery(0.05);
        }
    }

    protected void sendData(){
        if (this.state.isActive()) {
            System.out.println("✅" + this.name + ": is sending the data with speed of " + getBandwidth());
            System.out.println("✅" + this.name + ": the total size of data is " + getBandwidth() + " sent the data");
        } else {
            System.out.println("❌" + this.name + ": is not working");
        }
    }

    @Override
    public String toString() {
        return "Type=" + getClass().getName() + "Name=" + name + ", Status=" + state.isActive() + "Battery level=" + get_battery() + "Bandwidth=" + getBandwidth() + "]";
    }
}
