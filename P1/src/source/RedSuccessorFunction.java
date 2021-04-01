package src.source;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import java.util.List;
import java.util.ArrayList;

public class RedSuccessorFunction  implements SuccessorFunction {

    //Funcion generadora de succesores para HillClimbing
    //hay que comparar tiempos de ejecución
    public ArrayList getSuccessors (Object redState) {
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
                if (estadoValido && (sucessor.transmissionesSC.get(i) != sucessor.transmissionesSC.get(j))) {
                    state.swap(i,j);
                    double heur2 = sucessor.getHeuristic((float) sucessor.cost_transmissio, (float)state.info_perduda);
                    if (heur2 < heur) res.add(new Successor("sensor " + i + " ha hecho swap con sensor "+ j, sucessor));
                    else state.swap(j,i);
                }
            }
        }
        return res;
    }
}
