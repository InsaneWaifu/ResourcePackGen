import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import java.io.IOException



@JsonSerialize(using = ItemModel.ItemModelSerializer::class)
class ItemModel constructor(val namespace: Namespace, val name: String, parent: String, texture: String,
                            overrides: MutableList<MutableMap<String, Any>>? = null) {

    class ItemModelSerializer @JvmOverloads constructor(t: Class<ItemModel?>? = null) : StdSerializer<ItemModel>(t) {
        @Throws(IOException::class, JsonProcessingException::class)
        override fun serialize(
            value: ItemModel, jgen: JsonGenerator, provider: SerializerProvider
        ) {
            jgen.writeStartObject()
            jgen.writeStringField("parent", value.parent!!)
            jgen.writeFieldName("textures")
            jgen.writeStartObject()
            jgen.writeStringField("layer0", "${value.namespace.name}:${value.texture}")
            jgen.writeEndObject()
            jgen.writeFieldName("overrides")
            jgen.writeStartArray()
            for (i in value.overrides!!) {
                jgen.writeStartObject()
                for (key in i.keys) {
                    jgen.writeFieldName(key)
                    jgen.writeObject(i[key])
                }
                jgen.writeEndObject()
            }
            jgen.writeEndArray()
        }
    }

    fun saveItemModel() {
        namespace.dir.resolve("models").resolve("item").mkdirs()
        mapper.writeValue(namespace.dir.resolve("models").resolve("item").resolve("$name.json"), this)
    }

    val parent: String? = parent
    val texture: String? = texture
    val overrides: MutableList<MutableMap<String, Any>>? = overrides as MutableList<MutableMap<String, Any>>?
}