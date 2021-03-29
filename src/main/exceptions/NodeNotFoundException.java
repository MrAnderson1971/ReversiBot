package exceptions;

/*
Exception for when the tree tries to access a non-existant node.
 */
public class NodeNotFoundException extends Exception {

    /*
    EFFECTS: instantiates new instance of this with message.
     */
    public NodeNotFoundException(String message) {
        super(message);
    }
}
