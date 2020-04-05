package com.food.delivery.companyservice.company;

import com.food.delivery.companyservice.company.domain.Company;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@AllArgsConstructor
public final class CompanyGetService {

	private final CompanyRepository companyRepository;

	public Flux<Company> findAll() {
		return companyRepository.findAll();
	}
}