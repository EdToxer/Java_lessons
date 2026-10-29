package com.company.sat;

public class Stylist extends ZooKeepers{
    private boolean licence;

    Stylist(String name, boolean certificate){
        this.name = name;
        this.licence = certificate;
    }

    @Override
    public void check_animal(Animal animal){
        if (!animal.isFeed) {
            animal.Fed();
            System.out.println(animal.name + " был покормлен");
        }
        if (animal.isClean < 1.0) {
            if (getLicence()) {
                animal.Cleaned(0.5);
                System.out.println(animal.name + " был приведён в порядок");
            } else System.out.println("Стилист по имени " + name + " не имеет сертификата");
        }
        if (animal instanceof Lion) {
            ((Lion) animal).getHair();
        }
    }

    public boolean getLicence(){
        return licence;
    }

    public double Clean(Animal animal){
        if (animal.isClean < 1.0) {
            return animal.Cleaned(0.5);
        } else return animal.isClean;
    }

    @Override
    public void pet(Animal animal){
        if (animal instanceof Parrot) {
            if (!((Parrot) animal).getLove()) {
                ((Parrot) animal).love_touch = true;
                System.out.println("Попугаю по имени " + animal.name + " нравится ласка");
            }
        } else {
            System.out.println("Стилисту разрешено гладить только попугаев");
        }
    };
}
