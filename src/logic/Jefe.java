
package logic;

import java.util.concurrent.Semaphore;

public class Jefe extends Thread {
    public static int diasDespacho;
    public int dia;
    public static String estado;
    Archivo parametros;
    private Semaphore mutexModifDias;
    
    public Jefe(int dia, int diasDespacho, Semaphore mutexModifDias, Archivo parametros){
       this.dia = dia;
       this.diasDespacho = diasDespacho;
       this.mutexModifDias = mutexModifDias;
       this.parametros = parametros;
       Jefe.estado = "durmiendo";
    }
    
     
    @Override
    public void run() {
        
        while(true){
            float numero1 = this.dia;          
            float numero2 = 24;      
            float resultado;
            resultado = numero1/numero2;

            try {                  
                //System.out.println((int)((this.dia*1000)-(resultado*1.5*1000)));
                Thread.sleep((int)((this.dia*1000)-(resultado*1.5*1000)));
                System.out.println("El jefe está dormido");
                mutexModifDias.acquire();
                                
                //Si el gerente no está modificando el contador, entonces el jefe lo puede modificar
                estado = "modif. contador";
                System.out.println("El jefe está modificando el contador");
                //System.out.println((int)(resultado*1.5*1000));
                Thread.sleep((int)(resultado*1.5*1000));

                if(Jefe.diasDespacho < 0){
                    Jefe.diasDespacho = parametros.getDiasDespacho();
                } else {
                    Jefe.diasDespacho--;
                }
                
                mutexModifDias.release();
                estado = "durmiendo";
                    
            } catch (InterruptedException ex) {
                System.out.println("Ocurrió un error!");
            }
        } 
    }

    public static int getDiasDespacho() {
        return diasDespacho;
    }

    public static String getEstado() {
        return estado;
    }

    public static void setDiasDespacho(int diasDespacho) {
        Jefe.diasDespacho = diasDespacho;
    }
    
}

