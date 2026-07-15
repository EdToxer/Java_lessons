package com.company.sat;

public class Doctor extends ZooKeepers{
    private int years_exp;

    Doctor(String name, int years){
        this.name = name;
        this.years_exp = years;
    }

    @Override
    public void check_animal(Animal animal){
        if (!animal.isFeed) {
            animal.Fed();
            System.out.println(animal.name + " был покормлен");
        }
        if (getExp()) {
            if (!animal.isHealthy) {
                animal.Healed();
                System.out.println(animal.name + " был вылечен");
            }
        } else System.out.println("Доктор по имени " + name + " еще недостаточно опытен");

        if (animal instanceof Parrot) {
            ((Parrot) animal).getLove();
        }

    }

    public boolean getExp(){
        return (years_exp > 5);
    }

    public boolean Heal(Animal animal){
        if (!animal.isHealthy) {
            return animal.Healed();
        } else return false;
    }

    @Override
    public void pet(Animal animal){
        if (animal instanceof Lion) {
            System.out.println("Льву по имени " + animal.name + " нравится ласка");
        } else {
            System.out.println("Доктору разрешено гладить только льва");
        }
    };

}
