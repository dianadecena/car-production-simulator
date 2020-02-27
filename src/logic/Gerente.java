
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

            //Gererar número random entre 6 y 18
            int random = (int)(Math.random()*(18-6+1)+6);
            
            //Calculos para ver cuánto vale 1 hora en segundos
            //si 24 horas (numero2) son un día y un día (numero1) segundos, entonces (resultado) serán lo que vale una hora en segundos
            float numero1 = this.dia;          
            float numero2 = 24;      
            float resultado;
            resultado = numero1/numero2;
                
            try {
                estado = "durmiendo";
                System.out.println("El gerente está durmiendo");
                Thread.sleep((int)(resultado*random*1000));
                
                //Verificar si el jefe no está modificando el contador 
                mutexModifDias.acquire();
                
                estado = "verif. contador";
                System.out.println("El gerente está verificando el contador");
                //Tarda un tiempo verificando el contador 
                Thread.sleep((int)(resultado*0.5*1000));
                
                //Si la cantidad de días es igual a 0 debe despachar los carros 
                if(Jefe.getDiasDespacho() == 0){
                    estado = "despachando";  
                    //Verifica si el ensamblador no está modificando la variable 
                    mutexCarrosTerminados.acquire();
                    Thread.sleep((int)(resultado*0.5*1000));
                    Fabrica.setCarrosTerminados(0);
                    Jefe.setDiasDespacho(this.diasDespacho);
                    //El gerente ya ha cumplido su trabajo y le da acceso al ensamblador
                    mutexCarrosTerminados.release();
                } 

                //El gerente ya ha cumplido su trabajo y le da acceso al jefe
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
