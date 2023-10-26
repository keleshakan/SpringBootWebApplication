package com.example.MSSQLConnection.resource;

import com.example.MSSQLConnection.persistence.Department;
import com.example.MSSQLConnection.service.DepartmentService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DepartmentResource {

    DepartmentService departmentService;

    public DepartmentResource(DepartmentService departmentService){
        this.departmentService = departmentService;
    }

    @GetMapping("/departments")
    @Cacheable("departments-search")
    public List<Department> getAll(){
        return this.departmentService.getAll();
    }

    @GetMapping("/departments/{id}")
    public Department getById(@PathVariable Long id) throws InterruptedException {
        return this.departmentService.getById(id);
    }

    @PostMapping(value = "/departments", consumes = "application/json")
    public Department add(@RequestBody Department department){
        return this.departmentService.add(department);
    }

    @PutMapping(value = "/departments/{id}", consumes = "application/json")
    public Department update(@PathVariable Long id, @RequestBody Department department){
        return this.departmentService.update(id, department);
    }

    @DeleteMapping("/departments/{id}")
    public void delete(@PathVariable Long id){
        this.departmentService.delete(id);
    }

    @DeleteMapping("/departments")
    public void deleteAll(){
        this.departmentService.deleteAll();
    }
}
