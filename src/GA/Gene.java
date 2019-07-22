package GA;

import java.util.ArrayList;

public class Gene {//»ùÒò
    private int fitness=0;;
    private ArrayList<String> chromosome = new ArrayList<>();
    public int getfitness(){
        return fitness;
    }
    public void setfitness(int x){
        this.fitness = x;
    }
    public ArrayList getchromosome(){
        return chromosome;
    }
    public void setchromosome(ArrayList x){
        this.chromosome = x;
    }
}
