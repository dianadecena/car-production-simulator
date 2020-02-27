package logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;

public class Archivo {
	private int dia;
	private int diasDespacho;
	private int maxAlmacenR;
	private int maxAlmacenP;
	private int maxAlmacenM;
	private int initProductoresR;
	private int initProductoresP;
	private int initProductoresM;
	private int maxProductoresR;
	private int maxProductoresP;
        private int maxProductoresM;
	private int initEnsambladores;
        private int maxEnsambladores;

	public Archivo(String archivo){
        try{
            FileReader fr = new FileReader(archivo);
            BufferedReader br = new BufferedReader(fr);
            
            String linea;
            
            //Tiempo en segundos que dura un día 
            linea = br.readLine();
             if(esNumeroPositivo(linea)){
                this.dia = Integer.parseInt(linea);
            } else{
                Alerta a = new Alerta(null, true);
                a.setLocationRelativeTo(null);
                a.setDefaultCloseOperation(a.DO_NOTHING_ON_CLOSE);
                a.setVisible(true);
            }
            
            //Dias para el despacho de los carros 
            linea = br.readLine();
            if(esNumeroPositivo(linea)){
                this.diasDespacho = Integer.parseInt(linea);
            } else{
                Alerta a = new Alerta(null, true);
                a.setLocationRelativeTo(null);
                a.setDefaultCloseOperation(a.DO_NOTHING_ON_CLOSE);
                a.setVisible(true);
            }
            
            //Capacidad máxima del almacén de ruedas
            linea = br.readLine();
            if(esNumeroPositivo(linea)){
                this.maxAlmacenR = Integer.parseInt(linea);
            } else{
                Alerta a = new Alerta(null, true);
                a.setLocationRelativeTo(null);
                a.setDefaultCloseOperation(a.DO_NOTHING_ON_CLOSE);
                a.setVisible(true);
            }
            //Capacidad máxima del almacén de parabrisas
            linea = br.readLine();
            if(esNumeroPositivo(linea)){
                this.maxAlmacenP = Integer.parseInt(linea);
            } else{
                Alerta a = new Alerta(null, true);
                a.setLocationRelativeTo(null);
                a.setDefaultCloseOperation(a.DO_NOTHING_ON_CLOSE);
                a.setVisible(true);
            }
            //Capacidad máxima del almacén de motores 
            linea = br.readLine();
             if(esNumeroPositivo(linea)){
                this.maxAlmacenM = Integer.parseInt(linea);
            } else{
                Alerta a = new Alerta(null, true);
                a.setLocationRelativeTo(null);
                a.setDefaultCloseOperation(a.DO_NOTHING_ON_CLOSE);
                a.setVisible(true);
            }
            
            //Cantidad inicial de productores de ruedas
            linea = br.readLine();
            if(esNumeroPositivo(linea)){
                this.initProductoresR = Integer.parseInt(linea);
            } else{
                Alerta a = new Alerta(null, true);
                a.setLocationRelativeTo(null);
                a.setDefaultCloseOperation(a.DO_NOTHING_ON_CLOSE);
                a.setVisible(true);
            }
            //Cantidad inicial de productores de parabrisas
            linea = br.readLine();;
            if(esNumeroPositivo(linea)){
                this.initProductoresP = Integer.parseInt(linea);
            } else{
                Alerta a = new Alerta(null, true);
                a.setLocationRelativeTo(null);
                a.setDefaultCloseOperation(a.DO_NOTHING_ON_CLOSE);
                a.setVisible(true);
            }
            //Cantidad inicial de productores de motores
            linea = br.readLine();
            if(esNumeroPositivo(linea)){
                this.initProductoresM = Integer.parseInt(linea);
            } else{
                Alerta a = new Alerta(null, true);
                a.setLocationRelativeTo(null);
                a.setDefaultCloseOperation(a.DO_NOTHING_ON_CLOSE);
                a.setVisible(true);
            }
            
            //Cantidad máxima de productores de ruedas
            linea = br.readLine();
            if(esNumeroPositivo(linea)){
                this.maxProductoresR = Integer.parseInt(linea);
            } else{
                Alerta a = new Alerta(null, true);
                a.setLocationRelativeTo(null);
                a.setDefaultCloseOperation(a.DO_NOTHING_ON_CLOSE);
                a.setVisible(true);
            }
            //Cantidad máxima de productores de parabrisas
            linea = br.readLine();
            if(esNumeroPositivo(linea)){
                this.maxProductoresP = Integer.parseInt(linea);
            } else{
                Alerta a = new Alerta(null, true);
                a.setLocationRelativeTo(null);
                a.setDefaultCloseOperation(a.DO_NOTHING_ON_CLOSE);
                a.setVisible(true);
            }
            //Cantidad máxima de productores de motores
            linea = br.readLine();
            if(esNumeroPositivo(linea)){
                this.maxProductoresM = Integer.parseInt(linea);
            } else{
                Alerta a = new Alerta(null, true);
                a.setLocationRelativeTo(null);
                a.setDefaultCloseOperation(a.DO_NOTHING_ON_CLOSE);
                a.setVisible(true);
            }
            
            //Cantidad inicial de ensambladores
            linea = br.readLine();
            if(esNumeroPositivo(linea)){
                this.initEnsambladores = Integer.parseInt(linea);
            } else{
                Alerta a = new Alerta(null, true);
                a.setLocationRelativeTo(null);
                a.setDefaultCloseOperation(a.DO_NOTHING_ON_CLOSE);
                a.setVisible(true);
            }
            
            //Cantidad máxima de ensambladores 
            linea = br.readLine();
            if(esNumeroPositivo(linea)){
                this.maxEnsambladores = Integer.parseInt(linea);
            } else{
                Alerta a = new Alerta(null, true);
                a.setLocationRelativeTo(null);
                a.setDefaultCloseOperation(a.DO_NOTHING_ON_CLOSE);
                a.setVisible(true);
            }
        }
        catch(IOException e){
            System.out.println("ERROR");
        } 
    }
     
    //Comprobar si el dato es un número entero, positivo y que no sea una letra
    public boolean esNumeroPositivo(String linea) {
         try{
            int num = Integer.parseInt(linea);
             return num >= 0;
        } catch(NumberFormatException e) {
            return false;
        }
    }

    public int getDia() {
        return dia;
    }

    public int getDiasDespacho() {
        return diasDespacho;
    }

    public int getMaxAlmacenR() {
        return maxAlmacenR;
    }

    public int getMaxAlmacenP() {
        return maxAlmacenP;
    }

    public int getMaxAlmacenM() {
        return maxAlmacenM;
    }

    public int getInitProductoresR() {
        return initProductoresR;
    }

    public int getInitProductoresP() {
        return initProductoresP;
    }

    public int getInitProductoresM() {
        return initProductoresM;
    }

    public int getMaxProductoresR() {
        return maxProductoresR;
    }

    public int getMaxProductoresP() {
        return maxProductoresP;
    }

    public int getMaxProductoresM() {
        return maxProductoresM;
    }

    public int getInitEnsambladores() {
        return initEnsambladores;
    }

    public int getMaxEnsambladores() {
        return maxEnsambladores;
    }

}