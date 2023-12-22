package com.example.templateapplication.validation

import android.util.Patterns
import com.example.templateapplication.R
import com.example.templateapplication.model.UiText
import com.example.templateapplication.model.ValidationResult

/**
 * Interface for a base use case in the application.
 *
 * Defines a contract for executing a use case with input and output parameters.
 *
 * @param In The type of input the use case takes.
 * @param Out The type of output the use case produces.
 */
interface BaseUseCase<In, Out> {
    fun execute(input: In): Out
}

/**
 * Use case for validating email addresses.
 *
 * Validates an email based on certain criteria such as blankness and format validity.
 * Returns [ValidationResult] indicating success or failure with an error message.
 */
class ValidateEmailUseCase: BaseUseCase<String, ValidationResult> {
    override fun execute(input: String): ValidationResult {
        if (input.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.strTheEmailCanNotBeBlank)
            )
        }
        if (!isEmailValid(input)) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.strThatsNotAValidEmail)
            )
        }
        return ValidationResult(
            successful = true,
            errorMessage = null
        )
    }
}

class ValidatePhoneNumberUseCase : BaseUseCase<String, ValidationResult> {
    override fun execute(input: String): ValidationResult {
        if (input.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.strThePhoneCanNotBeBlank),
            )
        }
        if(!isNumber(input)){
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.strThePhoneNumberShouldBeContentJustDigit),
            )
        }
        if (!isPhoneNumber(input)) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.strThePhoneNumberShouldBeCorrect),
            )
        }
        return ValidationResult(
            successful = true,
            errorMessage = null
        )
    }
}
class ValidateVatUseCase : BaseUseCase<String, ValidationResult> {
    override fun execute(input: String): ValidationResult {
        if (!isVatValid(input)) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.strInvalidVat)
            )
        }
        return ValidationResult(
            successful = true,
            errorMessage = null
        )
    }
}
class ValidateNotEmptyUseCase : BaseUseCase<String, ValidationResult> {
    override fun execute(input: String): ValidationResult {
        if (input.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.strInvalidText)
            )
        }
        return ValidationResult(
            successful = true,
            errorMessage = null
        )
    }
}
class ValidateRequiredNumberUseCase : BaseUseCase<String, ValidationResult> {
    override fun execute(input: String): ValidationResult {
        if (!isNumber(input)) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.strInvalidNumber)
            )
        }
        return ValidationResult(
            successful = true,
            errorMessage = null
        )
    }
}

/**
 * Checks if a given string represents a valid number.
 *
 * @param value The string to be checked.
 * @return Boolean indicating whether the string is a valid number.
 */
fun isNumber(value: String): Boolean {
    return (value.isNotEmpty()) && Regex("^\\d+\$").matches(value)
}

/**
 * Verifies if a string is a valid Belgian phone number.
 *
 * @param input The string representing the phone number.
 * @return Boolean indicating whether the phone number is valid as per Belgian standards.
 */
fun isPhoneNumber(input: String): Boolean {
    val belgianPhoneNumberPattern = """^(\+32\s?|0)4[56789]\d{7}$""".toRegex()
    val sanitizedInput = input.replace("\\s|-".toRegex(), "")
    return belgianPhoneNumberPattern.matches(sanitizedInput)
}

/**
 * Validates if the provided email string is in a valid format.
 *
 * @param email The email string to be validated.
 * @return Boolean indicating whether the email is valid.
 */
fun isEmailValid(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

/**
 * Validates if the provided VAT number is in a valid format.
 *
 * @param vat The VAT number string to be validated.
 * @return Boolean indicating whether the VAT number is valid.
 */
fun isVatValid(vat: String): Boolean {
    return vat.isEmpty() || Regex("^BE[01][0-9]{9}$").matches(vat)
}