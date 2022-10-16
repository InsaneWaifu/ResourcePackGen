import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import java.io.File
import java.io.IOException
import java.io.InputStream


val mapper = ObjectMapper()


@JsonSerialize(using = McMeta.ItemSerializer::class)
class McMeta constructor() {

    class ItemSerializer @JvmOverloads constructor(t: Class<McMeta?>? = null) : StdSerializer<McMeta>(t) {
        @Throws(IOException::class, JsonProcessingException::class)
        override fun serialize(
            value: McMeta, jgen: JsonGenerator, provider: SerializerProvider
        ) {
            jgen.writeStartObject()
            jgen.writeFieldName("pack")
            jgen.writeStartObject()
            jgen.writeNumberField("pack_format", value.pack_format!!)
            jgen.writeStringField("description", value.description!!)
            jgen.writeEndObject()
            jgen.writeEndObject()
        }
    }

    constructor( description: String, pack_format: Int, pack: ResourcePack? = null ) : this() {
        this.pack = pack
        this.pack_format = pack_format
        this.description = description
    }

    var pack: ResourcePack? = null
    var pack_format: Int? = null
    var description: String? = null

    @JsonProperty("pack")
    private fun unpackNested(pack: Map<String, Any>) {
        this.pack_format = pack["pack_format"] as Int
        this.description = pack["description"] as String
    }

    companion object Factory {
        fun fromJSON(file: File): McMeta {
            return mapper.readValue(file, McMeta::class.java)
        }
        fun fromJSON(stream: InputStream): McMeta {
            return mapper.readValue(stream, McMeta::class.java)
        }
    }


    fun save() {
        mapper.writeValue(pack!!.location.resolve("pack.mcmeta"), this)
    }

    fun saveToString(): String {
        return mapper.writeValueAsString(this)
    }

}