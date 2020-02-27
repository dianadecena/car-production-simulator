
package logic;

import java.util.concurrent.Semaphore;

public class Productor extends Thread {
    
    private int dia;
    String tipoProductor;
    Semaphore sVacio, sHayPieza, mutexAlmacen;
    public int cantMax;
    public boolean contratado;
    public static int ContPR = 0, ContPM = 0, ContPP = 0;
    
    public Productor(int dia, String tipoProductor, Almacen almacen, Semaphore sVacio, Semaphore sHayPieza, Semaphore mutexAlmacen,
    int cantMax){
        this.dia = dia;
        this.tipoProductor = tipoProductor;
        this.sVacio = sVacio;
        this.sHayPieza = sHayPieza;
        this.mutexAlmacen = mutexAlmacen;
        this.contratado = true;
        this.cantMax = cantMax;
    }
    
    @Override
    public void run() {
        
        while(this.contratado==true){

            try {
                switch(this.tipoProductor) {
            
            case "ruedas":
              
                //Verificar si hay espacio en el almacén   
                sVacio.acquire();
            
                //Se tarda 1 un día en producir una rueda 
                System.out.println("Se está produciendo una rueda");
                Thread.sleep(this.dia*1000);
            
                //Verificar si el ensamblador no está en el almacén para poder acceder
                mutexAlmacen.acquire();
            
                //Sección crítica 
                
               if(ContPR < cantMax && Fabrica.getCantRuedas() < cantMax){
                    Almacen.setAlmacenRuedas(ContPR, true, 0);
                    Fabrica.setCantRuedas(Fabrica.getCantRuedas()+1);
                    ContPR++;
                    if(ContPR == cantMax)
                        ContPR = 0;
                }
               
                System.out.println("Se hizo una rueda");
                System.out.println(Fabrica.cantRuedas);
                
                //El productor ya ha cumplido su trabajo y le da acceso al ensamblador
                mutexAlmacen.release();
            
                //Indicar que hay una pieza en el almacén
                sHayPieza.release();
            
                break;
                
            case "parabrisas":
                
                //Verificar si hay espacio en el almacén   
                sVacio.acquire();
            
                //Se tarda 2 días en producir un parabrisas
                System.out.println("Se está produciendo un parabrisas");
                Thread.sleep(this.dia*2000);
            
                //Verificar si el ensamblador no está en el almacén para poder acceder
                mutexAlmacen.acquire();
            
                //Sección crítica 
                if(ContPP < cantMax && Fabrica.getCantParabrisas() < cantMax){
                    Almacen.setAlmacenParabrisas(ContPP, true);
                    Fabrica.setCantParabrisas(Fabrica.getCantParabrisas()+1);
                    ContPP++;
                    if(ContPP == cantMax)
                        ContPP = 0;
                }                
               
                System.out.println("Se hizo un parabrisas");
                //El productor ya ha cumplido su trabajo y le da acceso al ensamblador
                mutexAlmacen.release();
            
                //Indicar que hay una pieza en el almacén
                sHayPieza.release();
            
                break;
            
            case "motores":
                
                //Verificar si hay espacio en el almacén   
                sVacio.acquire();
            
                //Se tarda 1 un día en producir una rueda 
                System.out.println("Se está produciendo un motor");
                Thread.sleep(this.dia*3000);
            
                //Verificar si el ensamblador no está en el almacén para poder acceder
                mutexAlmacen.acquire();
            
                //Sección crítica 
                if(ContPM < cantMax && Fabrica.getCantMotores() < cantMax){
                    Almacen.setAlmacenMotores(ContPM, true);
                    ContPM++;
                    Fabrica.setCantMotores(Fabrica.getCantMotores()+1);
                    if(ContPM == cantMax)
                        ContPM = 0;
                } 
                
                System.out.println("Se hizo un motor");
                //El productor ya ha cumplido su trabajo y le da acceso al ensamblador
                mutexAlmacen.release();
            
                //Indicar que hay una pieza en el almacén
                sHayPieza.release();
            
                break;
            }
                    
            } catch (InterruptedException ex) {
                System.out.println("Ocurrió un error!");
            }
        }
    }

    public void setContratado(boolean contratado) {
        this.contratado = contratado;
    }
     
}
