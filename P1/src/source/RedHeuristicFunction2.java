package src.source;

import aima.search.framework.HeuristicFunction;

//coste + datos en una proporcion
public class RedHeuristicFunction2 implements HeuristicFunction {

    @Override
    public double getHeuristicValue(Object state) {
        EstatSensor estat = (EstatSensor) state;
        return estat.getHeuristic2(); //retorna cost transmissió
    }
}
