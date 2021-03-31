import src.source.*;
import java.util.*;


import IA.Red.*;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;


public class RedSensores {

    public static void main(String[] args) throws Exception {

        Scanner in = new Scanner(System.in);
        Random random = new Random();

        System.out.println("Que quieres ejecutar?");
        System.out.println("Hill Climbing: 1");
        System.out.println("Simulated Annealing: 2");
        System.out.println("Experimentos: 3");
        System.out.println("Terminar programa: 0");

        int op = in.nextInt();
        while (op != 0 && op != 1 && op != 2 && op != 3) {
            System.out.println("Introduce un valor correcto: 0, 1, 2 o 3");
            op = in.nextInt();
        }

        while (op != 0) {
            if (op == 3) {
                System.out.println("Qué experimento deseas realizar? ");
                op = in.nextInt();
                while (op < 1 || op > 9 || op == 8) {
                    System.out.println("Introduce un valor del 1 al 9 (exceptuando el 8)");
                    op = in.nextInt();
                }
                switch (op) {
                    case 1:
                        experimento1();
                        break;
                    case 2:
                        experimento2();
                        break;
                    case 3:
                        experimento3();
                        break;
                    case 4:
                        experimento4();
                        break;
                    case 5:
                        experimento5();
                        break;
                    case 6:
                        experimento6();
                        break;
                    case 7:
                        experimento7();
                        break;
                }
                System.out.println("Que deseas realizar?");
                System.out.println("Hill Climbing: 1");
                System.out.println("Simulated Annealing: 2");
                System.out.println("Experimentos: 3");
                System.out.println("Terminar programa: 0");

                op = in.nextInt();
                while (op != 0 && op != 1 && op != 2 && op != 3) {
                    System.out.println("Introduce un valor correcto: 0, 1, 2 o 3");
                    op = in.nextInt();
                }
            }
            else {
                System.out.println("Configura los atributos del estado");
                System.out.println("Introduce el valor para el atributo seed o -1 si deseas usar un valor aleatorio");
                int seed = in.nextInt();
                if (seed == -1) seed = random.nextInt(10000);
                System.out.println("Introduce el número de centros");
                int numCent = in.nextInt();
                System.out.println("Introduce el número de sensores");
                int numSens = in.nextInt();

                EstatSensor estat;
                System.out.println("A continuación, elije la solución inicial");
                System.out.println("1: Sensores se conectan a centros más cercanos, lo que no cabe es random");
                System.out.println("2: Todo random");
                int solIni = in.nextInt();
                while (solIni != 1 && solIni != 2) {
                    System.out.println("Introduce un valor correcto: 1 o 2");
                    solIni = in.nextInt();
                }
                if (solIni == 1) {
                    estat = new EstatSensor(numSens, seed, numCent, seed);
                    estat.EstatInicial_1();
                }
                else {
                        estat = new EstatSensor(numSens, seed, numCent, seed);
                        estat.EstatInicial_2();
                }

                if (op == 1)
                        BusquedaHillClimbing(estat);
                else
                        BusquedaSimulatedAnnealing(estat, 10000, 100, 25, 0.0001);

                    System.out.println("Que deseas realizar?");
                    System.out.println("Hill Climbing: 1");
                    System.out.println("Simulated Annealing: 2");
                    System.out.println("Experimentos: 3");
                    System.out.println("Terminar programa: 0");

                    op = in.nextInt();
                    while (op != 0 && op != 1 && op != 2 && op != 3) {
                        System.out.println("Introduce un valor correcto: 0, 1, 2 o 3");
                        op = in.nextInt();
                    }
                }
            }
        }

/*
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
        //estat.generaSolInicial2(); */

    public static double BusquedaHillClimbing(EstatSensor e) throws Exception {
        return 0;
    }

    public static double BusquedaSimulatedAnnealing(EstatSensor e, int steps, int stilter, int k, double lambda ) throws Exception {
        return 0;
    }

    private static void experimento1() throws Exception {
        Sensores s = new Sensores(100,1234);
        CentrosDatos cd = new CentrosDatos(2,4567);

        int numSensores = 50;
        int seed = 1234;
        int numCent = 1;
        int seedC = 4567;
//        System.out.println(args[1]);
//        int tipoIni = Integer.parseInt(args[1]);
        int tipoIni = 2;
        EstatSensor estat = new EstatSensor(numSensores, seed, numCent, seedC);
        boolean valido= estat.estadoValido();

        System.out.println(valido);
    }

    private static void experimento2() throws Exception {
        Sensores s = new Sensores(100,1234);
        CentrosDatos cd = new CentrosDatos(2,4567);

        int numSensores = 50;
        int seed = 1234;
        int numCent = 1;
        int seedC = 4567;
//        System.out.println(args[1]);
//        int tipoIni = Integer.parseInt(args[1]);
        int tipoIni = 2;
        EstatSensor estat = new EstatSensor(numSensores, seed, numCent, seedC);
        boolean valido= estat.estadoValido();

        System.out.println(valido);
    }

    private static void experimento3() throws Exception {
        Sensores s = new Sensores(100,1234);
        CentrosDatos cd = new CentrosDatos(2,4567);

        int numSensores = 50;
        int seed = 1234;
        int numCent = 1;
        int seedC = 4567;
//        System.out.println(args[1]);
//        int tipoIni = Integer.parseInt(args[1]);
        int tipoIni = 2;
        EstatSensor estat = new EstatSensor(numSensores, seed, numCent, seedC);
        boolean valido= estat.estadoValido();

        System.out.println(valido);
    }

    private static void experimento4() throws Exception {
        Sensores s = new Sensores(100,1234);
        CentrosDatos cd = new CentrosDatos(2,4567);

        int numSensores = 50;
        int seed = 1234;
        int numCent = 1;
        int seedC = 4567;
//        System.out.println(args[1]);
//        int tipoIni = Integer.parseInt(args[1]);
        int tipoIni = 2;
        EstatSensor estat = new EstatSensor(numSensores, seed, numCent, seedC);
        boolean valido= estat.estadoValido();

        System.out.println(valido);
    }

    private static void experimento5() throws Exception {
        Sensores s = new Sensores(100,1234);
        CentrosDatos cd = new CentrosDatos(2,4567);

        int numSensores = 50;
        int seed = 1234;
        int numCent = 1;
        int seedC = 4567;
//        System.out.println(args[1]);
//        int tipoIni = Integer.parseInt(args[1]);
        int tipoIni = 2;
        EstatSensor estat = new EstatSensor(numSensores, seed, numCent, seedC);
        boolean valido= estat.estadoValido();

        System.out.println(valido);
    }

    private static void experimento6() throws Exception {
        Sensores s = new Sensores(100,1234);
        CentrosDatos cd = new CentrosDatos(2,4567);

        int numSensores = 50;
        int seed = 1234;
        int numCent = 1;
        int seedC = 4567;
//        System.out.println(args[1]);
//        int tipoIni = Integer.parseInt(args[1]);
        int tipoIni = 2;
        EstatSensor estat = new EstatSensor(numSensores, seed, numCent, seedC);
        boolean valido= estat.estadoValido();

        System.out.println(valido);
    }

    private static void experimento7() throws Exception {
        Sensores s = new Sensores(100,1234);
        CentrosDatos cd = new CentrosDatos(2,4567);

        int numSensores = 50;
        int seed = 1234;
        int numCent = 1;
        int seedC = 4567;
//        System.out.println(args[1]);
//        int tipoIni = Integer.parseInt(args[1]);
        int tipoIni = 2;
        EstatSensor estat = new EstatSensor(numSensores, seed, numCent, seedC);
        boolean valido= estat.estadoValido();

        System.out.println(valido);
    }

}