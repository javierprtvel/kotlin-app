package com.codely.demo.cat

import com.codely.demo.shared.AgeCalculator
import java.time.LocalDate
import java.time.format.DateTimeParseException
import java.util.*

data class Cat(
    val id: Id,
    val name: Name,
    val origin: Origin,
    val vaccinated: Vaccinated,
    val birthDate: BirthDate,
    val color: Color,
    val age: Int,
    val breed: Breed,
    val createdAt: LocalDate
) {
    enum class Color {
        BLACK, RED, CINNAMON, BLUE, CREAM, LILAC, FAWN, WHITE;

        companion object {
            fun from(value: String?) = if (value.isNullOrBlank() || value.isEmpty() || !isValid(value)) {
                throw InvalidColorException(value)
            } else valueOf(value.uppercase())

            private fun isValid(value: String): Boolean = values().map { it.name }.contains(value.uppercase())
        }
    }

    companion object {
        fun from(
            id: Id,
            name: Name,
            origin: Origin,
            birthDate: BirthDate,
            color: Color,
            vaccinated: Vaccinated,
            createdAt: LocalDate,
            breed: Breed
        ) = Cat(
            id = id,
            name = name,
            origin = origin,
            vaccinated = vaccinated,
            birthDate = birthDate,
            color = color,
            age = AgeCalculator.calculate(birthDate.value, createdAt).years,
            breed = breed,
            createdAt = createdAt
        )
    }

    data class Name(val value: String) {
        companion object {
            fun from(value: String?) = if (value.isNullOrBlank() || value.isEmpty()) {
                throw InvalidNameException(value)
            } else {
                Name(value)
            }
        }
    }

    data class Id(val value: UUID) {
        companion object {
            fun from(value: String?) = try {
                Id(UUID.fromString(value))
            } catch (exception: Throwable) {
                throw InvalidIdException(value)
            }
        }
    }

    data class Origin(val value: String) {
        companion object {
            fun from(value: String?) = if (value.isNullOrEmpty() || value.isBlank()) {
                throw InvalidOriginException(value)
            } else Origin(value)
        }
    }

    data class Vaccinated(val value: Boolean) {
        companion object {
            fun from(value: String?) = if (value.isNullOrBlank() || value.isEmpty() || !isValid(value)) {
                throw InvalidVaccinatedException(value)
            } else {
                if (value == "yes") {
                    Vaccinated(true)
                } else {
                    Vaccinated(false)
                }
            }

            private fun isValid(value: String) = listOf("yes", "no").contains(value.lowercase())
        }
    }

    data class BirthDate(val value: LocalDate) {
        companion object {
            fun from(value: String?) = if (value.isNullOrBlank() || value.isEmpty()) {
                throw InvalidBirthDateException(value)
            } else try {
                BirthDate(LocalDate.parse(value))
            } catch (e: DateTimeParseException) {
                throw InvalidBirthDateException(value)
            }

        }
    }

    data class Breed(val value: String)
}
