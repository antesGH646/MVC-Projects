package com.cydeo.controller;

import com.cydeo.dto.TaskDTO;
import com.cydeo.enums.Status;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;
    private final ProjectService projectService;
    private final UserService userService;

    public TaskController(TaskService taskService, ProjectService projectService,
                          UserService userService) {
        this.taskService = taskService;
        this.projectService = projectService;
        this.userService = userService;
    }

    @GetMapping("/create")
    public String createTask(Model model) {
        model.addAttribute("task", new TaskDTO());
        model.addAttribute("projects", projectService.findAll());
        model.addAttribute("employees", userService.findEmployees());
        model.addAttribute("tasks", taskService.findAll());
        return "task/create";
    }

    @PostMapping("/create")
    public String insertTask(TaskDTO task) {
        taskService.save(task);
        return "redirect:/task/create";
    }

    @GetMapping("/delete/{taskId}")
    public String deleteTask(@PathVariable("taskId") Long taskId) {
        taskService.deleteById(taskId);
        return "redirect:/task/create";
    }

    /**
     * This method is used to display data from the table and display them on the UI after a
     * user clicks the Update button. The model attributes carries the data of the Java objects,
     * and the Thymeleaf displays them on the UI/form. After the User clicks the Save button,
     * the updated data should populate again in the table.
     * To update, you need two things. First, to display the Java objects into the UI form.
     * For this, you need a Get mapping. Second, you need to grab the user entries and populate
     * them or capture & display them into the table. For this, you need the Post mapping.
     * When using path parameter, must bind it using the @PathVariable(path-param)
     * @param taskId Long
     * @return create html under the task folder
     */
    @GetMapping("/update/{taskId}")
    public String editTask(@PathVariable("taskId") Long taskId, Model model) {

        model.addAttribute("task", taskService.findById(taskId));
        model.addAttribute("projects", projectService.findAll());
        model.addAttribute("employees", userService.findEmployees());
        model.addAttribute("tasks", taskService.findAll());
        return "task/update";
    }

    /**
     * This method is used to populate the UI captured objects into the table.
     * When using path parameter should bind it using the @PathVariable()
     * @param task TaskDTO
     * @return create html inside the task package
     */
//    @PostMapping("/update/{taskId}") //capture it the id from the UI
//    public String updateTask(@PathVariable("taskId") Long taskId, TaskDTO task) {
//        task.setId(taskId);//assign the UI captured id
//        //update the task object coming from model.addAttribute("task", taskService.findById(taskId))
//        taskService.update(task);
//        //redirecting to the model attributes and returning the create.html
//        return "redirect:/task/create";
//    }

    /**
     *  This method shows the easiest way to update and populate the update.
     *  This is the power of Spring, if the {id} is parts of the fields, Spring
     *  automatically binds the setter. But if you are using other than the field
     *  inside the TaskDTO, you need to assign or set it manually.
     */
    @PostMapping("/update/{id}")
    public String updateTask(TaskDTO task) {
        taskService.update(task);
        return "redirect:/task/create";
    }

    @GetMapping("/employee/pending-tasks")
    public String employeePendingTasks(Model model) {
        model.addAttribute("tasks", taskService.findAllTasksByStatusIsNot(Status.COMPLETE));
        return "task/pending-tasks";
    }

    @GetMapping("/employee/edit/{id}")
    public String employeeEditTask(@PathVariable("id") Long id, Model model) {

        model.addAttribute("task", taskService.findById(id));
        model.addAttribute("employees", userService.findEmployees());
        model.addAttribute("projects", projectService.findAllNonCompletedProjects());
        model.addAttribute("tasks", taskService.findAllTasksByStatusIsNot(Status.COMPLETE));
        model.addAttribute("statuses", Status.values());
        return "task/status-update";
    }

    @PostMapping("/employee/update/{id}")
    public String employeeUpdateTask(TaskDTO taskDTO) {
        taskService.updateStatus(taskDTO);
        return "redirect:/task/employee/pending-tasks";
    }

    @GetMapping("/employee/archive")
    public String employeeArchivedTasks(Model model) {
        model.addAttribute("tasks", taskService.findAllTasksByStatus(Status.COMPLETE));
        return "task/archive";
    }
}
