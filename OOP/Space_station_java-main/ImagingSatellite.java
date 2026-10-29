package com.example.demo;

public class ImagingSatellite extends Satellite{
    protected double resolution;
    protected int photosTaken;


    ImagingSatellite(String name, boolean status, double charge, double resolution, int photosTaken){
        this.state = new SatelliteState();

        this.name = name;
        this.state.set_to(status);
        this.energy.set_battery(charge);
        this.resolution = resolution;
        this.photosTaken = photosTaken;
    }

    public double getResolution() {
        return resolution;
    }

    public int getPhotosTaken(){
        return photosTaken;
    }

    @Override
    public void performMission(){
        if (this.state.isActive()) {
            takePhoto();
            consumeBattery(0.08);
            System.out.println("✅" + this.name + ": is taking the picture with resolution of " + getResolution() + "✅");
            System.out.println("✅" + this.name + ": the picture is taken");
        } else {
            System.out.println("❌" + this.name + " is not working");
        }
    }

    protected void takePhoto(){
        if (this.state.isActive()) photosTaken += 1;
    }

    @Override
    public String toString() {
        return "Type=" + getClass().getName() + "Name=" + name + ", Status=" + state.isActive() + "Battery level=" + get_battery() + "Gallery size=" + getPhotosTaken() + "Resolution" + getResolution() + "]";
    }
}
