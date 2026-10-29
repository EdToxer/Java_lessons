package com.company.sat;

public class Parrot extends Animal{
    protected boolean love_touch;

    Parrot(String name, boolean health, double cleaning, boolean food, boolean love){
        this.name = name;
        this.isHealthy = health;
        this.isClean = cleaning;
        this.isFeed = food;
        this.love_touch = love;
    }

    public boolean getLove() {
        love_touch = true;
        return love_touch;
    }

    @Override
    public void perform(){
        if (isFeed && isHealthy && (isClean > 0.4)) {
            sing();
        } else {
            System.out.println(name + " Чувствует себя не хорошо и не может выступать");
        }
    }

    protected void sing(){
        if (isClean >= 0.8 && love_touch) {
            System.out.println("✅Попугай по имени " + name + " громко поет потому что его погладили и помыли");
        } else {
            System.out.println("❌Попугай по имени " + name + " рычит тихо потому что его не гладили или плохо помыли");
        }
    }

    @Override
    public String toString() {
        return "Type=" + getClass().getName() + "Name=" + name + ", Health=" + isHealthy + ", Clean=" + isClean + ", Feed=" + isFeed + ", isLoved=" + love_touch;
    }
}
