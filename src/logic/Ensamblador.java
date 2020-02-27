
package logic;

import java.util.concurrent.Semaphore;

public class Ensamblador extends Thread {
    int dia;
    Semaphore sVacioAlmacenR, sHayRuedas, mutexAlmacenR, sVacioAlmacenP, sHayParabrisas, mutexAlmacenP, sVacioAlmacenM, sHayMotores, mutexAlmacenM;
    Semaphore mutexCarrosTerminados;
    Productor productor;
    public boolean contratado;
    private int ContR = 0, ContM = 0, ContP = 0;
    public int capacidadAlmacenR;
    public int cantRuedas, cantParabrisas, cantMotores;

    public Ensamblador(int dia, Semaphore sVacioAlmacenR, Semaphore sHayRuedas, Semaphore mutexAlmacenR, Semaphore sVacioAlmacenP, 
    Semaphore sHayParabrisas, Semaphore mutexAlmacenP, Semaphore sVacioAlmacenM, Semaphore sHayMotores, Semaphore mutexAlmacenM,
    Semaphore mutexCarrosTerminados, int cantRuedas, int cantParabrisas, int cantMotores) {
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
        this.cantRuedas = cantRuedas;
        this.cantParabrisas = cantParabrisas;
        this.cantMotores = cantMotores;
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
               if(ContR+4 > cantRuedas){
                    int aux= ContR+4-cantRuedas;
                    Almacen.setAlmacenRuedas(ContR, false, aux);
                    Fabrica.cantRuedas = Fabrica.cantRuedas - 4;
                    ContR = aux;
                    aux =0;
                }
                else {
                    Almacen.setAlmacenRuedas(ContR, false, 0);
                    Fabrica.cantRuedas = Fabrica.cantRuedas - 4;
                    ContR = ContR+4;}
                mutexAlmacenR.release();
                
                
                mutexAlmacenP.acquire();
                Almacen.setAlmacenParabrisas(ContP, false);
                ContP++;
                 Fabrica.cantParabrisas--;
                if(ContP == cantParabrisas)
                    ContP=0;
                mutexAlmacenP.release();
                
                mutexAlmacenM.acquire();
                Almacen.setAlmacenMotores(ContM, false);
                ContM++;
                Fabrica.cantMotores--;
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
    
}
