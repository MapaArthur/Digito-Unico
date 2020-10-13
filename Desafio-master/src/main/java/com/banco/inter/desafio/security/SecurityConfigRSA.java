package com.banco.inter.desafio.security;

import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

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

public class SecurityConfigRSA {
	
	private static final Charset UTF_8 = Charset.forName("UTF-8");
	public static final String ALGORITMO = "RSA";
	public static final Integer TAMANHO = 2048;
	
	private PublicKey chavePublica;
    private PrivateKey chavePrivada;

    public SecurityConfigRSA(PublicKey chavePublica, PrivateKey chavePrivada) {
        this.chavePublica = chavePublica;
        this.chavePrivada = chavePrivada;
    }
    
	/**
	 * Método responsável por gerar a chave privada e publica com Algoritmo em RSA e do tamnanho 2048.
	 * 
	 * @author arthurmapati@gmail.com	 	  
	 * 
	 * @return {@link KeyPair} - Chaves com Algoritmo em RSA e do tamnanho 2048
	 * @throws NoSuchAlgorithmException 
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
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 *	 
	 */
    
    public String criptografar(byte[] valor) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        
    	Cipher cipher = Cipher.getInstance(ALGORITMO);
        cipher.init(Cipher.ENCRYPT_MODE, chavePublica);
        byte[] valorBase64Encoded = Base64.getEncoder().encode(cipher.doFinal(valor));
		return new String(valorBase64Encoded, UTF_8);
    }
    
    /**
	 * Método responsável por descriptografar um dado.
	 * 
	 * @author arthurmapati@gmail.com	
	 * 
	 * @param {@link valor} - String que será descriptografadp	  
	 * 
	 * @return {@link String} - Valor descriptografado.
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 *	 
	 */
    
    public byte[] descriptografar(String valor) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
            	
    	Cipher cipher = Cipher.getInstance(ALGORITMO);
        cipher.init(Cipher.DECRYPT_MODE, chavePrivada);        
        byte[] valorBase64Decoded = Base64.getDecoder().decode(valor.getBytes(UTF_8));
		return cipher.doFinal(valorBase64Decoded);
    }   

}
