package src.source;

import IA.Red.Centro;
import IA.Red.CentrosDatos;
import IA.Red.Sensor;
import IA.Red.Sensores;

import java.lang.reflect.Array;
import java.util.*;

public class EstatSensor {

    /*
    ATRIBUTOS
     */

    static Sensores sens; //Conjunto de sensores
    static CentrosDatos cd; //Conjunto de centros de datos

    static ArrayList<ArrayList<Sensor>> conexionesS; //conexiones actuales de un sensor
    static ArrayList<ArrayList<Sensor>> SensorsAccessibles; //SensoresAccesibles de los Sensores (todos los sensores excepto los que esten llenos)

    static ArrayList<ArrayList<Centro>> conexionesCD;
    static ArrayList<ArrayList<Centro>> CentrosAccessibles; //Centros accessibles de los Centros (todos los centros excepto los que ya tengan capacitat 150).

    ArrayList<Integer> info_Capturada_Sensor; //agafar size sensor i per cada sensor veure infoCapturada

    /* INFO

     //cada centro de datos puede recibir hasta 150 Mb/s

    ArrayList<Integer> ConexionesCentroDatos; //un centro de datos puede recibir hasta 25 conexiones

    ArrayList<Double> RecibeMaxSensor; //cada sensor puede recibir el doble de su capacidad (si cap = 2,
    //puede recibir 4
    */

    static final double capacitat_Centre = 150; //capacitat centre es de 150 Mb/s

    ArrayList<Integer> info_Capturada_Centre; //agafar size sensor i per cada Centre veure infoCapturada


    private double cost_transmissio; //MINIMIZARLA

    private double quantitat_informacio; //MAXIMIZARLA

    /*
     CONSTRUCTORA ESTADO INICIAL
     */
    public EstatSensor (int numSensores, int seedSens, int numCent, int seedCent) {

        sens = new Sensores(numSensores, seedSens);
        cd = new CentrosDatos(numCent, seedCent);

        conexionesS = new ArrayList<ArrayList<Sensor>>();
        for (int i = 0; i < numSensores; ++i) {
            ArrayList<Sensor> conexS = new ArrayList<Sensor>();
            conexionesS.add(conexS);
        }

        conexionesCD = new ArrayList<ArrayList<Centro>>();
        for (int j = 0; j < numCent; ++j) {
            ArrayList<Centro> conexC = new ArrayList<>();
            conexionesCD.add(conexC);
        }

        //ArrayList<Integer> info_Capturada_Sensor; //agafar size sensor i per cada sensor veure infoCapturada

        info_Capturada_Sensor = new ArrayList<Integer>();

        for (int i = 0; i < numSensores; ++i) {
            Integer infoS = 0;
            info_Capturada_Sensor.add(infoS);
        }

        for (int j = 0; j < numCent; ++j) {
            Integer infoC = 0;
            info_Capturada_Centre.add(infoC);
        }
    }

    public EstatSensor(EstatSensor state) {

        sens = state.sens;
        cd = state.cd;

        conexionesS = new ArrayList<ArrayList<Sensor>>(state.conexionesS);
        conexionesCD = new ArrayList<ArrayList<Centro>>(state.conexionesCD);

        info_Capturada_Sensor = new ArrayList<Integer>(state.info_Capturada_Sensor);
        info_Capturada_Centre = new ArrayList<Integer>(state.info_Capturada_Centre);

    }


    /*
    OPERADORES
     */

    public void envia_info() {} //este operador envia info a un sensor o a un centro
    //hemos de tener en cuenta cual puede ser el más optimo


    /*
    GOAL TEST
     */
    public boolean isGoalState() {
        return false;
    }


    /*
    GETTERS
     */

    public void getNConexionesS() { //numero conexiones sensor
        for (int i = 0; i < conexionesS.size(); ++i) {
            System.out.println("Sensor " + i + ": ");
            for (int j = 0; j < conexionesS.get(i).size(); ++j) {
                System.out.println(j + " ");
            }
        }
    }

    public void getNConexionesCD() { //numero conexiones CentroDatos
        for (int i = 0; i < conexionesS.size(); ++i) {
            System.out.println("Sensor " + i + ": ");
            for (int j = 0; j < conexionesS.get(i).size(); ++j) {
                System.out.println(j);
            }
        }
    }

    public Integer getInfoEmmagatzemadaSensors(int num_sensor) {
        return info_Capturada_Sensor.get(num_sensor);
    }


    public int getInfoEmmagatzemadaCentroDatos(int num_centroDatos) {
        return info_Capturada_Sensor.get(num_centroDatos);
    }







}
