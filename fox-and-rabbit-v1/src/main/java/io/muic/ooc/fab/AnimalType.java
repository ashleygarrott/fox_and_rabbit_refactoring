package io.muic.ooc.fab;

import java.awt.*;

public enum AnimalType {
    RABBIT(0.08, 5, Rabbit.class, Color.ORANGE),
    FOX(0.02, 15, Fox.class, Color.BLUE),
    TIGER(0.02, 20, Tiger.class, Color.RED),
    HUNTER(0.008, 20, Hunter.class, Color.GREEN);



    private double creationProbability;

    private Class animalClass;

    public int getFoodValue() {
        return foodValue;
    }

    private int foodValue;



    public double getCreationProbability() {
        return creationProbability;
    }

    public Class getAnimalClass() {
        return animalClass;
    }

    public Color getColor() {
        return color;
    }

    private Color color;

    AnimalType(double creationProbability, int foodValue, Class animalClass, Color color){
        this.creationProbability = creationProbability;
        this.animalClass = animalClass;
        this.color = color;
        this.foodValue = foodValue;
    }


}
