
package logic;

import java.util.concurrent.Semaphore;

public class Productor extends Thread {
    
    private int dia;
    String tipoProductor;
    Almacen almacen;
    Semaphore sVacio, sHayPieza, mutexAlmacen;
    public int cantRuedas, cantParabrisas, cantMotores;
    public boolean contratado;
    private int ContPR = 0, ContPM = 0, ContPP = 0;
    public Archivo parametros;
    
    
    public Productor(int dia, String tipoProductor, Almacen almacen, Semaphore sVacio, Semaphore sHayPieza, Semaphore mutexAlmacen){
        this.dia = dia;
        this.tipoProductor = tipoProductor;
        this.almacen = almacen;
        this.sVacio = sVacio;
        this.sHayPieza = sHayPieza;
        this.mutexAlmacen = mutexAlmacen;
        this.contratado = true;
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
                
                if(ContPR < cantRuedas){
                    almacen.setAlmacenRuedas(ContPR, true, 0);
                    Fabrica.cantRuedas++;
                    ContPR++;
                    if(ContPR == cantRuedas)
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
                if(ContPP < cantParabrisas){
                    almacen.setAlmacenParabrisas(ContPP, true);
                    ContPP++;
                    if(ContPP == cantParabrisas)
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
                if(ContPM < cantMotores){
                    almacen.setAlmacenMotores(ContPM, true);
                    ContPM++;
                    if(ContPM == cantMotores)
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

    public int getCantRuedas() {
        return cantRuedas;
    }

    public int getCantParabrisas() {
        return cantParabrisas;
    }

    public int getCantMotores() {
        return cantMotores;
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

    public void setContratado(boolean contratado) {
        this.contratado = contratado;
    }
    
    public void almacenarPieza(){
        
    }
     
}
