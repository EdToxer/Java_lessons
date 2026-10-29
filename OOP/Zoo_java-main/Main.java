package com.company.sat;

public class Main {
    public static void main(String[] args) {
        System.out.println("Attempting to access Satellites...");

        Zoo ny_zoo = new Zoo("Зоопарк Нью-Йорка");

        ny_zoo.addKeeper(new Doctor("Айболит", 15));
        ny_zoo.addKeeper(new Stylist("Марти", false));
        ny_zoo.addKeeper(new Stylist("Мэлон", true));

        ny_zoo.addAnimal(new Lion("Alex", false, 0.7, true, false));
        ny_zoo.addAnimal(new Parrot("Kesha", false, 0.3, false, true));
        ny_zoo.addAnimal(new Parrot("Masha", false, 0.1, true, false));


        for (int i = 0; i < ny_zoo.getKeepers().size(); i++){
            String name = ny_zoo.getKeepers().get(i).name;
            System.out.println("На работу принят в зоопарк  " +  ny_zoo.name + "✅: по имени " + name + " на должность " + ny_zoo.getKeepers().get(i).getClass());
        }

        for (int i = 0; i < ny_zoo.getZoo().size(); i++){
            String name = ny_zoo.getZoo().get(i).name;
            System.out.println("Животное добавлено в зоопарк  " +  ny_zoo.name + "✅: по имени " + name + " называется " + ny_zoo.getZoo().get(i).getClass());
        }

        for (Animal animal: ny_zoo.getZoo()){
            System.out.println(animal);
        }

        ny_zoo.performAllAnimals();

        ny_zoo.check_clean();
        ny_zoo.check_health();
        ny_zoo.check_feed();


        for (int i = 0; i < ny_zoo.getKeepers().size(); i++){
            ZooKeepers worker = ny_zoo.getKeepers().get(i);

            for (int j = 0; j < ny_zoo.getZoo().size(); j++){
                Animal anml = ny_zoo.getZoo().get(j);
                worker.check_animal(anml);
            }
        }

        for (Animal animal: ny_zoo.getZoo()){
            System.out.println(animal);
        }

        System.out.println("Successfully accessed the Helper class!");

        ny_zoo.performAllAnimals();
    }

}