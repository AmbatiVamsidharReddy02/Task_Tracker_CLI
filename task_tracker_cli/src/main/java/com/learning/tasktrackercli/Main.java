package com.learning.tasktrackercli;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Scanner;

import com.learning.tasktrackercli.Entity.Task;
import com.learning.tasktrackercli.Enums.Status;
import com.learning.tasktrackercli.Services.TaskManager;

public class Main {
    public static void main(String[] args) {

        TaskManager taskManager = new TaskManager();
        Scanner sc = new Scanner(System.in);

        try {
            while (true) {

                // work on the UI and ask for command.
                System.out.println();
                displayOptions();
                System.out.print("Please Enter Your Choice:- ");
                String command = sc.nextLine().trim();
                if (command.equals("exit")) {
                    System.out.println("Exiting the CLI.....");
                    break;
                }
                // split the command by " " -> whitespace
                String[] requestData = command.split(" ");

                switch (requestData.length) {
                    case 0:
                        noProperInput();
                        break;
                    default:
                        multiArgumentRequestProcessing(requestData, taskManager);
                        break;
                }

            }
        } catch (Exception e) {
            System.out.println("Exception occured, Error:- " + e.getMessage());
        } finally {
            sc.close();
        }

    }

    private static void noProperInput() {
        System.out.println("Cannot find appropriate action. Please check the command again");
    }

    // Note:- This is crude way of writing code. this could be improved to
    // modularization. Could improve it in future.
    private static void multiArgumentRequestProcessing(String[] request, TaskManager taskManager) {
        String mainCommand = request[0].trim();
        if (request.length == 1 && mainCommand.equals("list")) {
            // this could be list command
            taskManager.getAllTasks().forEach((t) -> {
                System.out.println(t);
            });
        } else if (request.length == 2) {
            if (mainCommand.equals("mark-in-progress") || mainCommand.equals("mark-done")) {
                int id = Integer.parseInt(request[1].trim());
                Task task = taskManager.getTaskById(id);
                if (task != null) {
                    if (mainCommand.equals("mark-in-progress")) {
                        task.setStatus(Status.INPROGRESS);
                    }
                    if (mainCommand.equals("mark-done")) {
                        task.setStatus(Status.DONE);
                    }
                    task.setUpdatedAt(LocalDate.now());
                    taskManager.update(task);
                    System.out.println("Task updated to " + mainCommand);
                } else {
                    System.out.println("Cannot find the Task with id:-  " + id);
                }
            } else if (mainCommand.equals("list")) {
                String arg = request[1].trim();
                if (arg.equals("done")) {
                    taskManager.getTasksByStatus(Status.DONE).forEach((t) -> {
                        System.out.println(t);
                    });
                } else if (arg.equals("inprogress")) {
                    taskManager.getTasksByStatus(Status.INPROGRESS).forEach((t) -> {
                        System.out.println(t);
                    });
                } else if (arg.equals(("todo"))) {
                    taskManager.getTasksByStatus(Status.TODO).forEach((t) -> {
                        System.out.println(t);
                    });
                } else {
                    noProperInput();
                }
            } else if (mainCommand.equals("add")) {
                Task task = new Task(String.join(" ", Arrays.copyOfRange(request, 1, request.length)), Status.TODO);
                taskManager.add(task);
                System.out.println("Successfully added Task with taskDescription " + task.getDescription()
                        + " taskId:- " + task.getTaskId());
            } else if (mainCommand.equals("delete")) {
                int id = Integer.parseInt(request[1].trim());
                Task task = taskManager.getTaskById(id);
                if (task != null) {
                    taskManager.delete(id);
                    System.out.println("Sucessfully Deleted task with taskId:- " + id);
                } else {
                    System.out.println("No task found to Delete with Task ID:- " + id);
                }
            } else {
                noProperInput();
            }
        } else if (request.length == 3) {
            if (mainCommand.equals("update")) {
                int id = Integer.parseInt(request[1].trim());
                Task task = taskManager.getTaskById(id);

                if (task != null) {
                    task.setDescription(String.join(" ", Arrays.copyOfRange(request, 2, request.length)));
                    taskManager.update(task);
                    System.out.println("Successfully updated Task with taskId:- " + task.getTaskId());
                } else {
                    System.out.println("No Task found to update with taskId:- " + id);
                }
            } else {
                noProperInput();
            }
        } else {
            noProperInput();
        }
    }

    private static void displayOptions() {
        System.out.println("""
                Add Task -> add <taskDescription>
                Update Task -> update <taskId> <updatedTaskDescription>
                Delete Task -> delete <taskId>
                Mark a task as in progress -> mark-in-progress <taskId>
                Mark a task as in done -> mark-done <taskId>
                List all tasks -> list
                List all tasks that are done -> list done
                List all tasks that are todo -> list todo
                List all tasks that are in progress -> list inprogress
                Exit -> exit
                """);
    }
}