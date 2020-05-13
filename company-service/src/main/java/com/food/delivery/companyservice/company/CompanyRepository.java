package com.food.delivery.companyservice.company;

import com.food.delivery.companyservice.company.domain.Company;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

interface CompanyRepository extends ReactiveMongoRepository<Company, String> {
}
