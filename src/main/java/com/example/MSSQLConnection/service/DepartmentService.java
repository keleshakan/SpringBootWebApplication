package com.example.MSSQLConnection.service;

import com.example.MSSQLConnection.persistence.Department;
import com.example.MSSQLConnection.persistence.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

@Service
public class DepartmentService {

    static Logger logger = Logger.getLogger(DepartmentService.class.getName());

    DepartmentRepository repository;

    public DepartmentService(DepartmentRepository repository){
        this.repository = repository;
    }

    public List<Department> getAll(){
        return this.repository.findAll();
    }

    public Department getById(Long id) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(createRunnable(id));
        executorService.execute(createRunnable(id));
        executorService.shutdown();

        Thread.sleep(1);

        if (this.repository.findById(id).isPresent()){
            return this.repository.findById(id).get();
        }
        return null;
    }

    public Department add(Department department){
        return this.repository.save(department);
    }

    public Department update(Long id, Department department){
        Optional<Department> optionalDepartment = this.repository.findById(id);
        if (optionalDepartment.isPresent()){
            optionalDepartment.get().setName(department.getName());
            return this.repository.save(optionalDepartment.get());
        }
        throw new RuntimeException();
    }

    public void delete(Long id){
        this.repository.deleteById(id);
    }

    public void deleteAll(){
        this.repository.deleteAll();
    }

    public void setDepartmentName(Long id) throws InterruptedException {
        Department department = null;
        try{
            if (this.repository.findById(id).isPresent()){
                department = this.repository.findById(id).get();
            }
        }catch (Exception e){
            logger.warning(e.getMessage());
        }

        if (Objects.isNull(department)){
            logger.warning("department is null");
            return;
        }
        department.setName("Math");

        try{
            this.repository.save(department);
        }catch (Exception exception){
            logger.warning(exception.getMessage());
        }
    }

    private Runnable createRunnable(Long id){
        return () -> {
            try {
                setDepartmentName(id);
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                logger.warning(e.getMessage());
            }
        };
    }
}
