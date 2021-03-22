package src.source;

import aima.search.framework.HeuristicFunction;

//coste
public class RedHeuristicFunction1 implements HeuristicFunction {
    @Override
    public double getHeuristicValue(Object state) {
        EstatSensor estat = (EstatSensor) state;
        return estat.getHeuristic1(); //retorna cost transmissió
    }
}
