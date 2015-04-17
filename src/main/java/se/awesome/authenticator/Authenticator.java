package se.awesome.authenticator;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.UUID;

import se.awesome.encrypter.Encrypter;
import se.awesome.service.QuotesService;

public class Authenticator {

	private QuotesService quotesService;

	public Authenticator(QuotesService quotesService) {
		this.quotesService = quotesService;
	}

	public String login(String username, String password) {
		String dbPassword = quotesService.getPassword(username);
		String salt = quotesService.getSalt(username);
		String encrypted = Encrypter.getSecurePassword(password, salt);
		
		if (dbPassword.equals(encrypted)) {
			String authToken = UUID.randomUUID().toString();
			quotesService.createToken(authToken, username);
			return authToken;

		}
		return null;
	}

	public boolean isAuthTokenValid(String token, String username) {
		if (quotesService.containsToken(token, username)) {
			return true;
		}
		return false;
	}

}
