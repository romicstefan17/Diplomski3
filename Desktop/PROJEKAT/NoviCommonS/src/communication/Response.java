/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

import java.io.Serializable;

public class Response implements Serializable {

    private Object result;
    private Exception exception;
    private int operation;
    private String poruka;

    public Response() {

    }

    public Response(Object result, Exception exception, int operation, String poruka) {
        this.result = result;
        this.exception = exception;
        this.operation = operation;
        this.poruka = poruka;
    }

    public Exception getException() {
        return exception;
    }

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public int getOperation() {
        return operation;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }

}
