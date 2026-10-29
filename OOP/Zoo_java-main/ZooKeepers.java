package com.company.sat;

public abstract class ZooKeepers{
    protected String name;

    public abstract void check_animal(Animal animal);

    public boolean Feed(Animal animal){
        if (!animal.isFeed) {
            return animal.Fed();
        } else return false;
    }

    protected abstract void pet(Animal animal);
}
