package src.source;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;

public class RedSuccessorFunction  implements SuccessorFunction {

    public static ArrayList<Double> costosMillors;
    public static ArrayList<Double> infoPerdudaMillor;
    //Funcion generadora de succesores para HillClimbing
    //hay que comparar tiempos de ejecución
    public ArrayList getSuccessors (Object redState) {
        costosMillors = new ArrayList<>();
        infoPerdudaMillor = new ArrayList<>();
        ArrayList<Successor> res = new ArrayList<>();
        EstatSensor state = (EstatSensor) redState;
        boolean estadoValido = state.estadoValido();

        int count = 0;
        int numSensores = state.getNumSensors();
        int numCent = state.getNumCentros();


//            double heur = sucessor.getHeuristic((float) sucessor.cost_transmissio, (float) sucessor.info_perduda);

        for (int i = 0; i < numSensores; ++i) {

            for (int j = i + 1; j < numSensores+numCent; ++j) {
                EstatSensor sucessor = new EstatSensor(state);

                if ((sucessor.transmissionesSC.get(i) != sucessor.transmissionesSC.get(j)) && (sucessor.transmissionesSC.get(i) != j &&
                        i != sucessor.transmissionesSC.get(j)) && !sucessor.evitaCiclos(i, j)) {
                    if (j >= numSensores && state.getNConexionesCD(j) < 25) {
                        sucessor.moverSensor(i, j);
                        //sucessor.estadoSens();

//                    sucessor.estadoSens();
                        //sucessor.calculaHeuristic();
//                    System.exit(0);
//                       sucessor.moverSensor(i, j);
//                        double heur2 = sucessor.getHeuristic((float) sucessor.cost_transmissio, (float) sucessor.info_perduda);
                        res.add(new Successor("sensor " + i + " ha hecho swap con sensor " + j, sucessor));
                        ++count;
//                    System.out.println("Lo acabo de guardar");
//                        costosMillors.add(sucessor.cost_transmissio);
//                        infoPerdudaMillor.add(sucessor.info_perduda);
                    }
                }
            }
/*
            for (int k = i + 1; k < numSensores; ++k) {
                EstatSensor sucessor = new EstatSensor(state);

                if ((sucessor.transmissionesSC.get(i) != sucessor.transmissionesSC.get(k)) && (sucessor.transmissionesSC.get(i) != k &&
                        i != sucessor.transmissionesSC.get(k)) && !sucessor.evitaCiclos(i, k)) {

                    sucessor.swapFreestyle(i, k);
                    //state.estadoSens();
                    //System.out.println("swap sensor " + i + " e " + k);
                    //sucessor.estadoSens();

                    sucessor.calculaHeuristic();
//                        double heur2 = sucessor.getHeuristic((float) sucessor.cost_transmissio, (float) sucessor.info_perduda);
                    res.add(new Successor("sensor " + i + " ha hecho swap con sensor " + k, sucessor));
//                    System.out.println("Lo acabo de guardar");
//                        costosMillors.add(sucessor.cost_transmissio);
//                        infoPerdudaMillor.add(sucessor.info_perduda);
                }

            }
*/

        }
        System.out.println("NUMERO DE SUCESORES: " + count);
        //System.exit(0);
     //System.out.println("SALGO");
        return res;
    }
}
