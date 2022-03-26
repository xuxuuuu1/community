package com.xx.community.dao;


import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class AlphaImpl implements AlphaDao{
    @Override
    public String select() {
        return "mysql";
    }
}
