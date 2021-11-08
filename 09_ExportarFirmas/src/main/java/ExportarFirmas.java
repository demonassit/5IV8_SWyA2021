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
import java.security.*;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.*;



public class ExportarFirmas {
    
    private static Cipher rsa;
    
    public static void main(String[] args) throws Exception{
    
        //generacion de las claves
        
        KeyPairGenerator generadorRSA = KeyPairGenerator.getInstance("RSA");
        
        KeyPair llaves = generadorRSA.generateKeyPair();
        
        PublicKey llavePublica = llaves.getPublic();
        
        PrivateKey llavePrivada = llaves.getPrivate();
        
        //metodo para salvar y recuperar los archivos de la clave publica
        
        saveKey(llavePublica, "publickey.key");
        
        llavePublica = loadPublicKey("publickey.key");
        
        //metodo para salvar y recuperar los archivos de la clave privada
        
        saveKey(llavePrivada, "privatekey.key");
        
        llavePrivada = loadPrivateKey("privatekey.key");
        
        
        
        rsa = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        
        //texto para cifrar
        
        String text = "Hola mundo de habia una vez un patito";
        
        
        //ciframos
        
        rsa.init(Cipher.ENCRYPT_MODE, llavePublica);
        
        byte[] encriptado = rsa.doFinal(text.getBytes());
        
        //vamos a escribir el cifrado para que sea visible
        
        for(byte b : encriptado){
            System.out.print(Integer.toHexString(0xFF & b));
        }
        System.out.println();
        
        //para descifrar
        rsa.init(Cipher.DECRYPT_MODE, llavePrivada);
        //aqui se queda
        System.out.println("hola");
        byte[] bytesdescifrados = rsa.doFinal();
        System.out.println("se muere");
        String textodescifrado = new String(bytesdescifrados);
        
        System.out.println(textodescifrado);
        
    }

    private static void saveKey(Key key, String archivo) throws Exception {
        byte[] llavesbytes = key.getEncoded();
        FileOutputStream fos = new FileOutputStream(archivo);
        fos.write(llavesbytes);
        fos.close();
    }

    private static PublicKey loadPublicKey(String archivo) throws Exception {
        FileInputStream fis = new FileInputStream(archivo);
        int numBytes = fis.available();
        byte[] bytes = new byte[numBytes];
        fis.read(bytes);
        fis.close();
        
        KeyFactory llaveBytePublic = KeyFactory.getInstance("RSA");
        KeySpec keyspec = new X509EncodedKeySpec(bytes);
        PublicKey nuevallavepublica = llaveBytePublic.generatePublic(keyspec);
        
        return nuevallavepublica;
                
    }

    private static PrivateKey loadPrivateKey(String archivo) throws Exception {
        FileInputStream fis = new FileInputStream(archivo);
        int numBytes = fis.available();
        byte[] bytes = new byte[numBytes];
        fis.read(bytes);
        fis.close();
        
        KeyFactory llaveBytePrivada = KeyFactory.getInstance("RSA");
        KeySpec keyspec = new PKCS8EncodedKeySpec(bytes);
        PrivateKey nuevallaveprivada = llaveBytePrivada.generatePrivate(keyspec);
        
        return nuevallaveprivada;
    }
    
    
}
