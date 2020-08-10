package com.food.delivery.menuservice.company

import com.food.delivery.menuservice.employee.EmployeeClient
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class CompanyGetService(private val employeeClient: EmployeeClient, private val companyClient: CompanyClient) {

    fun getCompany(employeeAdminEmail: String): Mono<CompanyRest> {
        return employeeClient.getEmployee(employeeAdminEmail)
                .flatMap { companyClient.getCompany(it.companyId) }
    }
}
