package com.food.delivery.companyservice.utils.validators;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(MockitoExtension.class)
class PhoneNumberValidatorImplTest {

	@Test
	@DisplayName("Should return true when phone is valid")
	void shouldAcceptValidPhone() {
		//arrange && act

		final var phoneNumberRegex = Patterns.getPhoneNumberRegex();
		final var pattern = Pattern.compile(phoneNumberRegex);

		final var valid_1 = pattern.matcher("123123123").matches();
		final var valid_2 = pattern.matcher("+48 123 123 123").matches();
		final var valid_3 = pattern.matcher("(42) 123 123 123").matches();
		final var valid_4 = pattern.matcher("+1-541-754-3010").matches();
		final var valid_5 = pattern.matcher("(089) / 636-48018").matches();
		final var valid_6 = pattern.matcher("19-49-89-636-48018").matches();

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
		final var phoneNumberRegex = Patterns.getPhoneNumberRegex();
		final var pattern = Pattern.compile(phoneNumberRegex);

		final var invalid_1 = pattern.matcher("123123123a").matches();
		final var invalid_2 = pattern.matcher("+48%123 123 123").matches();
		final var invalid_3 = pattern.matcher("   ").matches();
		final var invalid_4 = pattern.matcher(" 123123123").matches();
		final var invalid_5 = pattern.matcher("123123123 ").matches();


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
