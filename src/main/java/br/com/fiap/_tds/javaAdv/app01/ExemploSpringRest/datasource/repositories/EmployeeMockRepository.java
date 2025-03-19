package br.com.fiap._tds.javaAdv.app01.ExemploSpringRest.datasource.repositories;

import br.com.fiap._tds.javaAdv.app01.ExemploSpringRest.domainmodel.Employee;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeMockRepository {

    private final List<Employee> dataset = new ArrayList<>();

    public EmployeeMockRepository() {
        this.dataset.add( new Employee(1L, "Orlandao", "CHEFE DA PORRA TODA", "GERAL"));
        this.dataset.add( new Employee(2L, "Employee1", "production1", "prod"));
        this.dataset.add( new Employee(3L, "Employee2", "production2", "prod"));
        this.dataset.add( new Employee(4L, "Employee3", "production3", "prod"));
        this.dataset.add( new Employee(5L, "Employee4", "production4", "prod"));
        this.dataset.sort((o1, o2) -> o1.getId().compareTo(o2.getId()));
    }

    public List<Employee> getAll(){
        return this.dataset.subList(0, this.dataset.size());
    }


    public Employee save(Employee employee) {
        this.dataset.sort((o1, o2) -> o1.getId().compareTo(o2.getId()));
        Long lastID = this.dataset.get(this.dataset.size() -1).getId();
        employee.setId(lastID + 1);
        this.dataset.add(employee);
        System.out.println("id"+lastID);
        return employee;
    }

    public void deleteById(Long id) {
        this.dataset.removeIf(
                employee ->
                        employee.getId().equals(id));
    }

    public void delete(Employee anEmployee) {
        this.dataset.removeIf(
                employee ->
                        employee.getId().equals(anEmployee.getId()));
    }

    public Employee findById(Long id) {
        for(Employee employee : this.getAll()){
            if(employee.getId().equals(id)){
                return employee;
            }
        }
        return null;
    }
}
