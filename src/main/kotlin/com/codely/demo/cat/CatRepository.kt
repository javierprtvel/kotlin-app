package com.codely.demo.cat

interface CatRepository {
    fun save(cat: Cat)
}

class ListCatRepository : CatRepository {
    private var persistence: List<Cat> = listOf()

    override fun save(cat: Cat) {
        persistence.toMutableList().apply {
            add(cat)
            persistence = toList()
        }
    }
}

class MutableListCatRepository : CatRepository {
    private var persistence: MutableList<Cat> = mutableListOf()

    override fun save(cat: Cat) {
        persistence.add(cat)
    }
}

class MapCatRepository : CatRepository {
    private var persistence: Map<Cat.Id, Cat> = mapOf()

    override fun save(cat: Cat) {
        persistence.toMutableMap().apply {
            this[cat.id] = cat
            persistence = toMap()
        }
    }
}

class MutableMapCatRepository : CatRepository {
    private val persistence: MutableMap<Cat.Id, Cat> = mutableMapOf()

    override fun save(cat: Cat) {
        persistence[cat.id] = cat
    }
}