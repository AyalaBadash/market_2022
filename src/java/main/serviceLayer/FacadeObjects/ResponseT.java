package main.serviceLayer.FacadeObjects;

public class ResponseT<T> extends Response {
    private T value;

    public ResponseT(String message){
        super(message);
    }
    public ResponseT(T value){
        this.value = value;
    }
    public T getValue() {
        return value;
    }
}
