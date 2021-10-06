/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author demon
 */
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.*;




public class AES {
    
    private static final String ALGO = "AES";
    //llave
    
    private static final byte[] keyvalue = new byte[]{
    /*
        El valor de la llave va a ser conforme al algoritmo aes
        16 CARACTERES AES 128   9  rondas
        24 CARACTERES AES 192   11 rondas 
        32 CARACTERES AES 256   13 rondas
        */
        'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 
        'o', 'p', 'a', 's', 'd', 'f', 'g', 'h',
        'o', 'p', 'a', 's', 'd', 'f', 'g', 'h',
        'o', 'p', 'a', 's', 'd', 'f', 'g', 'h'
    };
    
    //vamos a crear el metodo para poder cifrar
    
    public static String encrypt(String Data) throws Exception{
        //Generar llaves
        Key key = generateKey();
        //cifrado
        Cipher cifrado = Cipher.getInstance(ALGO);
        //inicializo
        cifrado.init(Cipher.ENCRYPT_MODE, key);
        byte[] encValores = cifrado.doFinal(Data.getBytes());
        System.out.println("Valores sin formato : "+ encValores);
        //vamos a darle ahora si un formato para que pueda visualizar las cosas
        String valoresencriptados = new BASE64Encoder().encode(encValores);
        return valoresencriptados;
        
    }
    
    //decifrar
    
    
    public static String decrypt(String Datoencriptado) throws Exception{
        //Generar llaves
        Key key = generateKey();
        //cifrado
        Cipher cifrado = Cipher.getInstance(ALGO);
        //inicializo
        cifrado.init(Cipher.DECRYPT_MODE, key);
        
        /*
        Aqui hay un cambio gracias a que ahora si le estamos colocando un formato
        legible de salida
        Primero es decodificar los valores
        */
        
        byte[] decodificadorValores = new BASE64Decoder().decodeBuffer(Datoencriptado);
       
        byte[] decValores = cifrado.doFinal(decodificadorValores);
        System.out.println("Valores sin formato decodificados: "+ decodificadorValores);
        //vamos a darle ahora si un formato para que pueda visualizar las cosas
        String cadenadescifrada = new String(decValores);
        return cadenadescifrada;
        
    }

    private static Key generateKey() {
        Key key = new SecretKeySpec(keyvalue, ALGO);
        return key;
    }
    
}
