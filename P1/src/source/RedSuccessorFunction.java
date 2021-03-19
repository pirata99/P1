package src.source;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import java.util.List;
import java.util.ArrayList;

public class RedSuccessorFunction  implements SuccessorFunction {

    //Funcion generadora de succesores para HillClimbing
    //hay que comparar tiempos de ejecución
    public List getSuccessors (Object redState) {
        ArrayList<Successor> res = new ArrayList<>();
        EstatSensor state = (EstatSensor) redState;

        return null;
    }
}
