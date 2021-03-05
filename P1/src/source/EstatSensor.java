package source;

import IA.Red.CentrosDatos;
import IA.Red.Sensor;
import IA.Red.Sensores;

import java.util.*;

public class EstatSensor {

    //Atributs

    static Sensores sens; //Conjunto de sensores
    static CentrosDatos cd; //Conjunto de centros de datos

    static ArrayList<ArrayList<Sensor>> conexiones; //conexiones actuales de un sensor
    static ArrayList<ArrayList<Sensor>> SensorsAccessibles; //SensoresAccesibles de los Sensores

    ArrayList<Double> info_Capturada_Sensor; //agafar size sensor i per cada sensor veure infoCapturada

    ArrayList<Double> RecibeMaxCentroDatos; //cada centro de datos puede recibir hasta 150 Mb/s

    ArrayList<Integer> ConexionesCentroDatos; //un centro de datos puede recibir hasta 25 conexiones

    ArrayList<Double> RecibeMaxSensor; //cada sensor puede recibir el doble de su capacidad (si cap = 2,
    //puede recibir 4

    double capacitat_Centre; //capacitat centre es de 150 Mb/s

    ArrayList<Double> info_Capturada_Centre; //agafar size sensor i per cada Centre veure infoCapturada


    private double cost_transmissio; //MINIMIZARLA

    private double quantitat_informacio; //MAXIMIZARLA

    //getters




    public boolean isGoalState() {
        return false;
    }










}
