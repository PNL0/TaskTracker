import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        TaskTracker taskTracker = new TaskTracker();
        Scanner scanner = new Scanner(System.in);
        while(true){
            if(!scanner.hasNext())
                continue;
            String command = scanner.next();
            try {
                switch (command) {
                    case "add" -> {
                        String task = scanner.nextLine();
                        task = task.trim(); 
                        task = task.replaceAll("^\"|\"$", "");
                        taskTracker.add(task); 
                    }
                    case "update" -> {
                        int id = scanner.nextInt();
                        String task = scanner.nextLine();
                        task = task.trim(); 
                        task = task.replaceAll("^\"|\"$", "");
                        taskTracker.update(id, task);
                    }
                    case "delete" -> {
                        int id = scanner.nextInt();
                        taskTracker.delete(id); 
                    }
                    case "mark-in-progress" -> {
                        int id = scanner.nextInt();
                        taskTracker.mark_in_progess(id);  
                    }
                    case "mark-done" -> {
                        int id = scanner.nextInt();
                        taskTracker.mark_done(id);
                    }
                    case "list" -> {
                        if(!scanner.hasNext())
                            taskTracker.list("all");
                        else {
                            String status = scanner.next();
                            taskTracker.list(status);
                        }
                    }
                    default -> {
                        System.err.println("Invalid. Available actions: add, update, delete, mark and list");
                    }
                }
            } catch (Exception e) {
                System.err.println("Invalid input");
            }   
        }  
    }
}
