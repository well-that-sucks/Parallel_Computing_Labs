class DimensionsNotMatchingException extends Exception {
    private static final long serialVersionUID = 1L;

    public DimensionsNotMatchingException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return ("DimensionsNotMatchingExcpetion: " + getMessage());
    }
}
