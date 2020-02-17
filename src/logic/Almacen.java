
package logic;


public class Almacen {
    
    //Variable para guardar la capacidad 
    private int capacidad;
    //Array para guardar 0 y 1 dependiendo si hay o no pizas en el almacén
    private int[] almacen;
    //Variable para guardar la cantidad de piezas en el almacén 
    private int cantPiezas;
    
    private boolean[] AlmacenRuedas;
    private boolean[] AlmacenParabrisas;
    private boolean[] AlmacenMotores;

    
    public Almacen(int maxPiezas, char tipo){
        //Crear array para guardar las piezas (motores, parabrisas o ruedas)
        //this.almacen = new int[this.capacidad];
        //this.cantPiezas = 0; 
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

    public int[] getAlmacen() {
        return almacen;
    }

    public void setAlmacen(int i, int valor) {
        this.almacen[i] = valor;
    }

    public int getCantPiezas() {
        return cantPiezas;
    }

    public void setCantPiezas(int cantPiezas) {
        this.cantPiezas = cantPiezas;
    }


    public boolean[] getAlmacenRuedas() {
        return AlmacenRuedas;
    }

    public boolean[] getAlmacenParabrisas() {
        return AlmacenParabrisas;
    }

    public boolean[] getAlmacenMotores() {
        return AlmacenMotores;
    }

    public void setAlmacen(int[] almacen) {
        this.almacen = almacen;
    }


    public void setAlmacenParabrisas(int pos, boolean valor) {
         this.AlmacenParabrisas[pos] =  valor;
    }
    
    public void setAlmacenMotores(int pos, boolean valor) {
        this.AlmacenMotores[pos] =  valor;
    }

    public void setAlmacenRuedas(int pos, boolean valor, int aux) {
        if(aux ==0){    
            if(valor = true){
            this.AlmacenRuedas[pos] =  valor;
            }
            if(valor = false){
                for (int i = pos; i < pos+4; i++) {
                    this.AlmacenRuedas[i] =  valor;
                }
             
            }
        }
        else{
            switch(aux) {
                case 1:
                    this.AlmacenRuedas[pos] =  valor;
                    this.AlmacenRuedas[pos+1] =  valor;
                    this.AlmacenRuedas[pos+2] =  valor;
                    this.AlmacenRuedas[0] =  valor;
                    break;
                case 2:
                    this.AlmacenRuedas[pos] =  valor;
                    this.AlmacenRuedas[pos+1] =  valor;
                    this.AlmacenRuedas[0] =  valor;
                    this.AlmacenRuedas[1] =  valor;
                    break;
                case 3:
                    this.AlmacenRuedas[pos] =  valor;
                    this.AlmacenRuedas[0] =  valor;
                    this.AlmacenRuedas[1] =  valor;
                    this.AlmacenRuedas[2] =  valor;
                    break;
                case 4:
                    this.AlmacenRuedas[0] =  valor;
                    this.AlmacenRuedas[1] =  valor;
                    this.AlmacenRuedas[2] =  valor;
                    this.AlmacenRuedas[3] =  valor;
                    break;
                    
            }
                
            
        }
    }
    
    
}
