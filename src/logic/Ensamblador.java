
package logic;

import java.util.concurrent.Semaphore;

public class Ensamblador extends Thread {
    int dia;
    Semaphore sVacioAlmacenR, sHayRuedas, mutexAlmacenR, sVacioAlmacenP, sHayParabrisas, mutexAlmacenP, sVacioAlmacenM, sHayMotores, mutexAlmacenM;
    Semaphore mutexCarrosTerminados;
    Productor productor;
    public boolean contratado;
    private int ContR = 0, ContM = 0, ContP = 0;
    Almacen almacen;
    public int cantRuedas, cantParabrisas, cantMotores;

    public Ensamblador(int dia, Semaphore sVacioAlmacenR, Semaphore sHayRuedas, Semaphore mutexAlmacenR, Semaphore sVacioAlmacenP, 
    Semaphore sHayParabrisas, Semaphore mutexAlmacenP, Semaphore sVacioAlmacenM, Semaphore sHayMotores, Semaphore mutexAlmacenM,
    Semaphore mutexCarrosTerminados) {
        this.dia = dia;
        this.sVacioAlmacenR = sVacioAlmacenR;
        this.sHayRuedas = sHayRuedas;
        this.mutexAlmacenR = mutexAlmacenR;
        this.sVacioAlmacenP = sVacioAlmacenP;
        this.sHayParabrisas = sHayParabrisas;
        this.mutexAlmacenP = mutexAlmacenP;
        this.sVacioAlmacenM = sVacioAlmacenM;
        this.sHayMotores = sHayMotores;
        this.mutexAlmacenM = mutexAlmacenM;
        this.mutexCarrosTerminados = mutexCarrosTerminados;
        this.contratado = true;
    }
    
    @Override
    public void run() {
        
        while(contratado==true){

            try {
                System.out.println("Ensamblador esperando");
                sHayRuedas.acquire(4);
                sHayParabrisas.acquire(1);
                sHayMotores.acquire(1);
                
                System.out.println("Se está produciendo un carro");
                Thread.sleep(this.dia*2000); 
                
                mutexAlmacenR.acquire();
                //si contPR es 28 entra ya que 28+4=32 entonces.....
                //ContPR min puede ser 31 31-30=1
                if(ContR+4 >cantRuedas){
                    int aux= ContR+4-cantRuedas;
                    almacen.setAlmacenRuedas(ContR, false, aux);
                    ContR = aux;
                    aux =0;
                }
                else {
                    almacen.setAlmacenRuedas(ContR, false, 0);
                    ContR = ContR+4;}
                
                mutexAlmacenR.release();
                
                
                mutexAlmacenP.acquire();
                almacen.setAlmacenParabrisas(ContP, false);
                ContP++;
                if(ContP == cantParabrisas)
                    ContP=0;
                mutexAlmacenP.release();
                
                mutexAlmacenM.acquire();
                almacen.setAlmacenMotores(ContM, false);
                ContM++;
                if(ContM == cantMotores)
                    ContM=0;
                mutexAlmacenM.release();
                
                sVacioAlmacenM.release(1);
                sVacioAlmacenP.release(1);
                sVacioAlmacenR.release(4);
                
                System.out.println("Se hizo un carro");
                Fabrica.carrosTerminados++;
            }
                    
            catch (InterruptedException ex) {
                System.out.println("Ocurrió un error!");
            }
        }
}

    public void setContratado(boolean contratado) {
        this.contratado = contratado;
    }
    
    public void tomarRuedas(){
        
    }
    
    public void tomarMotor(){
        
    }
    
    public void tomarParabrisas(){
        
    }

    public void setCantRuedas(int cantRuedas) {
        this.cantRuedas = cantRuedas;
    }

    public void setCantParabrisas(int cantParabrisas) {
        this.cantParabrisas = cantParabrisas;
    }

    public void setCantMotores(int cantMotores) {
        this.cantMotores = cantMotores;
    }
    
}
