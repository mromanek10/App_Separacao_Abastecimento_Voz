package com.example.FactoryJSONRequest;

public interface SendRequest<T> {
    void create(String mJSONURLString);
    void sendRequest(String mJSONURLString);
}
