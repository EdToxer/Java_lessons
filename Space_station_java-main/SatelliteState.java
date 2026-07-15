package com.example.demo;

public class SatelliteState {
    protected boolean isActive;

    protected boolean isActive(){
        return isActive;
    }

    protected void set_to(boolean status){
        isActive = status;
    }

    protected void turn_up(){
        isActive = true;
    }

    protected void turn_down(){
        isActive = false;
    }
}
