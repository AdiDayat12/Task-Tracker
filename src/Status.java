public enum Status {
    TODO("Todo"),
    INPROGRESS("Done"),
    DONE("Done");

    private final String status;
    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
