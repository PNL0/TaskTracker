import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskTracker {
    private List<Task> taskList;

    public TaskTracker(){
        this.taskList = new ArrayList<>();
    }

    // Convert taskList to JSON format manually
    public String toJson() {
        StringBuilder json = new StringBuilder();
        json.append("[\n");
        int size = taskList.size();
        for (int i = 0; i < size; i++) {
            Task task = taskList.get(i);
            json.append("  {")
                .append("\"id\": ").append(task.getId()).append(", ")
                .append("\"description\": \"").append(task.getDescription()).append("\", ")
                .append("\"status\": \"").append(task.getStatus()).append("\", ")
                .append("\"createdAt\": \"").append(task.getCreatedAt()).append("\", ")
                .append("\"updatedAt\": \"").append(task.getUpdatedAt()).append("\"")
                .append("}");
            if (i < taskList.size() - 1) {
                json.append(",");
            }
            json.append("\n");
        }
        json.append("]");
        return json.toString();
    }

    // Save JSON data to a file
    public void saveToFile(String filename) {
        try (FileWriter file = new FileWriter(filename)) {
            file.write(toJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void add(String task) {
        int id = this.taskList.size() + 1;
        String status = "todo";
        LocalDateTime createdAt = LocalDateTime.now();
        taskList.add(new Task(id, task, status, createdAt, createdAt));
        saveToFile("tasks");
    }

    public void update(int id, String task) {
        taskList.get(id-1).setDescription(task);
        taskList.get(id-1).setUpdatedAt(LocalDateTime.now());
        saveToFile("tasks");
    }

    public void delete(int id) {
        taskList.remove(id-1);
        int size = taskList.size();
        for(int i = id-1; i < size; i++){
            taskList.get(i).setId(i+1);
        }
        saveToFile("tasks");
    }

    public void mark_in_progess(int id) {
        taskList.get(id-1).setStatus("in-progress");
        saveToFile("tasks");
    }

    public void mark_done(int id) {
        taskList.get(id-1).setStatus("done");
        saveToFile("tasks");
    }

    public void list(String status) {
        int size = taskList.size();
        for (int i = 0; i < size; i++) {
            Task task = taskList.get(i);
            if(task.getStatus().equals(status))
                System.out.println(task.getId() + " - " + task.getDescription());
            else if(status.equals("all"))
               System.out.println(task.getId() + " - " + task.getDescription());
        }  
    }
}
