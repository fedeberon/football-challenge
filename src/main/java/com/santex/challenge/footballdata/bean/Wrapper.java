package com.santex.challenge.footballdata.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by federicoberon on 02/07/2019.
 */

@Component
public class Wrapper<T> {

    public static String LEAGUE_ALREADY_IMPORTED = "League already imported";
    public static String NOT_FOUND = "Not found";
    public static String SUCCESSFULLY_IMPORTED = "Successfully imported";

    @JsonIgnore
    private T t;

    @JsonIgnore
    private List<T> list;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String total;

    @JsonIgnore
    private HttpStatus status;

    public Wrapper() {}

    public Wrapper(T t, HttpStatus status, String message) {
        this.t = t;
        this.status = status;
        this.message = message;

    }

    public Wrapper(List<T> list, HttpStatus status, String message) {
        this.list = list;
        this.status = status;
        this.message = message;
    }

    public Wrapper(List<T> list, HttpStatus status) {
        this.list = list;
        this.status = status;
    }

    public Wrapper(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public Wrapper(String message, String total, HttpStatus status) {
        this.message = message;
        this.status = status;
        this.total = total;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Wrapper(List<T> list) {
        this.list = list;
    }

    public Wrapper(T t) {
        this.t = t;
    }

    public Wrapper(HttpStatus status) {
        this.status = status;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
