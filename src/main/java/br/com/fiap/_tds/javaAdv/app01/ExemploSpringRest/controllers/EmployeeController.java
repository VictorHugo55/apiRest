package br.com.fiap._tds.javaAdv.app01.ExemploSpringRest.controllers;

import br.com.fiap._tds.javaAdv.app01.ExemploSpringRest.domainmodel.Employee;
import br.com.fiap._tds.javaAdv.app01.ExemploSpringRest.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
@AllArgsConstructor
public class EmployeeController {

    @Autowired
    private EmployeeService service;


    @GetMapping("/")
    public ResponseEntity<List<Employee>> listAll(){

        return new ResponseEntity<>(
                this.service.getAll(),
                HttpStatus.OK
        );
    }

    //http://localhost:8080/api/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Employee> findById( @PathVariable("id") Long id){
        System.out.println("http://localhost:8080/api/" + id);
        Employee emp = this.service.getById(id);
        if(emp == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(emp, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeEmployeeById(@PathVariable Long id ) {
        this.service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee employeeCreated = this.service.save(employee);
        return new ResponseEntity<>(employeeCreated, HttpStatus.CREATED);
    }

    @GetMapping("/hello")
    public ResponseEntity<String> helloWorld(){
        return new ResponseEntity<String>("Hello World RESTFULL manner!!!", HttpStatus.OK);
    }

    @GetMapping("/hello2")
    public String helloWorld2(){
        return "Hello World RESTFULL manner22222222!!!";
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee){
        Employee databaseEmployee = this.service.getById(id);
        databaseEmployee.setName(employee.getName());
        databaseEmployee.setDepartment(employee.getDepartment());
        databaseEmployee.setRole(employee.getRole());
        this.service.update(databaseEmployee);
        return new ResponseEntity<>(databaseEmployee, HttpStatus.OK);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<Employee> patchEmployee(@PathVariable Long id, @RequestBody Map<String, Object> updates){
        Employee updatedEmployee = this.service.partialUpdate(id, updates);
        if( updatedEmployee != null ){
            return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
