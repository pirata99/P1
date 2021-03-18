package src.source;

import IA.Red.CentrosDatos;
import IA.Red.Sensores;

import java.util.*;

public class EstatSensor {

    /*
    ATRIBUTOS
     */

    static Sensores sens; //Conjunto de sensores
    static CentrosDatos cd; //Conjunto de centros de datos

    ArrayList<Integer> transmissionesSC; //conexiones que hace un sensor a otro sensor o centro

    //del 0 al numSensores nos referimos a sensores, del 100 al 10X nos referimos a centros (podrían ser caracteres tmb)

    ArrayList<Integer> numConectadosSC; //num de sensores o centros que tiene conectados 1 sensor o centro


    ArrayList<Integer> info_Capturada_SC; //agafar size sensor o centro i ver la info capturada de cada uno

    /* INFO

     //cada centro de datos puede recibir hasta 150 Mb/s

     //un centro de datos puede recibir hasta 25 conexiones

     //cada sensor puede recibir el doble de su capacidad (si cap = 2,
    //puede recibir 4
    */

    final double capacitat_Centre = 150; //capacitat centre es de 150 Mb/s


    private double cost_transmissio; //MINIMIZARLA

    private double quantitat_informacio; //MAXIMIZARLA

    /*
     CONSTRUCTORA ESTADO INICIAL
     */
    public EstatSensor (int numSensores, int seedSens, int numCent, int seedCent) {

        sens = new Sensores(numSensores, seedSens);
        cd = new CentrosDatos(numCent, seedCent);

        transmissionesSC = new ArrayList<Integer>();
        for (int i = 0; i < numSensores + numCent; ++i) {
            transmissionesSC.add(-1);
        }

        numConectadosSC = new ArrayList<Integer>();
        for (int j = 0; j < numSensores+numCent; ++j) {
            numConectadosSC.add(0);
        }

        //ArrayList<Integer> info_Capturada_Sensor; //agafar size sensor i per cada sensor veure infoCapturada

        info_Capturada_SC = new ArrayList<Integer>(numSensores+numCent);



        for (int i = 0; i < numSensores; ++i) {
            int max = (int) (sens.get(i).getCapacidad()*2);
            info_Capturada_SC.add((int) sens.get(i).getCapacidad());
        }
        for (int i = numSensores; i < numSensores+numCent; ++i) {
            info_Capturada_SC.add(0);
        }

        cost_transmissio = 0;

        Queue<Integer> conectadosACentro = new LinkedList<>();
        Queue<Integer> NoConectadosACentro = new LinkedList<>();
        ArrayList<Boolean> conectaAcentro = new ArrayList<Boolean>(numSensores);

        for (int s = 0; s < numSensores; ++s) {
            int proper = -1; //aquesta variable et marca el centre més proper a un sensor en concret
            Double distMin = -1.; //aquesta variable marca la distancia minima que hi ha d'un sensor a un altre

            for (int c = 0; c < numCent; ++c) {
                double dist = distSC(sens.get(s).getCoordX(), sens.get(s).getCoordY(), cd.get(c).getCoordX(), cd.get(c).getCoordY());
                if ((distMin == -1 || dist < distMin) && numConectadosSC.get(numSensores + c) < 25) {
                    distMin = dist;
                    proper = c;
                }
            }

            if (distMin != -1) { //si s'ha pogut connectar a algun centre:
                int repTotal = (int) (getInfoEmmagatzemadaSC(numSensores+proper) + sens.get(s).getCapacidad());
                info_Capturada_SC.set(numSensores+proper, repTotal);
                //una vegada ja hem mirat tot, finalment fem la conexió de sensor a centre
                numConectadosSC.set(numSensores+proper, getNConexionesCD(numSensores+proper)+1);
                transmissionesSC.set(s, numSensores+proper);
                double dist = distSC(sens.get(s).getCoordX(), sens.get(s).getCoordY(), cd.get(proper).getCoordX(), cd.get(proper).getCoordY());
                cost_transmissio += Math.pow(dist, 2) * sens.get(s).getCapacidad(); //DIST^2 X volumenDadesS
                //conectaAcentro.set(s,true);
                conectadosACentro.add(s);
            }

            else {
                NoConectadosACentro.add(s);
            }
        }
        int id_sensor;
        while (!NoConectadosACentro.isEmpty()) {
            id_sensor = NoConectadosACentro.remove();
            conectadosACentro = iniConectaSensores(conectadosACentro, id_sensor);




        }

        /* CONECTA SENSOR A OTRO
        if ((getNConexionesCD(idD) <= 3) && (getInfoEmmagatzemadaSC(idD) <= sens.get(idD).getCapacidad()*2)) {
            transmissionesSC.set(idO, idD);
            int count = getNConexionesCD(idD);
            numConectadosSC.set(idD, count++);
            double info = getInfoEmmagatzemadaSC(idD);
            info_Capturada_SC.set(idD, info+sens.get(idO).getCapacidad());
        }

         */

    }

    private Queue<Integer> iniConectaSensores (Queue<Integer> disponibles, int id_Sensor) {
        int destino = disponibles.peek();
        transmissionesSC.set(id_Sensor, destino);
        int count = getNConexionesCD(destino)+1;
        numConectadosSC.set(destino, count);
        info_Capturada_SC.set(destino, (int) (getInfoEmmagatzemadaSC(destino) + sens.get(id_Sensor).getCapacidad()));
        if(count == 3) {
            disponibles.remove();
        }

        return disponibles;
    }

    private double distSC(int coordX, int coordY, int coordX1, int coordY1) {

        int distX = coordX - coordX1;
        int distY = coordY - coordY1;

        double resX = Math.pow(distX, 2);
        double resY = Math.pow(distY, 2);

        double res = Math.sqrt(resX+resY);

        return res;

    }

    public EstatSensor(EstatSensor state) {

        sens = state.sens;
        cd = state.cd;

        transmissionesSC = new ArrayList<Integer>(state.transmissionesSC);
        numConectadosSC = new ArrayList<Integer>(state.numConectadosSC);

        info_Capturada_SC = new ArrayList<Integer>(state.info_Capturada_SC);


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

    public int getTransmissionSC(int id) { //numero conexiones sensor

        System.out.println("Sensor " + id + ": Transmissiones :" + transmissionesSC.get(id));
        return transmissionesSC.get(id);

    }

    public int getNConexionesCD(int id) { //numero conexiones CentroDatos

        //System.out.println("Centro " + id + ": Conexiones :" + numConectadosSC.get(id));
        return numConectadosSC.get(id);
    }

    public int getInfoEmmagatzemadaSC(int num_sensor) {
        return info_Capturada_SC.get(num_sensor);
    }

     /*
    SETTERS
     */
/*
    public void setTransmissionSC(int idO, int idD) { //idO es id origen, idD es destino
        //este operador envia info a un sensor o a un centro
        //hemos de tener en cuenta cual puede ser el más optimo

        if ((getNConexionesCD(idD) <= 3) && (getInfoEmmagatzemadaSC(idD) <= sens.get(idD).getCapacidad()*2)) {
                transmissionesSC.set(idO, idD);
                int count = getNConexionesCD(idD);
                numConectadosSC.set(idD, count++);
                double info = getInfoEmmagatzemadaSC(idD);
                info_Capturada_SC.set(idD, info+sens.get(idO).getCapacidad());
        }
    }
*/


    public void setInfoEmmagatzemadaSC(int num_sensor) {

    }








}
