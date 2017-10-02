package be.maximvdw.placeholderapi.internal.exceptions;

/**
 * PlaceholderPack empty exception
 */
public class PlaceholderEmptyException extends Exception {
    private static final long serialVersionUID = -5158721483958345495L;
    private String returnResult = "";

    public PlaceholderEmptyException(String result) {
        this.returnResult = result;
    }

    public String getReturnResult() {
        return returnResult;
    }
}
