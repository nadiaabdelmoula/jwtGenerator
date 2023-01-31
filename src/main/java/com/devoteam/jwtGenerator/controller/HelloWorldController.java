package com.devoteam.jwtGenerator.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import model.Jwtattributs;

import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.jwt.consumer.JwtContext;
import org.jose4j.keys.HmacKey;

@RestController
public class HelloWorldController {
	
	@GetMapping("/jwt")
	public String hello()
	{
		return "hello" ;
	}
	
	@PostMapping("/attributes")
	public void attributes()
	{
	
		
		// ajouter le traitement qui genere le jwt token a partir des valeurs recuperer
		}
	
	@PostMapping("/jwths256")
	public void JWS_HS256(@RequestBody Jwtattributs j) throws Exception {  
		System.out.println(j.getSecret());
		//System.out.println(j.getAlg());

		System.out.println(j.getUsername());
		System.out.println(j.getKey());
	    
		String command ="curl -X GET http://localhost:8001/consumers/douda/jwt";
				ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
		
		  // generate  key  
		  String secret = j.getSecret();
		  HmacKey key = new HmacKey(secret.getBytes("UTF-8"));
		  
		
		  JwtClaims jwtClaims = new JwtClaims();  
		//  jwtClaims.setSubject("7560755e-f45d-4ebb-a098-b8971c02ebef"); // set sub  
		  jwtClaims.setIssuedAtToNow();  // set iat  
		  jwtClaims.setExpirationTimeMinutesInTheFuture(10080); // set exp  
		  jwtClaims.setIssuer(j.getKey()); // set iss  
		  jwtClaims.setStringClaim("name", j.getUsername());   // set name  
		  
		  JsonWebSignature jws = new JsonWebSignature();  
		  // Set alg header as HMAC_SHA256  
		  jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA256);
		  jws.setKey(key);
		  jws.setPayload(jwtClaims.toJson());  
		  
		  String jwt = jws.getCompactSerialization(); //produce eyJ.. JWT  
		  
		  // we don't need NO_CONSTRAINT and disable require signature anymore 
		  JwtConsumer jwtConsumer = new JwtConsumerBuilder()  
		          .setRequireIssuedAt()  
		          .setRequireExpirationTime()  
		          .setExpectedIssuer(j.getKey())  
		          // set the verification key  
		          .setVerificationKey(key)  
		          .build();  
		  
		  
		  JwtContext jwtContext = jwtConsumer.process(jwt);  
		  // get JWS object  
		  JsonWebSignature consumedJWS = (JsonWebSignature)jwtContext.getJoseObjects().get(0);  
		  // get claims  
		  JwtClaims consumedJWTClaims = jwtContext.getJwtClaims();  
		  
		  // print claims as map  
		  System.out.println(consumedJWTClaims.getClaimsMap());  
		  
		  System.out.println(consumedJWTClaims.toJson());  
		  System.out.println(jws.getKey());  
		  System.out.println(jws.getCompactSerialization());
		
		}

}
