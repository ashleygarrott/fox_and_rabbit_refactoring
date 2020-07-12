package io.muic.ooc.fab;

import io.muic.ooc.fab.view.SimulatorView;

public class AnimalColorPainter {


    public void paintAnimalColor(SimulatorView view){
        AnimalType[] animaltypes = AnimalType.values();
        for (int i=0;i<animaltypes.length;i++){
            AnimalType animaltype = animaltypes[i];
            view.setColor(animaltype.getAnimalClass(), animaltype.getColor());
        }
    }
}
