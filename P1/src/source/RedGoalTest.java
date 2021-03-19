package src.source;
import aima.search.framework.GoalTest;

public class RedGoalTest implements GoalTest {
    public boolean isGoalState(Object redState) {
        EstatSensor state = (EstatSensor) redState;
        return (state.isGoalState());
    }
}
