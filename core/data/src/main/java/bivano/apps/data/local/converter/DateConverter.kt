package bivano.apps.data.local.converter

import androidx.room.TypeConverter
import java.util.*

class DateConverter {

    @TypeConverter
    fun toDate(value: Long?) = value?.let {
        Date(it)
    }

    @TypeConverter
    fun fromDate(value: Date?) = value?.time
}