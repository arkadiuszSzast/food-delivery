package com.food.delivery.companyservice.company;

import com.food.delivery.companyservice.company.model.CompanyRest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public final class CompanyGetService {

	private final CompanyMapper companyMapper;
	private final CompanyRepository companyRepository;

	public Flux<CompanyRest> findAll() {
		return companyRepository.findAll()
				.map(companyMapper::toRest);
	}

	public Mono<CompanyRest> findById(String id) {
		return companyRepository.findById(id)
				.map(companyMapper::toRest);
	}
}
