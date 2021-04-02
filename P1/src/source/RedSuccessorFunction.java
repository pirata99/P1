package src.source;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import java.util.List;
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


        int numSensores = state.getNumSensors();

        for (int i = 0; i < numSensores; ++i) {
            /*
            int transmissionI = state.getTransmissionSC(i);
            int almacenamientoI = state.getInfoEmmagatzemadaSC(i);
            double costAnteriorI = state.getCost_All(i);
             */
            for (int j = i+1; j < numSensores; ++j) {
                double heur = state.getHeuristic((float) state.cost_transmissio, (float)state.info_perduda);
                EstatSensor sucessor = new EstatSensor(state);
                if (estadoValido && (sucessor.transmissionesSC.get(i) != sucessor.transmissionesSC.get(j)) && (sucessor.transmissionesSC.get(i) != j && i != sucessor.transmissionesSC.get(j))) {
                    sucessor.swap(i,j);
                    double heur2 = sucessor.getHeuristic((float) sucessor.cost_transmissio, (float)state.info_perduda);
                    if (heur2 < heur) {
                        res.add(new Successor("sensor " + i + " ha hecho swap con sensor "+ j, sucessor));
                        System.out.println("Lo acabo de guardar");
                        heur = heur2;
                        costosMillors.add(sucessor.cost_transmissio);
                        infoPerdudaMillor.add(sucessor.info_perduda);
                    }
                    else sucessor.swap(j,i);
                }
            }
        }

        return res;
    }
}
