import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        Path filePath = Paths.get("Task Tracker");

        try{
            String content = "";
            Files.write(filePath, content.getBytes(), StandardOpenOption.CREATE);
            System.out.println("File created");
        } catch (IOException e){
            e.printStackTrace();
        }

        App app = new App();
        app.run();







    }
}