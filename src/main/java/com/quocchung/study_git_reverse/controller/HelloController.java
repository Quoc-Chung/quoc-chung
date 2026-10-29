package com.quocchung.study_git_reverse.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hello")
public class HelloController {
   @GetMapping("/name")
   public String getHello(@RequestParam(required = false) String  name){
     return  "Xin chao"   + name;
   }
}
