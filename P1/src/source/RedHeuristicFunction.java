package src.source;

import aima.search.framework.HeuristicFunction;

//coste
public class RedHeuristicFunction implements HeuristicFunction {
    @Override
    public double getHeuristicValue(Object state) {
        EstatSensor estat = (EstatSensor) state;
        float p_cost = 0.5f;
        float p_loss = 0.5f;
        return estat.getHeuristic(p_cost, p_loss); //retorna cost transmissió
    }
}
