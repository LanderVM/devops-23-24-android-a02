package com.example.templateapplication.validation

import android.util.Patterns
import com.example.templateapplication.R
import com.example.templateapplication.model.UiText
import com.example.templateapplication.model.ValidationResult

interface BaseUseCase<In, Out> {
    fun execute(input: In): Out
}
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

fun isNumber(value: String): Boolean {
    return (value.isNotEmpty()) && Regex("^\\d+\$").matches(value)
}
fun isPhoneNumber(input: String): Boolean {
    val belgianPhoneNumberPattern = """^(\+32\s?|0)4[56789]\d{7}$""".toRegex()
    val sanitizedInput = input.replace("\\s|-".toRegex(), "")
    return belgianPhoneNumberPattern.matches(sanitizedInput)
}
fun isEmailValid(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}
fun isVatValid(vat: String): Boolean {
    return Regex("^BE[01][0-9]{9}$").matches(vat)
}