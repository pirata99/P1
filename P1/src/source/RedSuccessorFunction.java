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
        EstatSensor sucessor = new EstatSensor(state);
        //boolean estadoValido = state.estadoValido();

        int numSensores = state.getNumSensors();


        for (int i = 0; i < numSensores; ++i) {
            int conexionesI = state.getNConexionesCD(i);
            int transmissionI = state.getTransmissionSC(i);
            int almacenamientoI = state.getInfoEmmagatzemadaSC(i);
            double costAnteriorI = state.getCost_All(i);
            for (int j = i+1; j < numSensores; ++j) {
                double costActJ = state.getCost_All(j);
                int transmissionJ = state.getTransmissionSC(j);
                if (transmissionI >= numSensores) {
                    if (transmissionJ >= numSensores && transmissionI != transmissionJ) {

                    }
                }
            }
        }
        return res;
    }
}
