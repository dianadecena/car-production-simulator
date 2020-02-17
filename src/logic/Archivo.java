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
                JOptionPane.showMessageDialog(null, "El dato del tiempo en segundos que dura un día para el despacho no es válido", "ERROR", JOptionPane.WARNING_MESSAGE);
                System.exit(0);
            }
            
            //Dias para el despacho de los carros 
            linea = br.readLine();
            if(esNumeroPositivo(linea)){
                this.diasDespacho = Integer.parseInt(linea);
            } else{
                JOptionPane.showMessageDialog(null, "El dato de los días para el despacho no es válido", "ERROR", JOptionPane.WARNING_MESSAGE);
                System.exit(0);
            }
            
            //Capacidad máxima del almacén de ruedas
            linea = br.readLine();
            if(esNumeroPositivo(linea)){
                this.maxAlmacenR = Integer.parseInt(linea);
            } else{
                JOptionPane.showMessageDialog(null, "El dato de la capacidad máxima del almacén de ruedas no es válido", "ERROR", JOptionPane.WARNING_MESSAGE);
                System.exit(0);
            }
            //Capacidad máxima del almacén de parabrisas
            linea = br.readLine();
            if(esNumeroPositivo(linea)){
                this.maxAlmacenP = Integer.parseInt(linea);
            } else{
                JOptionPane.showMessageDialog(null, "El dato de la capacidad máxima del almacén de parabrisas no es válido", "ERROR", JOptionPane.WARNING_MESSAGE);
                System.exit(0);
            }
            //Capacidad máxima del almacén de motores 
            linea = br.readLine();
             if(esNumeroPositivo(linea)){
                this.maxAlmacenM = Integer.parseInt(linea);
            } else{
                JOptionPane.showMessageDialog(null, "El dato de la capacidad máxima del almacén de motores no es válido", "ERROR", JOptionPane.WARNING_MESSAGE);
                System.exit(0);
            }
            
            //Cantidad inicial de productores de ruedas
            linea = br.readLine();
            if(esNumeroPositivo(linea)){
                this.initProductoresR = Integer.parseInt(linea);
            } else{
                JOptionPane.showMessageDialog(null, "El dato de la cantidad inicial de productores de ruedas no es válido", "ERROR", JOptionPane.WARNING_MESSAGE);
                System.exit(0);
            }
            //Cantidad inicial de productores de parabrisas
            linea = br.readLine();;
            if(esNumeroPositivo(linea)){
                this.initProductoresP = Integer.parseInt(linea);
            } else{
                JOptionPane.showMessageDialog(null, "El dato de la cantidad inicial de productores de parabrisas no es válido", "ERROR", JOptionPane.WARNING_MESSAGE);
                System.exit(0);
            }
            //Cantidad inicial de productores de motores
            linea = br.readLine();
            if(esNumeroPositivo(linea)){
                this.initProductoresM = Integer.parseInt(linea);
            } else{
                JOptionPane.showMessageDialog(null, "El dato de la cantidad inicial de productores de motores no es válido", "ERROR", JOptionPane.WARNING_MESSAGE);
                System.exit(0);
            }
            
            //Cantidad máxima de productores de ruedas
            linea = br.readLine();
            if(esNumeroPositivo(linea)){
                this.maxProductoresR = Integer.parseInt(linea);
            } else{
                JOptionPane.showMessageDialog(null, "El dato de la cantidad máxima de productores de ruedas no es válido", "ERROR", JOptionPane.WARNING_MESSAGE);
                System.exit(0);
            }
            //Cantidad máxima de productores de parabrisas
            linea = br.readLine();
            if(esNumeroPositivo(linea)){
                this.maxProductoresP = Integer.parseInt(linea);
            } else{
                JOptionPane.showMessageDialog(null, "El dato de la cantidad máxima de productores de parabrisas no es válido", "ERROR", JOptionPane.WARNING_MESSAGE);
                System.exit(0);
            }
            //Cantidad máxima de productores de motores
            linea = br.readLine();
            if(esNumeroPositivo(linea)){
                this.maxProductoresM = Integer.parseInt(linea);
            } else{
                JOptionPane.showMessageDialog(null, "El dato de la cantidad máxima de productores de motores no es válido", "ERROR", JOptionPane.WARNING_MESSAGE);
                System.exit(0);
            }
            
            //Cantidad inicial de ensambladores
            linea = br.readLine();
            if(esNumeroPositivo(linea)){
                this.initEnsambladores = Integer.parseInt(linea);
            } else{
                JOptionPane.showMessageDialog(null, "El dato de la cantidad inicial de ensambladores no es válido", "ERROR", JOptionPane.WARNING_MESSAGE);
                System.exit(0);
            }
            
            //Cantidad máxima de ensambladores 
            linea = br.readLine();
            if(esNumeroPositivo(linea)){
                this.maxEnsambladores = Integer.parseInt(linea);
            } else{
                JOptionPane.showMessageDialog(null, "El dato de la cantidad máxima de ensambladores no es válido", "ERROR", JOptionPane.WARNING_MESSAGE);
                System.exit(0);
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