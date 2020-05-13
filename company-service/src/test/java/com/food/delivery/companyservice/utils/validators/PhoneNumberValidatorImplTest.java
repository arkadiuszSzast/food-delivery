package com.food.delivery.companyservice.utils.validators;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(MockitoExtension.class)
class PhoneNumberValidatorImplTest {

	@Mock
	private ConstraintValidatorContext constraintValidatorContext;
	@InjectMocks
	private PhoneNumberValidatorImpl phoneNumberValidator;

	@Test
	@DisplayName("Should return true when phone is valid")
	void shouldAcceptValidPhone() {
		//arrange && act
		final var valid_1 = phoneNumberValidator.isValid("123123123", constraintValidatorContext);
		final var valid_2 = phoneNumberValidator.isValid("+48 123 123 123", constraintValidatorContext);
		final var valid_3 = phoneNumberValidator.isValid("(42) 123 123 123", constraintValidatorContext);
		final var valid_4 = phoneNumberValidator.isValid("+1-541-754-3010", constraintValidatorContext);
		final var valid_5 = phoneNumberValidator.isValid("(089) / 636-48018", constraintValidatorContext);
		final var valid_6 = phoneNumberValidator.isValid("19-49-89-636-48018", constraintValidatorContext);

		//assert
		assertAll(
				() -> assertThat(valid_1).isTrue(),
				() -> assertThat(valid_2).isTrue(),
				() -> assertThat(valid_3).isTrue(),
				() -> assertThat(valid_4).isTrue(),
				() -> assertThat(valid_5).isTrue(),
				() -> assertThat(valid_6).isTrue()
		);
	}

	@Test
	@DisplayName("Should return false when phone is invalid")
	void shouldNotAcceptInvalidPhone() {
		//arrange && act
		final var invalid_1 = phoneNumberValidator.isValid("123123123a", constraintValidatorContext);
		final var invalid_2 = phoneNumberValidator.isValid("+48%123 123 123", constraintValidatorContext);
		final var invalid_3 = phoneNumberValidator.isValid("   ", constraintValidatorContext);
		final var invalid_4 = phoneNumberValidator.isValid(" 123123123", constraintValidatorContext);
		final var invalid_5 = phoneNumberValidator.isValid("123123123 ", constraintValidatorContext);

		//assert
		assertAll(
				() -> assertThat(invalid_1).isFalse(),
				() -> assertThat(invalid_2).isFalse(),
				() -> assertThat(invalid_3).isFalse(),
				() -> assertThat(invalid_4).isFalse(),
				() -> assertThat(invalid_5).isFalse()
		);
	}
}
