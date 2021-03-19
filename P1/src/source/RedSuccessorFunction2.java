package src.source;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.List;

public class RedSuccessorFunction2  implements SuccessorFunction {

    //Funcion generadora de succesores para SimulatedAnealing
    //hay que comparar tiempos de ejecución
    public List getSuccessors(Object redState) {
        ArrayList<Successor> res = new ArrayList<>();
        EstatSensor state = (EstatSensor) redState;

        return null;
    }
}