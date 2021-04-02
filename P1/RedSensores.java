import IA.Red.CentrosDatos;
import IA.Red.Sensores;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;
import src.source.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;


public class RedSensores {

    static private int numIteracions = 1;
    static private ArrayList<Integer> cost = new ArrayList<>();

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

                op = in.nextInt(); //Aqui peta si no es un numero
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
                System.out.println(seed);
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

        Problem problema = new Problem(e, new RedSuccessorFunction(), new RedGoalTest(), new RedHeuristicFunction());
        Search search = new HillClimbingSearch();
        long iniTime = System.currentTimeMillis();
        SearchAgent agent = new SearchAgent(problema, search);
        long fiTime = System.currentTimeMillis();

        ArrayList<Double> cost = RedSuccessorFunction.costosMillors;
        ArrayList<Double> info = RedSuccessorFunction.infoPerdudaMillor;
        double costMin = 0;
        double infoPerd = 0;
        if (cost.size() > 0) {
            for (int i = cost.size() - 1; i < cost.size(); ++i) costMin = cost.get(i);
            for (int i = info.size()-1; i < info.size(); ++i) infoPerd = info.get(i);
        }
        if (costMin != 0 && infoPerd != 0) {
            System.out.println("Coste = " + costMin);
            System.out.println("InfoPerduda = " + infoPerd);
        }

        /*
        FileWriter fichero;
        fichero = new FileWriter("prova.txt", true);
        PrintWriter pw = new PrintWriter(fichero);

        if (numIteracions % 20 == 0)
            pw.println(String.format("%.2f", ((EstatSensor) search.getGoalState()).getHeuristic((float) cost, (float) info_perduda)) + " ");
        else
            pw.print(String.format("%.2f", ((EstatSensor) search.getGoalState()).getHeuristic((float) cost, (float) info_perduda)) + " ");
        fichero.close();
        ++numIteracions;

         */

        return (((EstatSensor) search.getGoalState()).getHeuristic((float) costMin, (float) infoPerd));
    }

    public static double BusquedaSimulatedAnnealing(EstatSensor e, int steps, int stilter, int k, double lambda ) throws Exception {
        Problem problema = new Problem(e, new RedSuccessorFunction(), new RedGoalTest(), new RedHeuristicFunction());
        Search search = new SimulatedAnnealingSearch(steps, stilter, k, lambda);
        SearchAgent agent = new SearchAgent(problema, search);

        ArrayList<Double> cost = RedSuccessorFunction.costosMillors;
        ArrayList<Double> info = RedSuccessorFunction.infoPerdudaMillor;
        double costMin = 0;
        double infoPerd = 0;
        if (cost.size() > 0) {
            for (int i = cost.size() - 1; i < cost.size(); ++i) costMin = cost.get(i);
            for (int i = info.size()-1; i < info.size(); ++i) infoPerd = info.get(i);
        }
        if (costMin != 0 && infoPerd != 0) {
            System.out.println("Coste = " + costMin);
            System.out.println("InfoPerduda = " + infoPerd);
        }



        return (((EstatSensor) search.getGoalState()).getHeuristic((float) costMin,(float) infoPerd));
    }

    private static void experimento1() throws Exception {
        ArrayList<Double> c1 = new ArrayList<>(); //coste op1
        ArrayList<Double> c2 = new ArrayList<>(); //coste op2
        ArrayList<Long> t1 = new ArrayList<>(); //tiempo op1
        ArrayList<Long> t2 = new ArrayList<>(); //tiempo op2

        Random random = new Random();

        for (int i = 0; i < 10; ++i) {
            int seed = random.nextInt(10000);
            int op = 1; //op swap o op move
            for (int j = 1; j < 3; ++j) {
                EstatSensor estat = new EstatSensor(100, seed, 4, seed);
                estat.EstatInicial_2();

                long ini = System.currentTimeMillis();
                double coste = BusquedaHillClimbing(estat);
                long fin = System.currentTimeMillis();

                if (j == 1) {
                    c1.add(coste);
                    t1.add(fin-ini);
                }
                else if (j == 2) {
                    c2.add(coste);
                    t2.add(fin-ini);
                }
            }
        }


        for (int i = 0; i < c1.size(); ++i) {
            c1.set(i, (double)Math.round(c1.get(i) * 100d) / 100d);
            c2.set(i, (double)Math.round(c1.get(i) * 100d) / 100d);
        }

        System.out.println("c1: " + c1);
        System.out.println("c2: " + c2);

        System.out.println("t1: " + t1);
        System.out.println("t2: " + t2);

    }

    private static void experimento2() throws Exception {
        Random random = new Random();
        ArrayList<Double> c1 = new ArrayList<>(), c2  = new ArrayList<>();
        ArrayList<Long> t1 = new ArrayList<>(), t2  = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            int seed = random.nextInt(10000);
            int op = 1; //move o swap
            for (int j = 1; j < 3; ++j) {
                EstatSensor e = new EstatSensor(100, seed, 4, seed);
                if (j == 1)
                    e.EstatInicial_1();
                else
                    e.EstatInicial_2();
                long ini = System.currentTimeMillis();
                double coste = BusquedaHillClimbing(e);
                long fin = System.currentTimeMillis();
                if (j == 1) {
                    c1.add(coste);
                    t1.add(fin - ini);
                } else {
                    c2.add(coste);
                    t2.add(fin - ini);
                }
            }
        }

        for (int i = 0; i < c1.size(); ++i) {
            c1.set(i, (double)Math.round(c1.get(i) * 100d) / 100d);
            c2.set(i, (double)Math.round(c1.get(i) * 100d) / 100d);
        }

        System.out.println("c1: " + c1);
        System.out.println("c2: " + c2);
        System.out.println("t1: " + t1);
        System.out.println("t2: " + t2);
    }

    private static void experimento3() throws Exception {
        experimento31();
        //experimento32();
    }

    private static void experimento31() throws Exception {
        Random random = new Random();
        ArrayList<Double> costes = new ArrayList<>();
        for (int i = 0; i < 12; ++i)
            costes.add(0.0);

        ArrayList<Integer> k = new ArrayList<>(Arrays.asList(1, 5, 25, 125));
        ArrayList<Double> lambda = new ArrayList<>(Arrays.asList(1.0, 0.01, 0.0001));
        int cnt = 0;

        for (int i = 0; i < 10; ++i) {
            int seed = random.nextInt(10000);
            for (int j = 0; j < k.size(); ++j) {
                for (int l = 0; l < lambda.size(); ++l) {
                    EstatSensor e = new EstatSensor(100, seed, 4, seed);
                    e.EstatInicial_2();
                    double c = BusquedaSimulatedAnnealing(e, 1, 1, k.get(j), lambda.get(l));
                    costes.set(cnt, costes.get(cnt) + c);
                    ++cnt;
                }
            }
            cnt = 0;
        }
        for (int i = 0; i < costes.size(); ++i) {
            costes.set(i, costes.get(i) / 10);
            costes.set(i, (double) Math.round(costes.get(i) * 100d) / 100d);
        }

        generarGraficaBarras(costes, k, lambda);
    }

    private static void experimento32() throws Exception {

    }

    private static void generarGraficaBarras(ArrayList<Double> compara, ArrayList<Integer> k, ArrayList<Double> l) {
        Grafica chart = new Grafica(compara, k, l);
        chart.setAlwaysOnTop(true);
        chart.pack();
        chart.setSize(600, 400);
        chart.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        chart.setVisible(true);
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