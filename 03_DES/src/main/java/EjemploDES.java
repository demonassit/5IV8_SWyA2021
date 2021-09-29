/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author demon
 */

import java.io.*;

/*Vamos a crear un programa que se encargue de poder cifrar un archivo
de texto a traves del cifrado DES utilizando las librerias de
crypto y security de java para atender los cifrados simetricos y asimetricos

Vamos a recibir como parametro un archivo de txt, asi como una clave*/

import javax.crypto.*;

import javax.crypto.spec.*;   //generar las llaves del cifrado

import java.security.*;      //trae las instancias del tipo de cifrado




public class EjemploDES {
    
    public static void main(String[] args) throws Exception {
        /*
        Lo primero que vamos a hacer es comprobar si tenemos cargado el fichero o
        archivo de texto que vamos a cifrar
        */
        
        if(args.length != 1){
            mensajeAyuda();
            System.exit(1);
        }
        
        
        /*
        Lo primero que se debe de hacer es cargar el tipo de instancia o proveedor
        del cifrado simetrico que se va a utilizar
        */
        
        System.out.println("1.- Generar la clave DES: ");
        //la llave la podemos crear a partir de una funcion generica
        //llamada funcion hash   MD5
        //una secuencia de numeros pseudoaleatorios
        KeyGenerator generadorDES = KeyGenerator.getInstance("DES");
        
        //inicializar la llave
        //llave de DES 56 bits
        //tamaño de la llave
        generadorDES.init(56);
        
        //vamos a generar la llave secreta para que entre al proceso iterativo de rondas
        //recordemos que en el proceso DES entra a una etapa de generacion de llaves 
        //entradaba a las 16 rondas con las subllaves 
        //esta es para generar esas subllaves
        SecretKey subllave = generadorDES.generateKey();
        
        
        System.out.println("Clave : " + subllave);
        
        //vamos a empezar con el algoritmo
        //vamos a crear las subllaves a su formato binario
        mostrarBytes(subllave.getEncoded());
        
        System.out.println("");
        
        //vamos a cifrar
        //aqui es donde vamos a definir el tipo de cifrado
        //el modo del cifrado
        //si se debe de agregar o no relleno para aumentar la seguridad del mismo
        //si va a tener algun estandar PKCS
        Cipher cifrado = Cipher.getInstance("DES/ECB/PKCS5Padding");  //AES, RSA, cualquier otro tipo de cifrado
        /*
        Vamos a decirle que todos los bloques de 64 bits que se conformen para 
        cifrar con des si el bloque no alcanza a llenarse con el texto plano
        pueda aplicar relleno para acompletar el bloque
        
        ALGORITMO : DES
        MODO : ECB (CIFRADO DE BLOQUES ELECTRONICO)
        RELLENO: PKCS5
        */
        
        System.out.println("2.- Cifrar con DES el fichero " + args[0] + ", dejar el resultado en: " 
                +args[0]+".cifrado");
        
        cifrado.init(Cipher.ENCRYPT_MODE, subllave);
        
        
        //leer el archivo y tener el buffer para la lectura, el tamaño y que entre en un bucle hasta
        //terminar de leer el tamño del archivo
        
        //el archivo o fichero lo transformamos a bits y hay que leerlo y cifrarlo o descifrarlo
        
        byte[] buffer = new byte[1000]; //quiero ir leyendo de 1000 en 1000 bits del fichero
        
        byte[] bufferCifrado; //aqui voy almacenar el resultado
        
        FileInputStream in = new FileInputStream(args[0]);
        
        FileOutputStream out = new FileOutputStream(args[0]+".cifrado");
        
        int bytesleidos = in.read(buffer, 0, 1000);
        while(bytesleidos != -1){
            //mientras no se llegue al final del archivo o fichero
            bufferCifrado = cifrado.update(buffer, 0, bytesleidos);
            //para el texto claro leer y enviarlo al buffer cifrado
            out.write(bufferCifrado);
            //escribir el archivo cifrado
            bytesleidos = in.read(buffer, 0, 1000);
        }
        //acompletar el fichero o archivo con el cifrado
        bufferCifrado = cifrado.doFinal();
        out.write(bufferCifrado); //escribir el final del texto cifrado si lo hay
        
        in.close();
        out.close();
        
        System.out.println("3.- Descifrar con DES el fichero " +args[0]+".cifrado" 
                + ", dejar el resultado en: " +args[0]+".descifrado");
        
        //vamos a descifrar
        cifrado.init(Cipher.DECRYPT_MODE, subllave);
        
        byte[] bufferPlano; //aqui voy almacenar el resultado descifrado
        
         in = new FileInputStream(args[0]+".cifrado");
        
         out = new FileOutputStream(args[0]+".descifrado");
        
        bytesleidos = in.read(buffer, 0, 1000);
        while(bytesleidos != -1){
            //mientras no se llegue al final del archivo o fichero
            bufferPlano = cifrado.update(buffer, 0, bytesleidos);
            //para el texto claro leer y enviarlo al buffer cifrado
            out.write(bufferPlano);
            //escribir el archivo cifrado
            bytesleidos = in.read(buffer, 0, 1000);
        }
        //acompletar el fichero o archivo con el descifrado
        bufferPlano = cifrado.doFinal();
        out.write(bufferPlano); //escribir el final del texto descifrado si lo hay
        
        in.close();
        out.close();
        
        
        
        
        
    }

    public static void mensajeAyuda() {
        System.out.println("Ejemplo de un cifrado DES utilizando librerias Crypto y Security");
        System.out.println("Necesita la carga de un archivo en txt, no se te olvide ¬¬ agregarlo");
        System.out.println("Con amor yo");
    }

    public static void mostrarBytes(byte[] buffer) {
        System.out.write(buffer, 0, buffer.length);
    }
    
}
