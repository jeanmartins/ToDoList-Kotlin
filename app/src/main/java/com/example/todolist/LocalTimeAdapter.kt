import com.google.gson.*
import java.lang.reflect.Type
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class LocalTimeAdapter : JsonSerializer<LocalTime>, JsonDeserializer<LocalTime> {

    private val formatter = DateTimeFormatter.ISO_LOCAL_TIME

    override fun serialize(
        src: LocalTime?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        val formattedTime = src?.format(formatter)
        return JsonPrimitive(formattedTime)
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalTime? {
        try {
            val formattedTime = json?.asString
            return formattedTime?.let { LocalTime.parse(it, formatter) }
        } catch (e: Exception) {
            // Se a desserialização falhar, retorne um valor padrão, como meia-noite (00:00)
            return LocalTime.MIDNIGHT
        }
    }
}
