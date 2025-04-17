import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskManager{
    private static TaskManager instance;
    private List<Task> listAll = new ArrayList<>();
    private List<Task> toDoList = new ArrayList<>();
    private List<Task> inProgressList = new ArrayList<>();
    private List<Task> doneList = new ArrayList<>();
    private Status status = Status.TODO;




    public static TaskManager getInstance ()  {
        if (instance == null){
            instance = new TaskManager();
        }
        return instance;
    }

    //Insert task
    public void addNewTask (Task task){
        Path path = Paths.get("Task Tracker");
        try {
            String content = new String(Files.readAllBytes(path));
            String newTask = task.toJson();
            if (content.equals("[]") || content.length() == 0){
                content = "[\n" + newTask + "\n]";
            } else {
                String values = content.replace("[", "").replace("]","");
                if (!values.substring(values.length()).equals(",")){
                    values = values.substring(0, values.length() - 1) + ",";
                }

                content = "[" + values + "\n" + newTask + "\n]";
            }

            Files.write(path, content.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void addAllTask (List<Task> taskList){
        for (Task task : taskList){
            addNewTask(task);
        }
    }


    //Get values from json file
    public String[] valuesFromJson (){
        Path path = Paths.get("Task Tracker");
        String content;
        try {
            content = new String(Files.readAllBytes(path));
            content = content.substring(2, content.length() - 5)
                    .replace("\t{\n\t", "")
                    .replaceAll("\t", "")
                    .replaceAll("\"", "");
        } catch (IOException e){
            System.out.println("File is not found");
            return new String[0];
        }

        return  content.split("},");
    }

    //Convert values to task
    private Task convertToTask (String [] json){
        String stringId = json[0].substring(4, json[0].length()).trim();
        int id = Integer.parseInt(stringId); // 5 is index of id's
        String task = json[1].substring(6, json[1].length());
        status = status.stringToStatus(json[2].substring(8, json[2].length()));
        LocalDateTime createdAt = convertToTime(json[3].substring(12, json[3].length()));
        LocalDateTime updatedAt = convertToTime(json[4].substring(12, json[4].length()).trim());
        return new Task(id, task, status, createdAt, updatedAt);
    }

    private LocalDateTime convertToTime (String time){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return LocalDateTime.parse(time, formatter);
    }

    //Get All task
    public List<Task> getAllTask (){
        List<Task> allTask = new ArrayList<>();
        String [] values = valuesFromJson();

        for (String v : values){
            allTask.add(convertToTask(v.split(",\n")));
        }
        return allTask;
    }

    

    //Update task
    public void update (int id, String task) throws IOException {
        List<Task> taskList = getAllTask();
        Task existingTask = null;
        for (Task t : taskList){
            if (id == t.getId()){
                existingTask = t;
            }
        }
        if (existingTask == null){
            System.out.println("file not found");
            return;
        }
        existingTask.setTask(task);
        existingTask.setTimeUpdated(LocalDateTime.now());

        FileWriter fileWriter = new FileWriter("Task Tracker");
        fileWriter.write("[]");
        fileWriter.close();

        addAllTask(taskList);
    }


    //Delete task
    public void delete (int id) throws FileNotFoundException {
        List<Task> taskList = getAllTask();
        for (Task t : taskList){
            if (t.getId() == id){
                taskList.remove(t);
            }
        }

        PrintWriter printWriter = new PrintWriter("Task Tracker");
        printWriter.close();

        addAllTask(taskList);

    }

    //Update status
    private void updateStatus (int id, Status status) throws FileNotFoundException {
        List<Task> taskList = getAllTask();
        for (Task t : taskList){
            if (t.getId() == id){
                t.setStatus(status);
            }
        }
        PrintWriter printWriter = new PrintWriter("Task Tracker");
        printWriter.close();
        addAllTask(taskList);
    }


    //Mark in-progress
    public void markInProgress (int id) throws FileNotFoundException {
        updateStatus(id, Status.INPROGRESS);
    }

    //Mark done
    public void markDone (int id) throws FileNotFoundException {
        updateStatus(id, Status.DONE);
    }

    //Display all task
    public void displayAllTask (){
        List<Task> taskList = getAllTask();
        for (Task t : taskList){
            System.out.println("Id: " + t.getId() + " Task: " + t.getTask() + " Status: " + t.getStatus());
        }
    }

    //Display todo
    public void displayToDo (){
        List<Task> taskList = getAllTask();
        for (Task t : taskList){
            if (t.getStatus().equals("Todo")){
                System.out.println("Id: " + t.getId() + " Task: " + t.getTask());
            }
        }
    }

    //Display in-progress
    public void displayInProgress (){
        List<Task> taskList = getAllTask();
        for (Task t : taskList){
            if (t.getStatus().equals("In Progress")){
                System.out.println("Id: " + t.getId() + " Task: " + t.getTask());
            }
        }
    }

    //Display done
    public void displayDone (){
        List<Task> taskList = getAllTask();
        for (Task t : taskList){
            if (t.getStatus().equals("Done")){
                System.out.println("Id: " + t.getId() + " Task: " + t.getTask());
            }
        }
    }
}
