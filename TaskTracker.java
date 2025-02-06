import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskTracker {
    private List<Task> taskList;

    public TaskTracker(){
        String filename = "tasks"; 
        if (!Files.exists(Paths.get(filename))) 
            this.taskList = new ArrayList<>();
        else  // Extract the data from the JSON file to the taskList
            this.taskList = loadFromFile(filename);
    }

    // Load tasks from a JSON file to a taskList
    public List<Task> loadFromFile(String filename) {
        List<Task> tasks = new ArrayList<>();
        try {
            String json = new String(Files.readAllBytes(Paths.get(filename))).trim();

            if (json.startsWith("[") && json.endsWith("]")) {
                json = json.substring(1, json.length() - 1).trim();
            }
            if (json.isEmpty()) 
                return tasks;

            // Split into individual task objects
            String[] taskArray = json.split("\\},\\s*\\{");  
       
            for (String task : taskArray) {
                // Reformat each task string to be valid JSON-like
                task = task.trim();
                if (!task.startsWith("{")) task = "{" + task;
                if (!task.endsWith("}")) task = task + "}";

                // Extract fields manually
                int id = extractInt(task, "\"id\":");
                String description = extractString(task, "\"description\":");
                String status = extractString(task, "\"status\":");
                LocalDateTime createdAt = extractDateTime(task, "\"createdAt\":");
                LocalDateTime updatedAt = extractDateTime(task, "\"updatedAt\":");

                tasks.add(new Task(id, description, status, createdAt, updatedAt));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tasks;
    }

    // Extract an integer value from a JSON-like string
    private int extractInt(String json, String key) {
        try {
            String value = json.split(key)[1].split(",")[0].trim();
            return Integer.parseInt(value.replaceAll("[^0-9]", ""));
        } catch (Exception e) {
            return 0; // Default ID if not found
        }
    }

    // Extract a string value from a JSON-like string
    private String extractString(String json, String key) {
        try {
            String value = json.split(key)[1].split(",")[0].trim();
            return value.replaceAll("^\"|\"$", ""); // Remove surrounding quotes
        } catch (Exception e) {
            return "";
        }
    }

    // Extract a LocalDateTime value from a JSON-like string
    private LocalDateTime extractDateTime(String json, String key) {
        try {
            String value = json.split(key)[1].split(",")[0].trim();
            value = value.replaceAll("^\"|\"$", ""); // Remove quotes
            return LocalDateTime.parse(value); // Convert to LocalDateTime
        } catch (Exception e) {
            return LocalDateTime.now(); // Default to current time if error
        }
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
                System.out.println("ID: " + task.getId() + ", Description: \"" + task.getDescription() + "\", Status: " + task.getStatus());
            else if(status.equals("all"))
                System.out.println("ID: " + task.getId() + ", Description: \"" + task.getDescription() + "\", Status: " + task.getStatus());
        }  
    }
}
