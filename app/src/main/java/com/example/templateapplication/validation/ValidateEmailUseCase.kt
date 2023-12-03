package com.example.templateapplication.validation

import android.util.Log
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

class ValidatePasswordUseCase : BaseUseCase<String, ValidationResult> {
    override fun execute(input: String): ValidationResult {
        if (input.length < 8) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.strThePasswordNeedsToConsistOfAtLeastEightCharacters),
            )
        }

        if (!isPasswordValid(input)) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.strThePasswordNeedsToContainAtLeastOneLetterAndDigit),
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}

class ValidatePhoneNumberUseCase : BaseUseCase<String, ValidationResult> {
    override fun execute(input: String): ValidationResult {
        Log.d("phone", input)
        if (input.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.strThePhoneCanNotBeBlank),
            )
        }
        if (!isPhoneNumber(input)) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.strThePhoneNumberShouldBeContentJustDigit),
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
        return ValidationResult(successful = true)
    }
}

fun isNumber(value: String): Boolean {
    return value.isEmpty() || Regex("^\\d+\$").matches(value)
}
fun isPhoneNumber(input: String): Boolean {
    // Belgian phone number regex pattern
    val belgianPhoneNumberPattern = """^(\+32\s?|0)4[56789]\d{7}$""".toRegex()
    // Remove spaces and dashes for better matching
    val sanitizedInput = input.replace("\\s|-".toRegex(), "")
    return belgianPhoneNumberPattern.matches(sanitizedInput)
}

fun isEmailValid(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun isPasswordValid(password: String): Boolean {
    return password.any { it.isDigit() } &&
            password.any { it.isLetter() }
}

fun isVatValid(vat: String): Boolean {
    return Regex("BE 0\\d{3} \\d{3} \\d{3}").matches(vat)
}