package test.com.farabiapp.api;

/**
 * Created by sathya on 24/08/16.
 */
public class APIException extends Exception {

    private static final long serialVersionUID = 5562656992588643121L;

    private String errorMessage;
    private int type;

    public APIException(String errorMessage, int type) {
        this.errorMessage = errorMessage;
        this.type = type;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
