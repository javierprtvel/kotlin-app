package com.codely.demo.cat

data class Name(val value: String) {
    companion object {
        fun from(value: String?): Name {
            if (value.isNullOrBlank() || value.isNullOrEmpty()) {
                throw InvalidNameException(value)
            }
            return Name(value)
        }
    }
}
