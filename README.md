# TaskTracker

TaskTracker is a simple command-line application designed to help you track and manage your tasks effectively. With this tool, you can add, update, delete, and mark tasks with various statuses. Whether you are managing work, study, or personal tasks, TaskTracker provides an easy-to-use interface for task management.

## Features

- Add new tasks
- Update and delete tasks
- Mark tasks as in-progress or done
- List all tasks or filter by their status

## Commands

### 1. Adding a new task
To add a new task, use the `add` command followed by the task description.

```
add "Buy groceries"
```

### 2. Updating a task
To update an existing task, use the `update` command with the task ID and the new task description.

```
update 1 "Buy groceries and cook dinner"
```

### 3. Deleting a task
To delete a task, use the `delete` command followed by the task ID.

```
delete 1
```

### 4. Marking a task as in-progress or done
To mark a task as "in-progress," use the `mark-in-progress` command with the task ID.

```
mark-in-progress 1
```

To mark a task as "done," use the `mark-done` command with the task ID.

```
mark-done 1
```

### 5. Listing tasks
To list all tasks, simply use the `list` command.

```
list
```

### 6. Listing tasks by status
You can also filter tasks by their status:

- List all tasks marked as done:

  ```
  list done
  ```

- List all tasks marked as todo (not started):

  ```
  list todo
  ```

- List all tasks marked as in-progress:

  ```
  list in-progress
  ```

## Compilation and Execution

To compile and run the application, follow these steps:

1. **Compile the Java file:**

   ```
   javac App.java
   ```

2. **Run the application:**

   ```
   java App
   ```

