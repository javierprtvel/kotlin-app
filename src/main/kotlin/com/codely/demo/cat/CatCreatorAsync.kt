package com.codely.demo.cat

import com.codely.demo.shared.Clock
import com.codely.demo.shared.Reader
import com.codely.demo.shared.Writer
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class CatCreatorAsync(
    private val reader: Reader,
    private val writer: Writer,
    private val clock: Clock,
    private val repository: MapCatRepository,
    private val breedSearcher: BreedSearcher
) {
    fun create(): Cat {
        val breedList = loadBreedsAsync()
        val id = Cat.Id.from(obtainInput("Please enter an id for your cat"))
        val name = Cat.Name.from(obtainInput("Please enter the name of your cat"))
        val origin = Cat.Origin.from(obtainInput("Please enter where your cat came from"))
        val vaccinated = Cat.Vaccinated.from(obtainInput("Is your cat vaccinated?"))
        val color = Cat.Color.from(obtainInput("What is the color of your cat?"))
        val birthDate = Cat.BirthDate.from(obtainInput("When did your cat birth <yyyy-MM-dd>?"))

        return runBlocking {
            val breed = obtainBreed(breedList.await())
            Cat.from(
                id,
                name,
                origin,
                birthDate,
                color,
                vaccinated,
                clock.now(),
                breed,
            ).apply {
                repository.save(this)
            }.also {
                writer.write("Your cat has been successfully created $it")
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun loadBreedsAsync(): Deferred<List<String>> {
        writer.write("Loading breeds... We'll let you know when it's done")
        return GlobalScope.async {
            val list = breedSearcher.search().map { it.value }
            writer.write(">>>>>>All breeds loaded!<<<<<<")
            list
        }
    }

    private fun obtainBreed(breedList: List<String>): Cat.Breed {
        val breed = obtainInput("What is your cat breed? The supported options are: $breedList")
        if (breed.isNullOrEmpty() || !breedList.contains(breed.lowercase())) {
            throw InvalidBreedException(breed)
        }

        return Cat.Breed(breed)
    }

    private fun obtainInput(message: String) = writer.write(message).run { reader.read() }
}
