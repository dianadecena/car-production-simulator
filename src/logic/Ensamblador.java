
package logic;

import java.util.concurrent.Semaphore;

public class Ensamblador extends Thread {
    
    int dia;
    Semaphore sVacioAlmacenR, sHayRuedas, mutexAlmacenR, sVacioAlmacenP, sHayParabrisas, mutexAlmacenP, sVacioAlmacenM, sHayMotores, mutexAlmacenM;
    Semaphore mutexCarrosTerminados;
    public boolean contratado;
    private static int ContR = 0, ContM = 0, ContP = 0;
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
                //Verificar si hay piezas listas para el carro
                sHayRuedas.acquire(4);
                sHayParabrisas.acquire(1);
                sHayMotores.acquire(1);
                
                //Se tarda 2 días en producir un carro
                System.out.println("Se está produciendo un carro");
                Thread.sleep(this.dia*2000); 
                
                //Verificar si el productor no está en el almacén para poder acceder
                mutexAlmacenR.acquire();
                //Verificar si el productor no está en el almacén para poder acceder
                mutexAlmacenP.acquire();
                //Verificar si el productor no está en el almacén para poder acceder
                mutexAlmacenM.acquire();
                
                //Sección crítica
                
                //Ruedas
                if(ContR+4 > cantRuedas){
                    int aux= ContR+4-cantRuedas;
                    Almacen.setAlmacenRuedas(ContR, false, aux);
                    Fabrica.setCantRuedas(Fabrica.getCantRuedas()-4);
                    ContR = aux;
                    aux =0;
                }
                else {
                    Almacen.setAlmacenRuedas(ContR, false, 0);
                    Fabrica.setCantRuedas(Fabrica.getCantRuedas()-4);
                    ContR = ContR+4;
                }
                
                //Parabrisas
                Almacen.setAlmacenParabrisas(ContP, false);
                ContP++;
                Fabrica.setCantParabrisas(Fabrica.getCantParabrisas()-1);
                if(ContP == cantParabrisas)
                    ContP=0;
                               
                //Motores
                Almacen.setAlmacenMotores(ContM, false);
                ContM++;
                Fabrica.setCantMotores(Fabrica.getCantMotores()-1);
                if(ContM == cantMotores)
                    ContM=0;

                //El ensamblador ya ha cumplido su trabajo y le da acceso al productor
                mutexAlmacenR.release();
                mutexAlmacenP.release();
                mutexAlmacenM.release();
                
                //Indicar que hay espacios vacios en los almacenes 
                sVacioAlmacenM.release(1);
                sVacioAlmacenP.release(1);
                sVacioAlmacenR.release(4);
                
                System.out.println("Se hizo un carro");
                Fabrica.setCarrosTerminados(Fabrica.getCarrosTerminados()+1);
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
