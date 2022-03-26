package com.xx.community.dao;


import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
public class Alpha02Impl implements AlphaDao{
    @Override
    public String select() {
        return "另一个数据库";
    }
}
