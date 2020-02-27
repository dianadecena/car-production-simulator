
package logic;

public class Almacen {
    
    //Variable para guardar la capacidad 
    private int capacidad;
    //Array para guardar 0 y 1 dependiendo si hay o no pizas en los almacenes  
    private static boolean[] AlmacenRuedas;
    private static boolean[] AlmacenParabrisas;
    private static boolean[] AlmacenMotores;

    
    public Almacen(int maxPiezas, char tipo){
        //Crear array para guardar las piezas (motores, parabrisas o ruedas)
        if (tipo == 'P'){
            this.capacidad = maxPiezas;
            this.AlmacenParabrisas = new boolean[this.capacidad];
        }
        if (tipo == 'M'){
            this.capacidad = maxPiezas;
             this.AlmacenMotores = new boolean[this.capacidad];
        }
        if (tipo == 'R'){
            this.capacidad = maxPiezas;
             this.AlmacenRuedas = new boolean[this.capacidad];
        }
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public static void setAlmacenParabrisas(int pos, boolean valor) {
         Almacen.AlmacenParabrisas[pos] =  valor;
    }
    
    public static void setAlmacenMotores(int pos, boolean valor) {
        Almacen.AlmacenMotores[pos] =  valor;
    }

    public static void setAlmacenRuedas(int pos, boolean valor, int aux) {
        if(aux ==0){    
            if(valor = true){
            Almacen.AlmacenRuedas[pos] =  valor;
            }
            if(valor = false){
                for (int i = pos; i < pos+4; i++) {
                    Almacen.AlmacenRuedas[i] =  valor;
                }
             
            }
        }
        else{
            switch(aux) {
                case 1:
                    Almacen.AlmacenRuedas[pos] =  valor;
                    Almacen.AlmacenRuedas[pos+1] =  valor;
                    Almacen.AlmacenRuedas[pos+2] =  valor;
                    Almacen.AlmacenRuedas[0] =  valor;
                    break;
                case 2:
                    Almacen.AlmacenRuedas[pos] =  valor;
                    Almacen.AlmacenRuedas[pos+1] =  valor;
                    Almacen.AlmacenRuedas[0] =  valor;
                    Almacen.AlmacenRuedas[1] =  valor;
                    break;
                case 3:
                    Almacen.AlmacenRuedas[pos] =  valor;
                    Almacen.AlmacenRuedas[0] =  valor;
                    Almacen.AlmacenRuedas[1] =  valor;
                    Almacen.AlmacenRuedas[2] =  valor;
                    break;
                case 4:
                    Almacen.AlmacenRuedas[0] =  valor;
                    Almacen.AlmacenRuedas[1] =  valor;
                    Almacen.AlmacenRuedas[2] =  valor;
                    Almacen.AlmacenRuedas[3] =  valor;
                    break;
            }    
        }
    }
}
