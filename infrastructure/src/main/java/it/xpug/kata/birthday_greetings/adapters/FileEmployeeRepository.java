package it.xpug.kata.birthday_greetings.adapters;

import it.xpug.kata.birthday_greetings.models.Employee;
import it.xpug.kata.birthday_greetings.ports.EmployeeRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class FileEmployeeRepository implements EmployeeRepository {
    private final LazyList<Employee> employees;

    public FileEmployeeRepository(InputStream resourceAsStream) {
        employees = new LazyList<>(() -> readEmployees(resourceAsStream));
    }

    @Override
    public List<Employee> getAll() {
        return employees.get();
    }

    private List<Employee> readEmployees(InputStream resourceAsStream) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(resourceAsStream))) {
            in.readLine();
            String str;
            List<Employee> employees = new ArrayList<>();
            while ((str = in.readLine()) != null) {
                String[] employeeData = str.split(", ");
                Employee employee = new Employee(employeeData[1], employeeData[0], employeeData[2], employeeData[3]);
                employees.add(employee);
            }
            return employees;
        } catch (IOException | ParseException e) {
            throw new ReadEmployeesException(e);
        }
    }

    private static class ReadEmployeesException extends RuntimeException {
        public ReadEmployeesException(Throwable cause) {
            super(cause);
        }
    }

    private static class LazyList<T> {
        private List<T> list;
        private final Supplier<List<T>> supplier;

        public LazyList(Supplier<List<T>> supplier) {
            this.supplier = supplier;
        }

        public List<T> get() {
            if (list == null) {
                this.list = this.supplier.get();
            }
            return this.list;
        }
    }
}
