import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
    private int id;
    private String task;
    private Status status;
    private TaskManager taskManager = TaskManager.getInstance();
    private static int idCounter = 1;
    LocalDateTime timeCreated;

    public Task (String task) throws IOException {
        this.id = idCounter++;
        this.task = task;
        this.status = Status.TODO;
        taskManager.addAll(this);
        this.timeCreated = LocalDateTime.now();
    }

    public String toJson (){
        return String.format("\t{\n\t\t\"id\": %s,\n\t\t\"task\": \"%s\",\n\t\t\"status\": \"%s\",\n\t\t\"created at\": %s\n\t}", this.getId(),this.getTask(), this.getStatus(), this.getTimeCreated());
    }

//    public Task formJson (String json){
//        int id = Integer.parseInt(json.split("\"id\":")[1].trim().split(",")[0]);
//        String task = json.split("\"status\":")[1].
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getStatus() {

        return status.getStatus();
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getTimeCreated() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = timeCreated.format(formatter);
        return formattedDate;
    }

    public void setTimeCreated(LocalDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }
}
