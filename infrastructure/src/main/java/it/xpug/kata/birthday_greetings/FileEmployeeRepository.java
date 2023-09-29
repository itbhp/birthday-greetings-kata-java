package it.xpug.kata.birthday_greetings;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class FileEmployeeRepository implements EmployeeRepository {
    private final InputStream resourceAsStream;

    public FileEmployeeRepository(InputStream resourceAsStream) {
        this.resourceAsStream = resourceAsStream;
    }

    @Override
    public List<Employee> getAll() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(resourceAsStream));
            in.readLine();
            String str = "";
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

    public static class ReadEmployeesException extends RuntimeException {
        public ReadEmployeesException(Throwable cause) {
            super(cause);
        }
    }
}
