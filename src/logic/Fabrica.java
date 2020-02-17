
package logic;

import java.util.concurrent.Semaphore;

/*
    Elaborado por:
      Diana Decena
      Jose Alguindigue
*/

public class Fabrica {
    
    //Variables para los parámetros
    public Archivo parametros;
    //Creación de cada almacén
    public Almacen almacenR, almacenP, almacenM;
    //Array para los productores 
    public Productor[] productoresR, productoresP, productoresM;
    //Array para los ensambladores 
    public Ensamblador[] ensambladores;
    //Crear jefe
    public Jefe jefe;
    //Crear gerente
    public Gerente gerente;
    //Semáforos para saber si hay espacio en el almacén 
    public Semaphore sVacioAlmacenR, sVacioAlmacenP, sVacioAlmacenM;
    //Semáforos para saber si  hay piezas en el almacén 
    public Semaphore sHayRuedas, sHayParabrisas, sHayMotores;
    //Semáforos para poder ingresar al almacén (productor o ensamblador)
    public Semaphore mutexAlmacenR, mutexAlmacenP, mutexAlmacenM;
    //Semáforo para modificar el contador de carros terminados (gerente o ensamblador)
    public Semaphore mutexCarrosTerminados;
    //Semáforo para modificar el contador de los días (jefe o gerente)
    public Semaphore mutexModifDias;
    public int cantProductoresR, cantProductoresP, cantProductoresM, cantEnsambladores, cantDias;
    public int maxProductoresR, maxProductoresP, maxProductoresM, maxEnsambladores;
    public static int carrosTerminados;
    public static int cantRuedas;
    
    public Fabrica() {
        this.inicializar();
    }
    
    public void inicializar(){
        this.parametros = new Archivo("parametros.txt");
        
        
        this.cantProductoresR = parametros.getInitProductoresR();
        this.cantProductoresP = parametros.getInitProductoresP();
        this.cantProductoresM = parametros.getInitProductoresM();
        this.cantEnsambladores = parametros.getInitEnsambladores();
        this.cantDias = parametros.getDiasDespacho();

        //Creación de almacenes para cada tipo de pieza 
        this.almacenR = new Almacen(this.parametros.getMaxAlmacenR(), 'R');
        this.almacenP = new Almacen(this.parametros.getMaxProductoresP(), 'P');
        this.almacenM = new Almacen(this.parametros.getMaxProductoresM(), 'M');
        
        //Creación de productores para cada tipo de pieza
        this.productoresR = new Productor[this.parametros.getMaxProductoresR()];
        this.productoresP = new Productor[this.parametros.getMaxProductoresP()];
        this.productoresM = new Productor[this.parametros.getMaxProductoresM()];
        
        //Máx productores para cada tipo de pieza
        this.maxProductoresR = parametros.getMaxProductoresR();
        this.maxProductoresP = parametros.getMaxProductoresP();
        this.maxProductoresM = parametros.getMaxProductoresM();
        this.maxEnsambladores = parametros.getMaxEnsambladores();
        
        //Creación de ensambladores
        this.ensambladores = new Ensamblador[this.parametros.getMaxEnsambladores()];
        
        //Semáforos para el almacén de ruedas 
        this.sVacioAlmacenR = new Semaphore(this.parametros.getMaxAlmacenR()); //inicialmente todos los espacios están vacíos
        this.sHayRuedas = new Semaphore(0); //inicialmente ningún espacio está lleno
        this.mutexAlmacenR = new Semaphore(1); //permitir el acceso a un hilo a la vez en el almacén 
        
        //Semáforos para el almacén de parabrisas
        this.sVacioAlmacenP = new Semaphore(this.parametros.getMaxAlmacenP());
        this.sHayParabrisas = new Semaphore(0);
        this.mutexAlmacenP = new Semaphore(1);
        
        //Semáforos para el almacén de motores
        this.sVacioAlmacenM = new Semaphore(this.parametros.getMaxAlmacenM());
        this.sHayMotores = new Semaphore(0);
        this.mutexAlmacenM = new Semaphore(1);
        
        //Semáforo para el acceso al contador de carros terminados
        this.mutexCarrosTerminados = new Semaphore(1);
        
        //Semáforo para el acceso al contador de días
        this.mutexModifDias = new Semaphore(1);
        
        this.crearProductores();
        this.crearEnsambladores();
        this.comenzarProductores();
        this.comenzarEnsambladores();
        
        carrosTerminados = 0;
        cantRuedas = 0;
    }
    
    public void crearProductores(){
        
        //Creación de productores de ruedas iniciales
        for(int i=0; i<this.parametros.getInitProductoresR(); i++){
            productoresR[i] = new Productor(this.parametros.getDia(), "ruedas", this.almacenR, this.sVacioAlmacenR, 
            this.sHayRuedas, this.mutexAlmacenR);
            productoresR[i].setCantRuedas(parametros.getMaxAlmacenR());
        }
        System.out.println("Productores de ruedas creados");
        
        //Creación de productores de parabrisas iniciales
        for(int i=0; i<this.parametros.getInitProductoresP(); i++){
            productoresP[i] = new Productor(this.parametros.getDia(), "parabrisas", this.almacenP, this.sVacioAlmacenP, 
            this.sHayParabrisas, this.mutexAlmacenP);
            productoresP[i].setCantParabrisas(parametros.getMaxAlmacenP());
        }
        System.out.println("Productores de parabrisas creados");
        
        //Creación de productores de motores iniciales
        for(int i=0; i<this.parametros.getInitProductoresM(); i++){
            productoresM[i] = new Productor(this.parametros.getDia(), "motores", this.almacenM, this.sVacioAlmacenM, 
            this.sHayMotores, this.mutexAlmacenM);
            productoresM[i].setCantMotores(parametros.getMaxAlmacenM());
        }
        System.out.println("Productores de motores creados");
    }
    
    public void crearEnsambladores(){
        
       //Creación de ensambladores iniciales
        for(int i=0; i<this.parametros.getInitEnsambladores(); i++){
            ensambladores[i] = new Ensamblador(this.parametros.getDia(), this.sVacioAlmacenR, 
            this.sHayRuedas, this.mutexAlmacenR, this.sVacioAlmacenP, 
            this.sHayParabrisas, this.mutexAlmacenP, this.sVacioAlmacenM, 
            this.sHayMotores, this.mutexAlmacenM, this.mutexCarrosTerminados);
            ensambladores[i].setCantMotores(parametros.getMaxAlmacenM());
            ensambladores[i].setCantParabrisas(parametros.getMaxAlmacenP());
            ensambladores[i].setCantRuedas(parametros.getMaxAlmacenR());
        }
        System.out.println("Ensambladores creados");
        
    }
    
    public void comenzarProductores(){
        
        //Creación de productores de ruedas iniciales
        for(int i=0; i<this.parametros.getInitProductoresR(); i++){
            productoresR[i].start();
        }
        System.out.println("Hilo de productores de ruedas iniciado");
        
        //Creación de productores de parabrisas iniciales
        for(int i=0; i<this.parametros.getInitProductoresP(); i++){
            productoresP[i].start();
        }
        System.out.println("Hilo de productores de parabrisas iniciado");
        
        //Creación de productores de motores iniciales
        for(int i=0; i<this.parametros.getInitProductoresM(); i++){
            productoresM[i].start();
        }
        System.out.println("Hilo de productores de motores iniciado");
    }
    
    public void comenzarEnsambladores(){
        
       //Creación de ensambladores iniciales
        for(int i=0; i<this.parametros.getInitEnsambladores(); i++){
            ensambladores[i].start();
        }
        System.out.println("Hilo de ensambladores iniciado");
    }
    
    public void contratarProductorR(){
        if(cantProductoresR < this.parametros.getMaxProductoresR()){
            productoresR[cantProductoresR] = new Productor(this.parametros.getDia(), "ruedas", this.almacenR, this.sVacioAlmacenR, 
            this.sHayRuedas, this.mutexAlmacenR);
            productoresR[cantProductoresR].start();
            cantProductoresR++;
            System.out.println("Contratado productor de ruedas");
        }
    }
    
    public void contratarProductorP(){
        if(cantProductoresP < this.parametros.getMaxProductoresP()){
            productoresP[cantProductoresP] = new Productor(this.parametros.getDia(), "parabrisas", this.almacenP, this.sVacioAlmacenP, 
            this.sHayParabrisas, this.mutexAlmacenP);
            productoresP[cantProductoresP].start();
            cantProductoresP++;
            System.out.println("Contratado productor de parabrisas");
        }
    }
    
    public void contratarProductorM(){
        if(cantProductoresM < this.parametros.getMaxProductoresM()){
            productoresM[cantProductoresM] = new Productor(this.parametros.getDia(), "motores", this.almacenM, this.sVacioAlmacenM, 
            this.sHayMotores, this.mutexAlmacenM);
            productoresM[cantProductoresM].start();
            cantProductoresM++;
            System.out.println("Contratado productor de motores");
        }
    }
    
    public void contratarEnsamblador(){
        if(cantEnsambladores < this.parametros.getMaxEnsambladores()){
            ensambladores[cantEnsambladores] = new Ensamblador(this.parametros.getDia(), this.sVacioAlmacenR, 
            this.sHayRuedas, this.mutexAlmacenR, this.sVacioAlmacenP, 
            this.sHayParabrisas, this.mutexAlmacenP, this.sVacioAlmacenM, 
            this.sHayMotores, this.mutexAlmacenM, this.mutexCarrosTerminados);
            ensambladores[cantEnsambladores].start();
            cantEnsambladores++;
            System.out.println("Contratado ensamblador");
        }
    }
    
    public void despedirProductorR(){
        if(cantProductoresR > 0){
            productoresR[cantProductoresR-1].setContratado(false);
            productoresR[cantProductoresR-1] = null;
            cantProductoresR--;
            System.out.println("Despedido productor de ruedas");
        }
    }
    
    public void despedirProductorP(){
        if(cantProductoresP > 0){
            productoresP[cantProductoresP-1].setContratado(false);
            productoresP[cantProductoresP-1] = null;
            cantProductoresP--;
            System.out.println("Despedido productor de parabrisas");
        }
    }
     
    public void despedirProductorM(){
        if(cantProductoresM > 0){
            productoresM[cantProductoresM-1].setContratado(false);
            productoresM[cantProductoresM-1] = null;
            cantProductoresM--;
            System.out.println("Despedido productor de motores");
        }
    }
    
    public void despedirEnsamblador(){
        if(cantEnsambladores > 0){
            ensambladores[cantEnsambladores-1].setContratado(false);
            ensambladores[cantEnsambladores-1] = null;
            cantEnsambladores--;
            System.out.println("Despedido ensamblador");
        }
    }

    public int getCantProductoresR() {
        return cantProductoresR;
    }

    public int getCantProductoresP() {
        return cantProductoresP;
    }

    public int getCantProductoresM() {
        return cantProductoresM;
    }

    public int getCantEnsambladores() {
        return cantEnsambladores;
    }

    public int getCantDias() {
        return cantDias;
    }

    public int getMaxProductoresR() {
        return maxProductoresR;
    }

    public int getMaxProductoresP() {
        return maxProductoresP;
    }

    public int getMaxProductoresM() {
        return maxProductoresM;
    }

    public int getMaxEnsambladores() {
        return maxEnsambladores;
    }
       
}
