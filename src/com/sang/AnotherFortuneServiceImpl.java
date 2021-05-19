package com.sang;

import org.springframework.stereotype.Component;

@Component
public class AnotherFortuneServiceImpl implements FortuneService {
    @Override
    public String getFortune() {
        return "Fortune injected by AnotherFortuneServiceImpl";
    }
}
