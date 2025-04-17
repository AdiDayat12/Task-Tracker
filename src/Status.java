public enum Status {
    TODO("Todo"),
    INPROGRESS("In Progress"),
    DONE("Done");

    private final String status;
    Status(String status) {
        this.status = status;
    }

    public String statusToString() {
        return status;
    }

    public Status stringToStatus (String status){
        if (status.equalsIgnoreCase("todo")){
            return TODO;
        } else if (status.equalsIgnoreCase("in progress")) {
            return INPROGRESS;
        } else {
            return DONE;
        }
    }
}
