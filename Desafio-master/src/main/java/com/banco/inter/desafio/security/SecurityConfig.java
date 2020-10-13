package com.banco.inter.desafio.security;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Classe responsavel para criptogragar e descritogragar.
 * 
 * @author arthurmapati@gmail.com
 * 
 */

public class SecurityConfig {
	
	public static final String ALGORITMO = "RSA";
	public static final Integer TAMANHO = 2048;
	
	private PublicKey chavePublica;
    private PrivateKey chavePrivada;

    public SecurityConfig(PublicKey chavePublica, PrivateKey chavePrivada) {
        this.chavePublica = chavePublica;
        this.chavePrivada = chavePrivada;
    }
    
	/**
	 * Método responsável por gerar a chave privada e publica com Algoritmo em RSA e do tamnanho 2048.
	 * 
	 * @author arthurmapati@gmail.com	 	  
	 * 
	 * @return {@link KeyPair} - Chaves com Algoritmo em RSA e do tamnanho 2048
	 *	 
	 */
    
    public static KeyPair getParChave() throws NoSuchAlgorithmException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance(ALGORITMO);
        generator.initialize(TAMANHO);
        KeyPair parChave = generator.generateKeyPair();

        return parChave;
    }
    
    /**
	 * Método responsável por criptogragar um dado.
	 * 
	 * @author arthurmapati@gmail.com	
	 * 
	 * @param {@link valor} - String que será criptografada	  
	 * 
	 * @return {@link String} - Valor criptografado.
	 *	 
	 */
    
    public String criptografar(String valor) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException {
        
    	Cipher cipher = Cipher.getInstance(ALGORITMO);
        cipher.init(Cipher.ENCRYPT_MODE, chavePublica);        
        return new String(cipher.doFinal(valor.getBytes()));
    }
    
    /**
	 * Método responsável por descriptografar um dado.
	 * 
	 * @author arthurmapati@gmail.com	
	 * 
	 * @param {@link valor} - String que será descriptografadp	  
	 * 
	 * @return {@link String} - Valor descriptografado.
	 *	 
	 */
    
    public String descriptografar(String valor) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException, BadPaddingException, IllegalBlockSizeException {
            	
    	Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, chavePrivada);
        return new String(cipher.doFinal(valor.getBytes()));
    }   

}
