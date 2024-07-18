package com.example.tdspring.models;

/*
* An Action is an operation done by a User on a Stock : Check or History
* */
public interface Action {
    Stock getStock();
    String getType();
}
