package com.codely.demo.cat

sealed class CatCreationException(override val message: String?) : IllegalArgumentException(message)

class InvalidNameException(name: String?) : CatCreationException("<$name> is not a valid cat name")
class InvalidOriginException(origin: String?) : CatCreationException("<$origin> is not a valid cat origin")
class InvalidColorException(color: String?) : CatCreationException("<$color> is not a valid cat color")
class InvalidVaccinatedException(vaccinated: String?) : CatCreationException("<$vaccinated> is not a valid cat vaccinated value")
class InvalidIdException(id: String?) : CatCreationException("<$id> is not a valid cat ID")
class InvalidBirthDateException(birthDate: String?) : CatCreationException("<$birthDate> is not a valid cat birth date")
class InvalidBreedException(breed: String?) : CatCreationException("<$breed> is not a valid cat breed")