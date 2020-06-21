package com.food.delivery.companyservice.utils.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NameTransformers;
import org.modelmapper.convention.NamingConventions;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DefaultModelMapper {

	@Bean
	public ModelMapper modelMapper() {
		final var modelMapper = new ModelMapper();
		modelMapper.getConfiguration()
				.setDeepCopyEnabled(true)
				.setMatchingStrategy(MatchingStrategies.STRICT)
				.setDestinationNameTransformer(NameTransformers.builder())
				.setDestinationNamingConvention(NamingConventions.builder());

		return modelMapper;
	}

}
