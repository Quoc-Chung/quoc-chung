package com.quocchung.study_git_reverse.service.impl;

import com.quocchung.study_git_reverse.dto.EmployeeRequest;
import com.quocchung.study_git_reverse.dto.EmployeeResponse;
import com.quocchung.study_git_reverse.entity.Employee;
import com.quocchung.study_git_reverse.repository.EmployeeRepository;
import com.quocchung.study_git_reverse.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<EmployeeResponse> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public EmployeeResponse getEmployeeById(Long id) {
        return toResponse(findEmployeeById(id));
    }

    @Override
    @Transactional
    public EmployeeResponse createEmployee(EmployeeRequest request) {
        String email = request.getEmail().trim();
        if (employeeRepository.existsByEmailIgnoreCase(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email nhân viên đã tồn tại");
        }

        Employee employee = new Employee();
        copyRequestToEntity(request, employee);
        return toResponse(employeeRepository.save(employee));
    }

    @Override
    @Transactional
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {
        Employee employee = findEmployeeById(id);
        String email = request.getEmail().trim();
        if (employeeRepository.existsByEmailIgnoreCaseAndIdNot(email, id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email nhân viên đã tồn tại");
        }

        copyRequestToEntity(request, employee);
        return toResponse(employeeRepository.save(employee));
    }

    @Override
    @Transactional
    public void deleteEmployee(Long id) {
        employeeRepository.delete(findEmployeeById(id));
    }

    private Employee findEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Không tìm thấy nhân viên với id: " + id));
    }

    private void copyRequestToEntity(EmployeeRequest request, Employee employee) {
        employee.setFullName(request.getFullName().trim());
        employee.setEmail(request.getEmail().trim());
        employee.setPhone(request.getPhone() == null ? null : request.getPhone().trim());
        employee.setPosition(request.getPosition().trim());
        employee.setSalary(request.getSalary());
    }

    private EmployeeResponse toResponse(Employee employee) {
        return new EmployeeResponse(
                employee.getId(),
                employee.getFullName(),
                employee.getEmail(),
                employee.getPhone(),
                employee.getPosition(),
                employee.getSalary());
    }
}
