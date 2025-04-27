package com.aselsan.utils

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader

class CsvDataLoader {
    fun <T> load(filePath: String, rowParser: (Map<String, String>) -> T): List<T> {

        val p = this::class.java.getResourceAsStream(filePath)
        val items = mutableListOf<T>()
        csvReader().open(p) {
            readAllWithHeaderAsSequence().forEach { row ->
                val item = rowParser(row)
                items.add(item)
            }
        }
        return items
    }
}