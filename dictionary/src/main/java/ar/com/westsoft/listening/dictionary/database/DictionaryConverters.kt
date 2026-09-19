package ar.com.westsoft.listening.dictionary.database

import androidx.room.TypeConverter
import ar.com.westsoft.listening.dictionary.source.WiktionaryItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DictionaryConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromWiktionaryItemList(value: List<WiktionaryItem>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toWiktionaryItemList(value: String?): List<WiktionaryItem>? {
        if (value == null) return emptyList()
        val listType = object : TypeToken<List<WiktionaryItem>>() {}.type
        return gson.fromJson(value, listType)
    }
}
