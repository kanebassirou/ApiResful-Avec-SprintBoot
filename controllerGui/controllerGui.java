package com.uidt.ApiResful.controllerGui;

import com.uidt.ApiResful.model.Employee;
import com.uidt.ApiResful.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
@RequestMapping("/employeeView")
public class controllerGui {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public String getAllEmployees(Model model) {
        model.addAttribute("employees", employeeService.getEmployees());
        model.addAttribute("employee", new Employee());
        return "employeeList";
    }

    @PostMapping
    public String createEmployee(Employee employee) {
        employeeService.saveEmployee(employee);
        return "redirect:/employeeView";
    }
    
    @GetMapping("/edit/{id}")
    public String editEmployee(@PathVariable Long id, Model model) {
        Optional<Employee> employee = employeeService.getEmployee(id);
        if (employee.isPresent()) {
            model.addAttribute("employee", employee.get());
            return "employeeEdit";
        } else {
            return "redirect:/employeeView";
        }
    }

    @PostMapping("/update")
    public String updateEmployee(Employee employee) {
        employeeService.saveEmployee(employee);
        return "redirect:/employeeView";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/employeeView";
    }
}
