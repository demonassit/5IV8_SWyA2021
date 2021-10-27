/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author demon
 */

import java.io.IOException;
import java.io.InputStream;
import java.security.*;

import java.security.spec.*;

import javax.crypto.*;

import javax.crypto.spec.*;

import javax.crypto.interfaces.*;

//calculo de los numeros gigantescos

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.jcajce.provider.BouncyCastlePQCProvider;

/*
El provider BC es el que nos ayuda a poder hacer el calculo
de los numero p q n y fi de forma mas rapida, sin tener que estar
nosotros de forma manual realizando dicho calculo

*/
public class RSALIB {
    
    
    public static void main(String[] args) throws Exception{
        
        //añadir el provider para el soporte con RSA
        Security.addProvider(new BouncyCastleProvider());
        
        System.out.println("1.- Crear las llaves publicas y privadas");
        
        //inicializar el par de claves del algoritmo RSA
        
        KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA", "BC");
        
        //inicializar la llave [tamaño]
        
        keygen.initialize(1024);
        
        //generar las claves privadas y publicas
        
        KeyPair clavesRSA = keygen.genKeyPair();
        
        //definir la clave privada
        
        PrivateKey clavePrivada = clavesRSA.getPrivate();
        
        //definir la clave publica
        
        PublicKey clavePublica = clavesRSA.getPublic();
        
        
        System.out.println("2.- Introduce el texto Plano (64 caracteres)");
        
        byte[] bufferplano = leerLinea(System.in);
        
        /*
        Recordemos que existen diferentes tipos de modo del cifrado
        en el caso del Provider de BC, este no puede manejar de forma
        correcta el modo a bloques (ECB); por lo tanto solo puede
        cifrar con maximo 512 bits
        
        */
        
        Cipher cifrado = Cipher.getInstance("RSA", "BC");
        
        cifrado.init(Cipher.ENCRYPT_MODE, clavePublica);
        
        System.out.println("3.- Ciframos con la clave pública");
        
        byte[] buffercifrado = cifrado.doFinal(bufferplano);
        
        System.out.println("Texto Cifrado con Clave Pública");
        
        mostrarBytes(buffercifrado);
        
        System.out.println("\n");
        
        //vamos a descifrar
        
        cifrado.init(Cipher.DECRYPT_MODE, clavePrivada);
        
        System.out.println("4.- Desciframos con la Clave Privada");
        
        byte[] bufferdescifrado = cifrado.doFinal(buffercifrado);
        
        mostrarBytes(bufferdescifrado);
        
        System.out.println("\n");
        
        
        
        //ahora vamos a cifrar con la clave privada para ver que pasa
        
        cifrado = Cipher.getInstance("RSA", "BC");
        
        cifrado.init(Cipher.ENCRYPT_MODE, clavePrivada);
        
        System.out.println("5.- Ciframos con la clave privada");
        
        buffercifrado = cifrado.doFinal(bufferplano);
        
        System.out.println("Texto Cifrado con Clave Privada");
        mostrarBytes(buffercifrado);
        
        System.out.println("\n");
        
        //y vamos a descifrar con la clave publica para ver que pasa
        
        cifrado.init(Cipher.DECRYPT_MODE, clavePublica);
        
        System.out.println("6.- Desciframos con la Clave pública");
        
        bufferdescifrado = cifrado.doFinal(buffercifrado);
        
        mostrarBytes(bufferdescifrado);
        
        System.out.println("\n");
        
        
    }

    public static byte[] leerLinea(InputStream in) throws IOException {
        
        byte[] buffer1 = new byte[1000];
        int i = 0;
        byte c;
        c = (byte)in.read();
        while((c != '\n') && (i < 1000)){
            buffer1[i]=c;
            c = (byte)in.read();
            i++;
        }
        
        byte[] buffer2 = new byte[i];
        for(int j = 0; j < i; j++){
            buffer2[j] = buffer1[j];
        }
        return buffer2;
     }

    public static void mostrarBytes(byte[] buffer) {
        System.out.write(buffer, 0, buffer.length);
    }
    
}
