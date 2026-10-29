package com.company.sat;


import java.util.ArrayList;
import java.util.List;

public class Zoo extends Animal{
    private List<Animal> animals;
    private List<ZooKeepers> keepers;

    Zoo(String name){
        this.name = name;
        this.animals = new ArrayList<>();
        this.keepers = new ArrayList<>();
    }

    public void addAnimal(Animal enter_sat){
        animals.add(enter_sat);
    }

    public void addKeeper(ZooKeepers employee){
        keepers.add(employee);
    }
    
    public void check_health(){
        int anmls = 0;
        for (int i = 0; i < animals.size(); i++){
            if (animals.get(i).isHealthy) anmls += 1;
        }
        System.out.println("Здоровых животных - " + anmls);
        System.out.println("Больных животных - " + (animals.size() - anmls));
    }

    public void check_feed(){
        int anmls = 0;
        for (int i = 0; i < animals.size(); i++){
            if (animals.get(i).isFeed) anmls += 1;
        }
        System.out.println("Сытых животных - " + anmls);
        System.out.println("Голодных животных - " + (animals.size() - anmls));
    }

    public void check_clean(){
        int anmls = 0;
        for (int i = 0; i < animals.size(); i++){
            if (animals.get(i).isClean > 0.5) anmls += 1;
        }
        System.out.println("Чистых животных - " + anmls);
        System.out.println("Грязных животных - " + (animals.size() - anmls));
    }

    public void performAllAnimals(){
        for (Animal animal : animals) {
            animal.perform();
        }
    }

    public List<Animal> getZoo(){
        return animals;
    }
    
    public List<ZooKeepers> getKeepers(){
        return keepers;
    }

    @Override
    protected void perform() {

    }
}
