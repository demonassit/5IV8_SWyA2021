/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany._firmadigital_clavesarchivo;

/**
 *
 * @author demon
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.security.*;

import java.security.spec.*;

import javax.crypto.*;

import javax.crypto.interfaces.*;
import javax.crypto.spec.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;



public class FirmaDigitalArchivos {
    
    public static void main(String[] args) throws Exception{
    
        if(args.length != 1){
            mensajeAyuda();
            System.exit(1);
        }
        
        System.out.println("Crea los archivos : " + args[0]+".secreta" 
                + args[0]+".publica" + args[0]+".privada" );
        
        //añadir el proveedor
        
        Security.addProvider(new BouncyCastleProvider());
        
        //generamos las claves de rsa
        
        KeyPairGenerator generadorRSA = KeyPairGenerator.getInstance("RSA", "BC");
        
        //inicializamos
        
        generadorRSA.initialize(1024);
        
        KeyPair clavesRSA = generadorRSA.genKeyPair();
        
        //privada y publica
        PrivateKey clavePrivada = clavesRSA.getPrivate();
        
        PublicKey clavePublica = clavesRSA.getPublic();
        
        
        //keyfactory para la instancia de la llave con rsa
        
        KeyFactory keyfactoryRSA = KeyFactory.getInstance("RSA", "BC");
        
        /*
        
        vamos a volcar la clave privada con el fichero segun las normas de la firma
        digital con rsa
        para ello vamos a codificarla con PKCS8 
        */
        
        PKCS8EncodedKeySpec pkcs8spec = new PKCS8EncodedKeySpec(clavePrivada.getEncoded());
        
        //vamos a escribirla en un archivo
        
        FileOutputStream out = new FileOutputStream(args[0]+".privada");
        out.write(pkcs8spec.getEncoded());
        out.close();
        
        //vamos a recuperar la clave privada del fichero
        
        byte[] bufferpriv = new byte[5000];
        FileInputStream in = new FileInputStream(args[0]+".privada");
        in.read(bufferpriv, 0, 5000);
        in.close();
        
        //recuperamos la clave privada desde los datos codificados en formado PKCS8
        
        PKCS8EncodedKeySpec clavePrivadaSpec = new PKCS8EncodedKeySpec(bufferpriv);
        //clave privada de firma
        PrivateKey clavePrivada2 = keyfactoryRSA.generatePrivate(clavePrivadaSpec);
        
        //validacion de la clave para saber si el archivo coincide con la clave
        
        if(clavePrivada.equals(clavePrivada2)){
            System.out.println("Ok, la clave privada ha sido guardada y recuperada");
        }
        
        /*
        vamos a volcar la clave publica en un archivo bajo el standar como lo establece
        la norma x.509
        */
        
        
        X509EncodedKeySpec x509spec = new X509EncodedKeySpec(clavePublica.getEncoded());
        
        //vamos a escribir el archivo
        
        out = new FileOutputStream(args[0]+".publica");
        out.write(x509spec.getEncoded());
        out.close();
        
        /*
        vamos a recuuperar la clave publica del archivo
        */
        
        byte[] bufferpub = new byte[5000];
        in = new FileInputStream(args[0]+".publica");
        in.read(bufferpub, 0, 5000);
        in.close();
        
        //recuperamos y validamos que sea la correcta
        
        X509EncodedKeySpec clavePublicaSpec = new X509EncodedKeySpec(bufferpub);
        
        PublicKey clavePublica2 = keyfactoryRSA.generatePublic(clavePublicaSpec);
        
        if(clavePublica.equals(clavePrivada2)){
            System.out.println("OK, la clave publica ha sido guardada y verificada");
        }
        
        //vamos a crear una instancia DES
        
        KeyGenerator generadorDES = KeyGenerator.getInstance("DES");
        generadorDES.init(56);
        SecretKey claveSecretaDES = generadorDES.generateKey();
        
        /*
        vamos a crear un secretkeyfactory usando las transformaciones de la 
        clave secreta para poder cifrar un mensaje, firmarlo y verificarlo
        */
        
        SecretKeyFactory secretkeyFactoryDES = SecretKeyFactory.getInstance("DES");
        
        //volcado de llaves publicas y privadas
        
        /*
        //vamos a escribirla en un archivo
        
        FileOutputStream out = new FileOutputStream(args[0]+".secreta");
        out.write(claveSecretaDES.getEncoded());
        out.close();
        
        //vamos a recuperar la clave privada del fichero
        
        byte[] buffersecret = new byte[5000];
        FileInputStream in = new FileInputStream(args[0]+".secreta");
        in.read(buffersecret, 0, 5000);
        in.close();
        
        
        DESKeySpec DESspec = new DESKeySpec(buffersecret);
        SecretKey claveSecreta2 = secretkeyfactoryDES.generateSecret(DESspec);
        
        if(claveSecreta.equals(claveSecreta2)){
            System.out.println("OK, la clave publica ha sido guardada y verificada");
        }
        
        
        
        */
        
        
        
    
    }

    public static void mensajeAyuda() {
        System.out.println("Ejemplo de almacenamiento de llaves");
    }
    
}
