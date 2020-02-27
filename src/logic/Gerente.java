
package logic;

import java.util.concurrent.Semaphore;

public class Gerente extends Thread {
    public int diasDespacho;
    public int dia;
    public static String estado;
    private Semaphore mutexModifDias, mutexCarrosTerminados;
    
    public Gerente(int dia, int diasDespacho, Semaphore mutexModifDias, Semaphore mutexCarrosTerminados){
       this.dia = dia;
       this.diasDespacho = diasDespacho;
       this.mutexModifDias = mutexModifDias;
       this.mutexCarrosTerminados = mutexCarrosTerminados;
       Gerente.estado = "durmiendo";
    }
    
     
    @Override
    public void run() {
        
        while(true){

            int random = (int)(Math.random()*(18-6+1)+6);
            
            float numero1 = this.dia;          
            float numero2 = 24;      
            float resultado;
            resultado = numero1/numero2;
                
            try {
                estado = "durmiendo";
                System.out.println("El gerente está durmiendo");
                Thread.sleep((int)(resultado*random*1000));
                
                mutexModifDias.acquire();
                
                estado = "verif. contador";
                System.out.println("El gerente está verificando el contador");
                Thread.sleep((int)(resultado*0.5*1000));
                
                if(Jefe.getDiasDespacho() == 0){
                    estado = "despachando";  
                    mutexCarrosTerminados.acquire();
                    //System.out.println("El gerente está despachando los carros");
                    Thread.sleep((int)(resultado*0.5*1000));
                    Fabrica.setCarrosTerminados(0);
                    Jefe.setDiasDespacho(this.diasDespacho);
                    mutexCarrosTerminados.release();
                } 
                
                //estado = "DESPACHANDO LOS CARROS";
                mutexModifDias.release();
                    
            } catch (InterruptedException ex) {
                System.out.println("Ocurrió un error!");
            }
        } 
    }

    public static String getEstado() {
        return estado;
    }
    
}
