package com.codely.demo

import com.codely.demo.cat.BreedSearcher
import com.codely.demo.cat.CatCreationException
import com.codely.demo.cat.CatCreatorAsync
import com.codely.demo.cat.HttpBreedClient
import com.codely.demo.cat.InvalidBirthDateException
import com.codely.demo.cat.InvalidBreedException
import com.codely.demo.cat.InvalidColorException
import com.codely.demo.cat.InvalidIdException
import com.codely.demo.cat.InvalidNameException
import com.codely.demo.cat.InvalidOriginException
import com.codely.demo.cat.InvalidVaccinatedException
import com.codely.demo.cat.MapCatRepository
import com.codely.demo.shared.Clock
import com.codely.demo.shared.Reader
import com.codely.demo.shared.Writer
import org.http4k.client.JavaHttpClient

fun main() {
    try {
        CatCreatorAsync(
            Reader(),
            Writer(),
            Clock(),
            MapCatRepository(),
            BreedSearcher(HttpBreedClient(JavaHttpClient()))
        ).create()
    } catch (e: CatCreationException) {
        when (e) {
            is InvalidNameException -> println("Invalid cat name")
            is InvalidOriginException -> println("Invalid cat origin")
            is InvalidColorException -> println("Invalid cat color")
            is InvalidVaccinatedException -> println("Invalid cat vaccinated value")
            is InvalidIdException -> println("Invalid cat ID")
            is InvalidBirthDateException -> println("Invalid cat birth date")
            is InvalidBreedException -> println("Invalid cat breed")
        }
    }
}