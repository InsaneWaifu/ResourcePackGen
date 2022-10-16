import java.io.File

class Namespace(val name: String, val pack: ResourcePack) {
    val dir = pack.location.resolve("assets").resolve(name)
    fun createFolder() {
        assert(dir.mkdirs())
    }

}