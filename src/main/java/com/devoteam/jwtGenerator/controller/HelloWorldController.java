package com.devoteam.jwtGenerator.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import model.Jwtattributs;

@RestController
public class HelloWorldController {
	
	@GetMapping("/jwt")
	public String hello()
	{
		return "hello" ;
	}
	
	@PostMapping("/attributes")
	public void attributes(@RequestBody Jwtattributs j)
	{
		System.out.println(j.getSecret());
		System.out.println(j.getAlg());

		System.out.println(j.getUsername());
		System.out.println(j.getKey());
		
		// ajouter le traitement qui genere le jwt token a partir des valeurs recuperer
		}

}
