package com.t25.hbv601g.timerunner.communications;

import com.t25.hbv601g.timerunner.entities.Employee;

import java.util.List;

/**
 * Created by dingo on 4.4.2017.
 */

public interface EmployeeListCollback  {

    void onSuccess(List<Employee> employeeList);
    void onFailure(String error);
}
