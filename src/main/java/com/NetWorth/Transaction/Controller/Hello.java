package com.NetWorth.Transaction.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class Hello {
    @GetMapping("/case")
    public static String vo(){
        return "Hello World!";
    }

}
