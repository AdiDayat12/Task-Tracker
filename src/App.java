import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class App {
    Scanner s = new Scanner(System.in);
    TaskManager taskManager = TaskManager.getInstance();
    private static int idCounter = 1;
    Path path = Paths.get("Task Tracker");

    public App() throws IOException {
    }

    public void run () throws IOException {
        FileWriter fileWriter = new FileWriter("Task Tracker");
        fileWriter.write("[]");
        fileWriter.close();
        while (true){
            System.out.println("Options:\nAdd\nUpdate\nDelete");
            System.out.println("List To Do\nList In Progress\nList Done\nHelp (-h)\nExit");
            System.out.println("Choose Option\n");

            String opt = s.nextLine();
            String [] input = opt.split(" ");
            int len = input.length;

            if (input[0].equalsIgnoreCase("add")){
                StringBuilder builder = new StringBuilder("");
                for (int i = 1; i < len; i++) {
                    builder.append(input[i]);
                    builder.append(" ");
                }
                String newTask = builder.toString().trim();
                Task task = new Task(newTask);
                taskManager.addNewTask(task);
                System.out.println("Task added successfully (ID: " + task.getId() + ")");
            } else if (input[0].equalsIgnoreCase("update")) {
                int id = Integer.valueOf(input[1]);
                StringBuilder builder = new StringBuilder("");
                for (int i = 2; i < len; i++) {
                    builder.append(input[i]);
                    builder.append(" ");
                }
                String newTask = builder.toString().replaceAll("\"", "").trim();
                taskManager.update(id, newTask);
            } else if (input[0].equalsIgnoreCase("delete")) {
                int id = Integer.valueOf(input[1]);
                taskManager.delete(id);
            } else if (input[0].equalsIgnoreCase("mark-in-progress")) {
                int id = Integer.valueOf(input[1]);
                taskManager.markInProgress(id);
            } else if (input[0].equalsIgnoreCase("mark-done")) {
                System.out.println(opt);
                int id = Integer.valueOf(input[1]);
                taskManager.markDone(id);
            } else if (len >= 2 && input[0].equalsIgnoreCase("list") && input[1].equalsIgnoreCase("todo")) {
                taskManager.displayToDo();
            } else if (len >= 2 && input[0].equalsIgnoreCase("list") && input[1].equalsIgnoreCase("in-progress")) {
                taskManager.displayInProgress();
            } else if (len >= 2 && input[0].equalsIgnoreCase("list") && input[1].equalsIgnoreCase("done")) {
                taskManager.displayDone();
            } else if (input[0].equalsIgnoreCase("list")) {
                taskManager.displayAllTask();
            } else if (input[0].equalsIgnoreCase("-h")) {
                displayHelp();
            } else if (input[0].equalsIgnoreCase("exit")){
                return;
            } else {
                System.out.println("Invalid command!!");
            }

        }
    }

    static void displayHelp (){
        System.out.println("---Commands---");
        System.out.println("add task");
        System.out.println("update id \"new task\"");
        System.out.println("delete id");
        System.out.println("mark-in-progress id");
        System.out.println("mark-done id");
        System.out.println("list");
        System.out.println("list todo");
        System.out.println("list in-progress");
        System.out.println("list done");
        System.out.println("exit");
        System.out.println("-".repeat(24));
        System.out.println();
    }

    public String toJson (int id, String task, String status){
        return String.format("{\\\"id\\\":%d,\\\"task\\\":\\\"%s\\\",\\\"status\\\":\\\"%s\\\"}", id, task, status);
    }
}
