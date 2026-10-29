package com.company.sat;

public class Lion extends Animal{
    protected boolean nice_hair;

    Lion(String name, boolean health, double cleaning, boolean food, boolean brush){
        this.name = name;
        this.isHealthy = health;
        this.isClean = cleaning;
        this.isFeed = food;
        this.nice_hair = brush;
    }

    public boolean getHair() {
        nice_hair = true;
        return nice_hair;
    }

    @Override
    public void perform(){
        if (isFeed && isHealthy && (isClean > 0.4)) {
            roar();
        } else {
            System.out.println(name + " Чувствует себя не хорошо и не может выступать");
        }
    }

    protected void roar(){
        if (isClean > 0.8 && nice_hair) {
            System.out.println("✅Лев по имени " + name + " громко речит потому что его причесали и помыли");
        } else {
            System.out.println("❌Лев по имени " + name + " рычит тихо потому что его не помыли или не причесали");
        }
    }

    @Override
    public String toString() {
        return "Type=" + getClass().getName() + "Name=" + name + ", Health=" + isHealthy + ", Clean=" + isClean + ", Feed=" + isFeed + ", niceHair=" + nice_hair + "]";
    }}
