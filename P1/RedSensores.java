import src.source.*;


import IA.Red.*;

import java.util.LinkedList;
import java.util.Queue;


public class RedSensores {

    public static void main(String[] args){
        Sensores s = new Sensores(100,1234);
        CentrosDatos cd = new CentrosDatos(2,4567);

        int numSensores = 50;
        int seed = 1234;
        int numCent = 1;
        int seedC = 4567;
//        System.out.println(args[1]);
//        int tipoIni = Integer.parseInt(args[1]);
        int tipoIni = 2;
        EstatSensor estat = new EstatSensor(tipoIni, numSensores, seed, numCent, seedC);
        boolean valido= estat.estadoValido();

        System.out.println(valido);
        //Test test = new Test();
        //estat.generaSolInicial1();
        //estat.generaSolInicial2();
    }




}