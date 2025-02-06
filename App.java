import java.io.IOException;
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
                        String task = scanner.nextLine().trim();
                        if (!task.matches("^\".*\"$"))
                            throw new IOException();
                        task = task.replaceAll("^\"|\"$", "");
                        taskTracker.add(task); 
                    }
                    case "update" -> {
                        int id = Integer.parseInt(scanner.next());
                        String task = scanner.nextLine().trim();
                        if (!task.matches("^\".*\"$"))
                            throw new IOException();
                        task = task.replaceAll("^\"|\"$", "");
                        taskTracker.update(id, task);
                    }
                    case "delete" -> {
                        String input = scanner.nextLine().trim();
                        if (input.isEmpty())
                            throw new IOException();
                        int id = Integer.parseInt(input);
                        taskTracker.delete(id);   
                    }
                    case "mark-in-progress" -> {
                        String input = scanner.nextLine().trim();
                        if (input.isEmpty())
                            throw new IOException();
                        int id = Integer.parseInt(input);
                        taskTracker.mark_in_progess(id);  
                    }
                    case "mark-done" -> {
                        String input = scanner.nextLine().trim();
                        if (input.isEmpty())
                            throw new IOException();
                        int id = Integer.parseInt(input);
                        taskTracker.mark_done(id);
                    }
                    case "list" -> {
                        String input = scanner.nextLine().trim();
                        if (input.isEmpty()) 
                            taskTracker.list("all");
                        else if(input.equals("todo") || input.equals("done") || input.equals("in-progress"))
                            taskTracker.list(input);
                        else
                            throw new IOException();
                    }
                    default -> {
                        throw new IOException();
                    }
                }
            } catch (Exception e) {
                scanner.nextLine();
                System.err.println("Invalid input");
            }   
        }  
    }
}
