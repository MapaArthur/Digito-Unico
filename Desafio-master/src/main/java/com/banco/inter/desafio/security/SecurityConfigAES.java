package com.banco.inter.desafio.security;

import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class SecurityConfigAES {
	
	private static final Charset UTF_8 = Charset.forName("UTF-8");
	public static final String ALGORITMO = "AES";
	public static final Integer TAMANHO = 256;
	
	private SecretKey chave;
   

    public SecurityConfigAES(SecretKey chave) {
        this.chave = chave;       
    }
    
    public SecurityConfigAES(byte[] chave) {
    	SecretKeySpec spec = new SecretKeySpec(chave, ALGORITMO);
        this.chave = spec;       
    }
    
	/**
	 * Método responsável por gerar a chave privada e publica com Algoritmo em AES e do tamnanho 256.
	 * 
	 * @author arthurmapati@gmail.com	 	  
	 * 
	 * @return {@link KeyPair} - Chaves com Algoritmo em AES e do tamnanho 256
	 * @throws NoSuchAlgorithmException 
	 *	 
	 */
    
    public static SecretKey getChave() throws NoSuchAlgorithmException {
    	KeyGenerator generator = KeyGenerator.getInstance(ALGORITMO);
    	generator.init(TAMANHO);
    	SecretKey chave = generator.generateKey();
    	
        return chave;
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
    
    public String criptografar(String valor) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException  {        
    	Cipher cipher = Cipher.getInstance(ALGORITMO);
        cipher.init(Cipher.ENCRYPT_MODE, chave);        
        byte[] valorBase64Encoded = Base64.getEncoder().encode(cipher.doFinal(valor.getBytes()));
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
    
    public String descriptografar(String valor) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
            	
    	Cipher cipher = Cipher.getInstance(ALGORITMO);
        cipher.init(Cipher.DECRYPT_MODE, chave);        
        byte[] valorBase64Decoded = Base64.getDecoder().decode(valor.getBytes(UTF_8));
		return new String(cipher.doFinal(valorBase64Decoded), UTF_8);
    } 
}
