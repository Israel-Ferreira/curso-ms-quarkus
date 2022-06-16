package io.codekaffee.ifood.cadastro.dto;

public class ErrorDTO {
    private String errMsg;
    private Integer statusCode;

    

    public ErrorDTO() {
    }

    public ErrorDTO(String errMsg, Integer statusCode) {
        this.errMsg = errMsg;
        this.statusCode = statusCode;
    }
    
    public String getErrMsg() {
        return errMsg;
    }
    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
    public Integer getStatusCode() {
        return statusCode;
    }
    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    

}
