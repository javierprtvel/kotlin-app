package com.codely.demo

import com.codely.demo.shared.Clock
import com.codely.demo.shared.Reader
import com.codely.demo.shared.Writer
import java.time.LocalDate
import java.time.Period
import kotlin.system.exitProcess

open class App(private val reader: Reader, private val writer: Writer, private val clock: Clock) {
    fun execute() {
        writer.write("Please enter your birthday with the format <yyyy-MM-dd>")
        val line = reader.read()
        line.takeUnless {
            !it.isNullOrEmpty() && !it.isNullOrBlank()
        }?.let {
            writer.write("The introduced date <$it> is not valid")
            return
        }

        line.takeUnless {
            it.isNullOrEmpty()
        }?.let {
            LocalDate.parse(it)
        }.apply {
            if (this == null) {
                writer.write("The introduced date <$this> is not valid")
                exitProcess(1)
            }
        }.also {
            writer.write("You wrote the date $it")
        }?.run {
            calculateDifferenceUntilToday()
        }

        writer.write("Bye!")
    }

    private fun LocalDate.calculateDifferenceUntilToday() = with(Period.between(this, clock.now())) {
        // "this" is the result of calling Period, not the "run-this"
        when { // when (true)
            years > 0 -> writer.write("The difference between the date you wrote and today is $years years.")
            months > 0 -> writer.write("The difference between the date you wrote and today is $months months.")
            days > 0 -> writer.write("The difference between the date you wrote and today is $days days.")
        }
    }
}