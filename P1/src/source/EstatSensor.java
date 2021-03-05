package source;

import IA.Red.CentrosDatos;
import IA.Red.Sensores;

import java.util.*;

public class EstatSensor {

    //Atributs
    static Sensores sens; //Conjunto de sensores
    static CentrosDatos cd; //Conjunto de centros de datos

    private double information_sensor; //mb del sensor

    private double capacity_CD; //capacidad del centro de datos
    private double capacity_S; //capacidad del sensor

    //capacidad maxima 25 conexiones a un centro de datos y 3 a un sensor
    private int nconexiones; //num conexiones centro de Datos


    private double cost_transmissio; //HAY QUE COGER EL MÍNIMO


    //getters

    public boolean isGoalState() {
        return false;
    }

    public double getInformationSensor () {
        return information_sensor;
    }

    public double getCapacity_CD (){
        return capacity_CD;
    }

    public double getCapacity_S () {
        return capacity_S;
    }







}
