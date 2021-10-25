/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author demon
 */

//vamos a necesitar a big integer para el calculo de los numerotes
import java.math.BigInteger;
import java.util.*;
import java.io.*;

public class RSA {
    
    int tamPrimo;
    BigInteger n, p, q;  //numeros que ocupa el RSA
    BigInteger fi;
    BigInteger e, d;
    
    //constructor de la clase
    public RSA(int tamPrimo){
        this.tamPrimo = tamPrimo;
    }
    
    //metodo para generar los numeros primos
    public void generarPrimos(){
        p = new BigInteger(tamPrimo, 10, new Random());
        do q = new BigInteger(tamPrimo, 10, new Random());
        while(q.compareTo(p)==0);
    }
    
    //el metodo para generar las claves
    //n = p * q
    //fi = (p-1)*(q-1)
    //de ahi hay que elegir el numero e y d eligiendo e como un coprimo y menor que n
    
    public void generarClaves(){
        //n = p*q
        n = p.multiply(q);
        
        //fi = (p-1)*(q-1)
        
        fi = p.subtract(BigInteger.valueOf(1));
        fi = fi.multiply(q.subtract(BigInteger.valueOf(1)));
        
        //como elegimos un coprimo de 1< e < fi
        
        do e = new BigInteger(2*tamPrimo, new Random());
        while((e.compareTo(fi) != -1) || (e.gcd(fi).compareTo(BigInteger.valueOf(1)) != 0));
        
        //calcular d
        // d = e^1 mod fi 
        //el multiplo inversor
        
        d = e.modInverse(fi);
        
    }
    
    //vamos a cifrar wiiiii usando la clave publica wiiiii
    //solo se pueden cifrar numeros doble wiiii
    
    
    public BigInteger[] cifrar(String mensaje){
    
        int i;
        byte[] temp = new byte[1];
        byte[] digitos = mensaje.getBytes();
        
        BigInteger[] bigdigitos = new BigInteger[digitos.length];
        
        //vamos a iterar esos digitos grandotes
        
        for(i = 0; i < bigdigitos.length; i++){
            temp[0] = digitos[i];
            bigdigitos[i] = new BigInteger(temp);
        }
        
        // C=M^e mod(n)
        
        BigInteger[] cifrado = new BigInteger[bigdigitos.length];
        
        for(i = 0; i < bigdigitos.length; i++){
            cifrado[i] = bigdigitos[i].modPow(e, n);
        }
        
        return cifrado;
    }
    
    //descifrar con la clave privada
    
    public String descifrar(BigInteger[] cifrado){
        
        BigInteger[] descifrado = new BigInteger[cifrado.length];
        
        // M = C ^d mod n
        
        for( int i = 0; i < descifrado.length; i++ ){
            descifrado[i] = cifrado[i].modPow(d, n);
        }
        
        char[] charArray = new char[descifrado.length];
        
        for( int i = 0; i < charArray.length; i++){
            charArray[i] = (char)(descifrado[i].intValue());
        }
        
        return (new String(charArray));
        
    }
    
    
    /*
    
    Opcion 1:
    
    Realizar este programa de calculo de RSA  con una hermosa
    ventana en swing usando metodos privados (1 persona) max 3 digitos los primos
    
    
    Opcion 2:
    
    Realizar este mismo algoritmo con un JSP y un servicio web en el cual
    se tiene un formulario y se agregan los correspondientes elementos 
    [con request y response] se hacen las operaciones [1 persona] maximo 3 digitos
    
    
    
    Opcion 3:
    Realizar un cliente y un servidor
    
    Cliente calcula sus numeros p y q n fi 
    Servidor calcula sus numeros p q n fi
    
    El equipo se pone de acuerdo para saber quien cifra y quien descifra los mensjaes
    de numeros en un chat 
    
    2 personas maximo 3 digitos
    
    
    
    
    */
    
    
}
