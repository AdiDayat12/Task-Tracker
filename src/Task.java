import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
    private int id;
    private String task;
    private Status status;
    private TaskManager taskManager = TaskManager.getInstance();
    private static int idCounter = 1;
    private LocalDateTime timeCreated;
    private LocalDateTime timeUpdated;

    public Task (String task) {
        this.id = idCounter++;
        this.task = task;
        this.status = Status.TODO;
        this.timeCreated = LocalDateTime.now();
        this.timeUpdated = LocalDateTime.now();
    }

    public Task(int id, String task, Status status, LocalDateTime timeCreated, LocalDateTime timeUpdated) {
        this.id = id;
        this.task = task;
        this.status = status;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
    }

    public String toJson (){
        return String.format("\t{\n\t\t\"id\": %s," +
                "\n\t\t\"task\": \"%s\"," +
                "\n\t\t\"status\": \"%s\"," +
                "\n\t\t\"created at\": %s," +
                "\n\t\t\"updated at\": %s" +
                "\n\t}", String.valueOf(this.getId()),this.getTask(),
                this.getStatus(), this.getTimeCreated(),
                this.getTimeUpdated());
    }



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

        return status.statusToString();
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getTimeCreated() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = timeCreated.format(formatter);
        return formattedDate;
    }


    public String getTimeUpdated() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return this.timeUpdated.format(formatter);
    }

    public void setTimeUpdated(LocalDateTime timeUpdated) {
        this.timeUpdated = timeUpdated;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", task=\"" + task + '\"' +
                ", status=" + status +
                ", timeCreated=" + timeCreated +
                ", timeUpdated=" + timeUpdated +
                '}';
    }
}
