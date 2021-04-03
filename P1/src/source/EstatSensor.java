package src.source;

import IA.Red.Centro;
import IA.Red.CentrosDatos;
import IA.Red.Sensor;
import IA.Red.Sensores;
import IA.Red.Centro;

import java.util.*;

public class EstatSensor {

    /*
    ATRIBUTOS
     */
    static int numSensors;
    static int numCentros;
    static Sensores sens; //Conjunto de sensores
    static CentrosDatos cd; //Conjunto de centros de datos

    ArrayList<Integer> transmissionesSC; //conexiones que hace un sensor a otro sensor o centro

    //del 0 al numSensores nos referimos a sensores, del 100 al 10X nos referimos a centros (podrían ser caracteres tmb)

    ArrayList<Integer> numConectadosSC; //num de sensores o centros que tiene conectados 1 sensor o centro


    ArrayList<Integer> info_Capturada_SC; //agafar size sensor o centro i ver la info capturada de cada uno

    ArrayList<Integer> maxCapacidadEnviada; // la capacidad maxima de cada sensor / centro

    /* INFO
     //cada centro de datos puede recibir hasta 150 Mb/s
     //un centro de datos puede recibir hasta 25 conexiones
     //cada sensor puede recibir el doble de su capacidad (si cap = 2,
    //puede recibir 4
    */

    final double capacitat_Centre = 150; //capacitat centre es de 150 Mb/s

    public double cost_transmissio; //MINIMIZARLA

    public double info_perduda; //MINIMIZAR

    /*
     CONSTRUCTORA ESTADO INICIAL
     */
    public EstatSensor (int numSensores, int seedSens, int numCent, int seedCent) {
        numCentros = numCent;
        numSensors = numSensores;
        sens = new Sensores(numSensores, seedSens);
        cd = new CentrosDatos(numCent, seedCent);


        transmissionesSC = new ArrayList<>();
        for (int i = 0; i < numSensores + numCent; ++i) {
            transmissionesSC.add(-1);
        }

        numConectadosSC = new ArrayList<>();
        for (int j = 0; j < numSensores+numCent; ++j) {
            numConectadosSC.add(0);
        }

        //ArrayList<Integer> info_Capturada_Sensor; //agafar size sensor i per cada sensor veure infoCapturada

        info_Capturada_SC = new ArrayList<>(numSensores+numCent);

        maxCapacidadEnviada = new ArrayList<>(numSensores+numCent);


        for (int i = 0; i < numSensores; ++i) {
            int max = (int) (sens.get(i).getCapacidad()*2);
            int capacidad = (int) sens.get(i).getCapacidad();
            info_Capturada_SC.add(capacidad);
            maxCapacidadEnviada.add(max);

        }
        for (int i = numSensores; i < numSensores+numCent; ++i) {
            info_Capturada_SC.add(0);
            maxCapacidadEnviada.add(150);
        }

        cost_transmissio = 0;
        info_perduda = 0;

        Queue<Integer> conectadosACentro = new LinkedList<>();
        Queue<Integer> NoConectadosACentro = new LinkedList<>();
        ArrayList<Boolean> conectaAcentro = new ArrayList<>(numSensores);



        //calculamos com están los indicadores para el estado inicial

        /*
        //Estado inicial 3: sensores se conectan de manera random a centros
        int centroAss = 0;
        for (int s = 0; s < numSensores; ++s) {
            int centroAssignado = -1; //centro al que se va a conectar el sensor
            //Double distMin = -1.; //aquesta variable marca la distancia minima que hi ha d'un sensor a un altre
            boolean trobat = false;
            for (int c = 0; c < numCent && !trobat; ++c) {
                int centroRandom = centroAss;
                //int centroRandom = (int) Math.random() * numCent; //limita el random al numCentros que hay
                if (numConectadosSC.get(numSensores + centroRandom) < 25) {
                    centroAssignado = centroRandom;
                    trobat = true;
                    if (centroAss == numCent-1) centroAss = -1;
                    centroAss++;
                }
            }
            if (centroAssignado != -1) {
                transmissionesSC.set(s, numSensores + centroAssignado);
                numConectadosSC.set(numSensores + centroAssignado, getNConexionesCD(numSensores + centroAssignado) + 1);
                int repTotal = (int) (getInfoEmmagatzemadaSC(numSensores + centroAssignado) + sens.get(s).getCapacidad());
                info_Capturada_SC.set(numSensores + centroAssignado, repTotal);
                double dist = distSC(sens.get(s).getCoordX(), sens.get(s).getCoordY(), cd.get(centroAssignado).getCoordX(), cd.get(centroAssignado).getCoordY());
                cost_transmissio += Math.pow(dist, 2) * sens.get(s).getCapacidad(); //DIST^2 X volumenDadesS
                conectadosACentro.add(s);
            }
            else { //conecta sensor a sensor
                NoConectadosACentro.add(s);
            }
        }
        */


    }


    public void setTransmissionSC(int idO, int idD) {
        //CONECTA SENSOR A OTRO
        //idO es id origen, idD es destino
        //este operador envia info a un sensor o a un centro
        //hemos de tener en cuenta cual puede ser el más optimo

        if ((getNConexionesCD(idD) < 3) && (getInfoEmmagatzemadaSC(idD) <= sens.get(idD).getCapacidad()*2)) {
            transmissionesSC.set(idO, idD);
            int count = getNConexionesCD(idD)+1;
            numConectadosSC.set(idD, count);
            double info = getInfoEmmagatzemadaSC(idD);
            info_Capturada_SC.set(idD, (int) (info+sens.get(idO).getCapacidad()));
        }
    }
    private Queue<Integer> iniConectaSensores (Queue<Integer> disponibles, int id_Sensor) {
        //busca un sensor cualquiera disponible
        int destino = disponibles.peek();

        transmissionesSC.set(id_Sensor, destino);
        int count = getNConexionesCD(destino)+1;
        numConectadosSC.set(destino, count);
        info_Capturada_SC.set(destino, (int) (getInfoEmmagatzemadaSC(destino) + sens.get(id_Sensor).getCapacidad()));
        System.out.println("El sensor" +id_Sensor+ " se ha conectado al sensor " + destino);
        if(count == 3) {
            disponibles.remove();
        }
        return disponibles;
    }

    public void EstatInicial_1(){
        Queue<Integer> conectadosACentro = new LinkedList<>();
        Queue<Integer> NoConectadosACentro = new LinkedList<>();
        ArrayList<Boolean> conectaAcentro = new ArrayList<>(numSensors);
        //Estado inicial 1: todos los sensores estan conectados a centros mas cercano, los sensores que no se conectan , se conectan a un sensor cualquiera

        for (int s = 0; s < numSensors; ++s) {
            int proper = -1; //aquesta variable et marca el centre més proper a un sensor en concret
            double distMin = -1.; //aquesta variable marca la distancia minima que hi ha d'un sensor a un altre

            for (int c = 0; c < numCentros; ++c) {
                double dist = distSC(sens.get(s).getCoordX(), sens.get(s).getCoordY(), cd.get(c).getCoordX(), cd.get(c).getCoordY());
                if ((distMin == -1 || dist < distMin) && numConectadosSC.get(numSensors + c) < 25) {
                    distMin = dist;
                    proper = c;
                }
            }

            if (distMin != -1) { //si s'ha pogut connectar a algun centre:
                int repTotal = (int) (getInfoEmmagatzemadaSC(numSensors+proper) + sens.get(s).getCapacidad());
                info_Capturada_SC.set(numSensors+proper, repTotal);
                //una vegada ja hem mirat tot, finalment fem la conexió de sensor a centre
                numConectadosSC.set(numSensors+proper, getNConexionesCD(numSensors+proper)+1);
                transmissionesSC.set(s, numSensors+proper);
                double dist = distSC(sens.get(s).getCoordX(), sens.get(s).getCoordY(), cd.get(proper).getCoordX(), cd.get(proper).getCoordY());
                cost_transmissio += Math.pow(dist, 2) * sens.get(s).getCapacidad(); //DIST^2 X volumenDadesS
                //conectaAcentro.set(s,true);
                conectadosACentro.add(s);
                System.out.println("El sensor" + s +" se ha conectado al centro " + proper);
            }

            else { //conecta sensor a sensor
                NoConectadosACentro.add(s);
                System.out.println("Soy el sensor "+  s + "y no he encontrado centro");
            }
        }
        int id_sensor;
        // conectamos los sensores que no han cabido en los centros
        while (!NoConectadosACentro.isEmpty()) {
            id_sensor = NoConectadosACentro.remove();
            conectadosACentro = iniConectaSensores(conectadosACentro, id_sensor);
        }
        calculaHeuristic();
        System.out.println(estadoValido());
        //calculaHeuristic();

    }
    public void EstatInicial_2(){

        for (int s = 0; s < numSensors; ++s) {
            int centroAssignado = -1; //centro al que se va a conectar el sensor
            //Double distMin = -1.; //aquesta variable marca la distancia minima que hi ha d'un sensor a un altre
            int i = 0;
            while (centroAssignado == -1 && i < 10) {
                Random r = new Random();
                int centroRandom = r.nextInt(numCentros);
                if (numConectadosSC.get(numSensors + centroRandom) < 25) {
                    centroAssignado = centroRandom;

                }
                ++i;
            }

            if (centroAssignado != -1) {
                transmissionesSC.set(s, numSensors + centroAssignado);
                numConectadosSC.set(numSensors + centroAssignado, getNConexionesCD(numSensors + centroAssignado) + 1);
                int repTotal = (int) (getInfoEmmagatzemadaSC(numSensors + centroAssignado) + sens.get(s).getCapacidad());
                info_Capturada_SC.set(numSensors + centroAssignado, repTotal);
                cost_transmissio += getCost_transmissio(s, centroAssignado); //DIST^2 X volumenDadesS

                System.out.println("El sensor " + s +" se ha conectado al centro " + centroAssignado + "y esta en " + sens.get(s).getCoordX()  + " , " + sens.get(s).getCoordY()+ " con capacidad de " + sens.get(s).getCapacidad()) ;
            }
            // (getInfoEmmagatzemadaSC(sensorRandom) + sens.get(s).getCapacidad() <= sens.get(sensorRandom).getCapacidad() * 2)
            else { //conecta sensor a sensor
                boolean trobat = false;
                while (!trobat) {
                    Random r = new Random();
                    int sensorRandom = r.nextInt(numSensors);
                    if ((getNConexionesCD(sensorRandom) < 3) && (transmissionesSC.get(sensorRandom)!=-1) && (sensorRandom!=s)) {
                        transmissionesSC.set(s, sensorRandom);
                        int count = getNConexionesCD(sensorRandom)+1;
                        numConectadosSC.set(sensorRandom, count);
                        double info = getInfoEmmagatzemadaSC(sensorRandom);
                        info_Capturada_SC.set(sensorRandom, (int) (info + sens.get(s).getCapacidad()));
                        double dist = distSC(sens.get(s).getCoordX(), sens.get(s).getCoordY(), sens.get(sensorRandom).getCoordX(), sens.get(sensorRandom).getCoordY());
                        cost_transmissio += Math.pow(dist, 2) * (sens.get(s).getCapacidad() + getInfoEmmagatzemadaSC(s)); //DIST^2 X volumenDadesS
                        trobat = true;
                        System.out.println("El sensor" +s+ " se ha conectado al sensor " + sensorRandom);

                    }
                }
            }
        }
        calculaHeuristic();
    }

    private double getCost_transmissio(int sensorId, int centroId){
        double dist = distSC(sens.get(sensorId).getCoordX(), sens.get(sensorId).getCoordY(), cd.get(centroId).getCoordX(), cd.get(centroId).getCoordY());
        double cost =  Math.pow(dist, 2) * getInfoEmmagatzemadaSC(sensorId); //DIST^2 X volumenDadesS
        return cost;
    }

    public double getCost_All (int sensorId) {
        double cost = sens.get(sensorId).getCapacidad();
        int i = 2;
        int nextSensor = getTransmissionSC(sensorId);
        while (nextSensor < numSensors) {
            cost += sens.get(nextSensor).getCapacidad()*i;
            ++i;
            nextSensor = getTransmissionSC(nextSensor);
        }
        return cost;
    }
//
//    public void printEstado(){
//        for(int i = 0; i < numSensors; i++){
//
//        }
//        System.out.println("El sensor " + s +  " se ha conectado al sensor " + sensorRandom);
//        System.out.println("Ahora mismo tengo un coste de " + cost_transmissio);
//    }

    private boolean checkMaxConexiones(int nextSensor){
        //devuelve true si es correcto
       // System.out.println("Sensor "+ nextSensor+ "con num" +getNConexionesCD(nextSensor));
        if(nextSensor<numSensors) return getNConexionesCD(nextSensor) > 3;
        else return getNConexionesCD(nextSensor) > 25;
    }

    public boolean estadoValido(){
        //devuelve true si el estado inicial es valido
        int nextSensor;
        //para cada sensor
        for(int i = 0; i < numSensors; i++){
            nextSensor = transmissionesSC.get(i);
            if(checkMaxConexiones(nextSensor)) {
                System.out.println("Maximo con " + nextSensor);
                return false;
            }
            int puntoPartida = nextSensor;
            ArrayList<Integer> camino = new ArrayList<>();
            //camino.add(puntoPartida);
            //miramos que en el camino no hayan bucles y que el camino este conectado a centro
            while(nextSensor < numSensors) {
                if(nextSensor == -1 || camino.contains(nextSensor)){ //||// camino.contains(nextSensor)){
                    System.out.println(puntoPartida+ " alomejor fallo aqui " + nextSensor);
                    return false;
                }
                //System.out.println("Estoy en " + nextSensor);
                camino.add(nextSensor);
                nextSensor = transmissionesSC.get(nextSensor);

            }
        }
        return true;
    }
    //copia
    public EstatSensor(EstatSensor state) {

        sens = state.sens;
        cd = state.cd;

        transmissionesSC = new ArrayList<>(state.transmissionesSC);
        numConectadosSC = new ArrayList<>(state.numConectadosSC);

        info_Capturada_SC = new ArrayList<>(state.info_Capturada_SC);

        maxCapacidadEnviada = new ArrayList<>(state.maxCapacidadEnviada);

        cost_transmissio = state.cost_transmissio;
        info_perduda = state.info_perduda;

    }
    /* HEURISTICS */

    public double getHeuristic(float p_cost, float p_loss) {
//      devuelve es heuristico ponderado de nuestros indicadores
        calculaHeuristic();
        System.out.println("el cost es "+ cost_transmissio +" y la info es " + info_perduda);
        return (p_cost * cost_transmissio + info_perduda * p_loss);
    }


    private void calculaHeuristic(){
//      esta funcion deberia de ser llamada para calcular inicialmente el el coste y info perdida general
//      se puede actualizar el valor de estas variables facilmente en al hacer una operación

        int info_perdida = 0;
        double cost_trans = 0;
        cost_transmissio = 0;
        info_perduda = 0;

        for (int i = 0; i < numSensors; i++){
            int infoEnvia = info_Capturada_SC.get(i);
            int infoMax = maxCapacidadEnviada.get(i)*3;
            int perd = infoEnvia - infoMax;
            //si hay perdidas
            if(perd > 0) {
                info_perdida = perd;
                infoEnvia = infoMax;
            }
            Sensor sens_temp_origen = sens.get(i);
            int s_origen_X = sens_temp_origen.getCoordX();
            int s_origen_Y = sens_temp_origen.getCoordY();

            int destino=transmissionesSC.get(i);
            int s_dest_X=-1;
            int s_dest_Y=-1;
            if(destino<numSensors) {
                Sensor sens_temp_dest = sens.get(destino);
                s_dest_X = sens_temp_dest.getCoordX();
                s_dest_Y = sens_temp_dest.getCoordY();
            }
            else{
                Centro cent_temp_dest = cd.get(destino-numSensors);
                s_dest_X = cent_temp_dest.getCoordX();
                s_dest_Y = cent_temp_dest.getCoordY();
            }

            double dist_orig_dest = distSC(s_origen_X,s_origen_Y, s_dest_X, s_dest_Y);
            cost_trans = costDist(dist_orig_dest, (int) infoEnvia);
            System.out.println("-----------------------" + infoEnvia +"-----------------------------");

            if (infoEnvia < 0) System.exit(0);
            cost_transmissio += cost_trans;
            //if (cost_transmissio < 0) System.exit(0);
            info_perduda += info_perdida;
        }

        System.out.println("El coste de transmision inicial es "+ cost_transmissio+ " y la perduda es de "+ info_perduda);
    }

    /*OPERADORES*/
    //LOS OPERADORES SIEMPRE TIENEN QUE GENERAR UN ESTADO SOLUCIÓN, HAY QUE TENER EN CUENTA QUE CUANDO CAMBIAS EL ESTADO, MODIFICAS EL HEURISTICO(COSTE)

    //1. SWAP, ES DECIR, CAMBIA UN SENSOR QUE ESTA CONECTADO A ALGO POR OTRO ALGO
    //2. ELIMINAR CONEXION DE UN SENSOR A ALGO I AÑADIRLA EN OTRO LADO (SIEMPRE I CUANDO MEJORE EL COSTE I MAXIMIZE LA INFO)

    //los operadores tienen que generar estados validos
    //Han de explorar el espacio de búsqueda


   /* public void swap_ConexioSC (int id_sensor, int id_sensor2) {
        //En esta accion cambiar dos sensores que estan conectados a dos centros diferentes
        int centroAssig = -1;
        double minDist = -1;
        for (int c = numSensors; c < numSensors+numCentros; ++c) {
            double dist = distSC(sens.get(id_sensor).getCoordX(), sens.get(id_sensor).getCoordY(), sens.get(c).getCoordX(), sens.get(c).getCoordY());
            if ((dist < minDist && minDist != -1) || minDist == -1) {
                centroAssig = c;
                minDist = dist;
            }
        }
        if (numConectadosSC.get(centroAssig) < 25) {
            if (centroAssig != getTransmissionSC(id_sensor)) {
                int centroAnterior = getTransmissionSC(id_sensor);
                int countAnt = getNConexionesCD(centroAnterior)-1;
                int new_storageAnterior = getInfoEmmagatzemadaSC(centroAnterior)- getInfoEmmagatzemadaSC(id_sensor);
                numConectadosSC.set(centroAnterior, countAnt);
                info_Capturada_SC.set(centroAnterior, new_storageAnterior);

                int countNew = getNConexionesCD(centroAssig)+1;
                int new_storageNew = getInfoEmmagatzemadaSC(centroAssig) + getInfoEmmagatzemadaSC(id_sensor);
                numConectadosSC.set(centroAssig, countNew);
                transmissionesSC.set(id_sensor, centroAssig);
                info_Capturada_SC.set(centroAssig, new_storageNew);
            }
        }
        else {
            if (centroAssig != getTransmissionSC(id_sensor)) {
                double maxDist = distSC(sens.get(id_sensor).getCoordX(), sens.get(id_sensor).getCoordY(), sens.get(centroAssig).getCoordX(), sens.get(centroAssig).getCoordY());
                int sensorAssig = -1;
                for (int s = 0; s < numSensors; ++s) {
                    if (getTransmissionSC(s) == centroAssig) {
                        double dist = distSC(sens.get(s).getCoordX(), sens.get(s).getCoordY(), sens.get(centroAssig).getCoordX(), sens.get(centroAssig).getCoordY());
                        if ((dist > maxDist && maxDist != -1)) {
                            sensorAssig = s;
                            maxDist = dist;
                        }
                    }
                }
                int centroAnterior = getTransmissionSC(id_sensor);
                int countAnt = getNConexionesCD(centroAnterior)-1;
                int new_storageAnterior = getInfoEmmagatzemadaSC(centroAnterior)- getInfoEmmagatzemadaSC(id_sensor);
                numConectadosSC.set(centroAnterior, countAnt);
                info_Capturada_SC.set(centroAnterior, new_storageAnterior);

                int countNew = getNConexionesCD(centroAssig)+1;
                int new_storageNew = getInfoEmmagatzemadaSC(centroAssig) + getInfoEmmagatzemadaSC(id_sensor);
                numConectadosSC.set(centroAssig, countNew);
                transmissionesSC.set(id_sensor, centroAssig);
                transmissionesSC.set(sensorAssig, centroAnterior);
                info_Capturada_SC.set(centroAssig, new_storageNew);
            }
        }
    }

    */

    private void actualizaInfoTransmit(int id_sensor_partida, int capacitatSubstract) {

//      capacitatSubstract representa la info que transmitia la rama que estaba conectada antes
//      y al desconectarla hay que quitar su peso de quienes estaban conectados
        int nextSensor = id_sensor_partida;
        int infoPrimera = info_Capturada_SC.get(id_sensor_partida);
        nextSensor = transmissionesSC.get(nextSensor);
        int suma = maxCapacidadEnviada.get(nextSensor);
        //empezamos por el siguiente de partida
        while (nextSensor < numSensors) {
            System.out.println("PETA EN ACTUALIZA " + nextSensor);
            //sumamos los datos que envia el sensor solo
            suma += maxCapacidadEnviada.get(nextSensor);
            info_Capturada_SC.set(nextSensor, infoPrimera + suma - capacitatSubstract);
            nextSensor = transmissionesSC.get(nextSensor);

        }
//      next sensor ya es un centro
        info_Capturada_SC.set(nextSensor, infoPrimera + suma - capacitatSubstract);
    }

    /*
    private void swap_ConexioSS (int id_sensor) {
        int Sensor_cercano = -1;
        double distMin = -1;
        for (int s = 0; s < numSensors; ++s) {
            if (s != id_sensor) {
                double dist = distSC(sens.get(id_sensor).getCoordX(), sens.get(id_sensor).getCoordY(), sens.get(s).getCoordX(), sens.get(s).getCoordY());
                if ((dist < distMin && distMin != -1) || distMin == -1) {
                    Sensor_cercano = s;
                    distMin = dist;
                }
            }
        }
        if (numConectadosSC.get(Sensor_cercano) < 3) {
            if (Sensor_cercano != getTransmissionSC(id_sensor)) {
                int sensorAnterior = getTransmissionSC(id_sensor);
                int countAnt = getNConexionesCD(sensorAnterior) - 1;
                int new_storageAnterior = getInfoEmmagatzemadaSC(sensorAnterior) - getInfoEmmagatzemadaSC(id_sensor);
                numConectadosSC.set(sensorAnterior, countAnt);
                info_Capturada_SC.set(sensorAnterior, new_storageAnterior);

                int countNew = getNConexionesCD(Sensor_cercano) + 1;
                int new_storageNew = getInfoEmmagatzemadaSC(Sensor_cercano) + getInfoEmmagatzemadaSC(id_sensor);
                numConectadosSC.set(Sensor_cercano, countNew);
                transmissionesSC.set(id_sensor, Sensor_cercano);
                info_Capturada_SC.set(Sensor_cercano, new_storageNew);
            }
        } else {
            if (Sensor_cercano != getTransmissionSC(id_sensor)) {
                double maxDist = distSC(sens.get(id_sensor).getCoordX(), sens.get(id_sensor).getCoordY(), sens.get(Sensor_cercano).getCoordX(), sens.get(Sensor_cercano).getCoordY());
                int sensorAssig = -1;
                for (int s = 0; s < numSensors; ++s) {
                    if (getTransmissionSC(s) == Sensor_cercano) {
                        double dist = distSC(sens.get(s).getCoordX(), sens.get(s).getCoordY(), sens.get(Sensor_cercano).getCoordX(), sens.get(Sensor_cercano).getCoordY());
                        if ((dist > maxDist && maxDist != -1)) {
                            sensorAssig = s;
                            maxDist = dist;
                        }
                    }
                }
                int SensorAnterior = getTransmissionSC(id_sensor);
                int countAnt = getNConexionesCD(SensorAnterior) - 1;
                int new_storageAnterior = getInfoEmmagatzemadaSC(SensorAnterior) - getInfoEmmagatzemadaSC(id_sensor);
                numConectadosSC.set(SensorAnterior, countAnt);
                info_Capturada_SC.set(SensorAnterior, new_storageAnterior);

                int countNew = getNConexionesCD(Sensor_cercano) + 1;
                int new_storageNew = getInfoEmmagatzemadaSC(Sensor_cercano) + getInfoEmmagatzemadaSC(id_sensor);
                numConectadosSC.set(Sensor_cercano, countNew);
                transmissionesSC.set(id_sensor, Sensor_cercano);
                transmissionesSC.set(sensorAssig, SensorAnterior);
                info_Capturada_SC.set(Sensor_cercano, new_storageNew);
            }
        }
    }

     */

    private ArrayList<Double> calculaIndicadoresParcialesSwap(int id_sensor){
        //aqui actualizamos tambien nuestros indicadores

//      CALCULO DE EL COSTE
        Sensor sens_temp_origen = sens.get(id_sensor);
        int s_origen_X = sens_temp_origen.getCoordX();
        int s_origen_Y = sens_temp_origen.getCoordY();


        int destino=transmissionesSC.get(id_sensor);
        int s_dest_X=-1;
        int s_dest_Y=-1;
        if(destino<numSensors) {
            Sensor sens_temp_dest = sens.get(destino);
            s_dest_X = sens_temp_dest.getCoordX();
            s_dest_Y = sens_temp_dest.getCoordY();
        }
        else{
            Centro cent_temp_dest = cd.get(destino-numSensors);
            s_dest_X = cent_temp_dest.getCoordX();
            s_dest_Y = cent_temp_dest.getCoordY();
        }


//      calculamos cuanta info esta enviando
        double infoEnvia = info_Capturada_SC.get(id_sensor);


        double maxEnvia = maxCapacidadEnviada.get(id_sensor)*3;
        if(infoEnvia>maxEnvia) infoEnvia = maxEnvia;

//      calculamos distancia
        double dist_orig_dest = distSC(s_origen_X,s_origen_Y, s_dest_X, s_dest_Y);
//        cost_transmissio -= costDist(dist_orig_dest, (int) infoEnvia);


//      CALCULO DE LA PERDIDA
        int nextSensor = transmissionesSC.get(id_sensor);
        double infoEnviaNext = info_Capturada_SC.get(nextSensor);
        double maxEnviaNext = maxCapacidadEnviada.get(nextSensor)*3;
        double perd = infoEnviaNext - maxEnviaNext;
        //si hay perdidas
        if(perd < 0) perd = 0;

        ArrayList<Double> ret = new ArrayList<Double>();
        ret.add( costDist(dist_orig_dest, (int) infoEnvia));
        ret.add( perd);
        return ret;
    }

    public void swap (int id_sensor, int id_sensor2) {
//        En esta accion se intercambian sensores
//        hay que actualizar:
//        transmissiones (conexion)
//        info retransmitida por el sensor al que te conectas
//        info recibida por los centros
        //if (evitaCiclos(id_sensor,id_sensor2)) { return;}
        int aux_transmissions = getTransmissionSC(id_sensor);
//      obtenemos los datos que transmite la rama
        int infoTransmitRama = info_Capturada_SC.get(id_sensor2);
        int infoTransmitRama2 = info_Capturada_SC.get(id_sensor);



//      guardamos el coste de transmitir que tenemos antes del swap para cada sensor implicado
        //ArrayList<Double> oldParcialIndic = calculaIndicadoresParcialesSwap(id_sensor);

        //ArrayList<Double> oldParcialIndic2 = calculaIndicadoresParcialesSwap(id_sensor2);



//      hacemos el swap, actualizamos la info transmitida y calculamos el nuevo coste de stransmitir
        transmissionesSC.set(id_sensor, getTransmissionSC(id_sensor2));

        actualizaInfoTransmit(id_sensor, infoTransmitRama);

        System.out.println("ME QUEDAO AQUI");

        //ArrayList<Double> newParcialIndic = calculaIndicadoresParcialesSwap(id_sensor);

        System.out.println("AQUI2");

//      hacemos el swap, actualizamos la info transmitida y calculamos el nuevo coste de stransmitir
        transmissionesSC.set(id_sensor2, aux_transmissions);
        actualizaInfoTransmit(id_sensor2, infoTransmitRama2);
        //ArrayList<Double> newParcialIndic2 = calculaIndicadoresParcialesSwap(id_sensor2);

        System.out.println("ACABO DE hacer swap DEL ELEMENTO " + id_sensor +" al " + id_sensor2);

//      actualizamos el coste y la infoperdida
        //calculaHeuristic();
        //cost_transmissio += newParcialIndic.get(0) + newParcialIndic2.get(0) - oldParcialIndic.get(0) - oldParcialIndic2.get(0);
        //info_perduda += newParcialIndic.get(1) + newParcialIndic2.get(1) - oldParcialIndic.get(1) - oldParcialIndic2.get(1);
        //System.out.println("El coste de transmision es "+ cost_transmissio+ " y la perduda es de "+ info_perduda);

    }

    public void swap2 (int id_sensor, int id_sensor2) {
//        En esta accion se intercambian sensores
//        hay que actualizar:
//        transmissiones (conexion)
//        info retransmitida por el sensor al que te conectas
//        info recibida por los centros
        if (evitaCiclos(id_sensor,id_sensor2)) { return;}
        int aux_transmissions = getTransmissionSC(id_sensor);
//      obtenemos los datos que transmite la rama
        int infoTransmitRama = info_Capturada_SC.get(id_sensor2);
        int infoTransmitRama2 = info_Capturada_SC.get(id_sensor);



//      guardamos el coste de transmitir que tenemos antes del swap para cada sensor implicado
        ArrayList<Double> oldParcialIndic = calculaIndicadoresParcialesSwap(id_sensor);

        ArrayList<Double> oldParcialIndic2 = calculaIndicadoresParcialesSwap(id_sensor2);



//      hacemos el swap, actualizamos la info transmitida y calculamos el nuevo coste de stransmitir
        transmissionesSC.set(id_sensor, getTransmissionSC(id_sensor2));

        actualizaInfoTransmit(id_sensor, infoTransmitRama);

        System.out.println("ME QUEDAO AQUI");

        ArrayList<Double> newParcialIndic = calculaIndicadoresParcialesSwap(id_sensor);

        System.out.println("AQUI2");

//      hacemos el swap, actualizamos la info transmitida y calculamos el nuevo coste de stransmitir
        transmissionesSC.set(id_sensor2, aux_transmissions);
        actualizaInfoTransmit(id_sensor2, infoTransmitRama2);
        ArrayList<Double> newParcialIndic2 = calculaIndicadoresParcialesSwap(id_sensor2);

        System.out.println("ACABO DE hacer swap DEL ELEMENTO " + id_sensor +" al " + id_sensor2);

//      actualizamos el coste y la infoperdida
        //calculaHeuristic();
        cost_transmissio += newParcialIndic.get(0) + newParcialIndic2.get(0) - oldParcialIndic.get(0) - oldParcialIndic2.get(0);
        info_perduda += newParcialIndic.get(1) + newParcialIndic2.get(1) - oldParcialIndic.get(1) - oldParcialIndic2.get(1);
        System.out.println("El coste de transmision es "+ cost_transmissio+ " y la perduda es de "+ info_perduda);

    }

    public boolean evitaCiclos (int id_sensor1, int id_sensor2) {
        int next = transmissionesSC.get(id_sensor1);
        int aux = next;
        boolean bucle = false;
        while (next < numSensors) {

            if (next == id_sensor2) return true;
            next = transmissionesSC.get(next);

        }
        next = transmissionesSC.get(id_sensor2);
        while (next < numSensors) {
            if (next == id_sensor1) return true;
            next = transmissionesSC.get(next);
        }
        return false;
    }

    public void mover_Sensor (int id_sensor) {
        Random r = new Random();
        int sensorRandom = r.nextInt(numSensors);
        if (id_sensor != sensorRandom && (transmissionesSC.get(id_sensor) != transmissionesSC.get(sensorRandom))) {
            if (checkMaxConexiones(sensorRandom)) {
                transmissionesSC.set(id_sensor, sensorRandom);
            }


        }


    }


//    private void swap (int id_sensor) { //esta mira cual es el tipo de swap que hay que hacer
//        if (getTransmissionSC(id_sensor) < numSensors) swap_ConexioSS(id_sensor);
//        else swap_ConexioSC(id_sensor);
//    }


    /*
    private void canvia_ConexioSensor (int id_sensor, int nuevo_destino) {
        int id_que_transmite = getTransmissionSC(id_sensor);
        double dist = distSC()
    }
     */






    public double distSC(int coordX, int coordY, int coordX1, int coordY1) {
        int distX = coordX - coordX1;
        int distY = coordY - coordY1;

        double resX = Math.pow(distX, 2);
        double resY = Math.pow(distY, 2);

        return Math.sqrt(resX+resY);
    }

    public double costDist(double dist, int data){

        double ret = Math.pow(dist,2) * data;

        return ret;

    }



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

        return transmissionesSC.get(id);

    }

    public int getNConexionesCD(int id) { //numero conexiones CentroDatos

        return numConectadosSC.get(id);
    }

    public int getInfoEmmagatzemadaSC(int num_sensor) {
        return info_Capturada_SC.get(num_sensor);
    }

    public int getNumSensors() {
        return numSensors;
    }

    public int getNumCentros() {
        return numCentros;
    }


}