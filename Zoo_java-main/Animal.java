package com.company.sat;

public abstract class Animal {
    protected String name;
    protected boolean isHealthy;
    protected double isClean;
    protected boolean isFeed;


    protected boolean Healed(){
        if (isFeed) {
            isHealthy = true;
            return true;
        }
        return false;
    }

    protected double Cleaned(double cleaning){
        if (isClean + cleaning <= 1.0) {
            isClean += cleaning;
            return isClean;
        } else return isClean=1.0;
    }

    protected boolean Fed(){
        if (!isFeed) {
            isFeed = true;
            return true;
        }
        return false;
    }

    protected abstract void perform();
}

