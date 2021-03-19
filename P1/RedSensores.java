import src.source.*;


import IA.Red.*;

import java.util.LinkedList;
import java.util.Queue;


public class RedSensores {

    public static void main(String[] args){
        Sensores s = new Sensores(100,1234);
        CentrosDatos cd = new CentrosDatos(2,4567);

        int numSensores = 100;
        int seed = 1234;
        int numCent = 2;
        int seedC = 4567;

        EstatSensor estat = new EstatSensor(numSensores, seed, numCent, seedC);

        System.out.println("Imprimer algo");
        //Test test = new Test();
        //estat.generaSolInicial1();
        //estat.generaSolInicial2();
    }


}