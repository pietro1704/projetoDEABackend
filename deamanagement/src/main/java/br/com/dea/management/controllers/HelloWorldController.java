package br.com.dea.management.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @RequestMapping(value = "/hello-world", method = RequestMethod.GET)
    public ResponseEntity<String> getFactory() {
        return ResponseEntity.ok("hello world");
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<String> oNomeNaoImporta() {
        return ResponseEntity.ok("<h1> TÍTULO HOME</h1>");
    }

}