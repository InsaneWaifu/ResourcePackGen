import java.io.File

class ResourcePack(folder: File) {
    val location = folder
    val exists
        get() = location.exists()

    var mcmeta: McMeta? = null

    init {
        if (exists) {
            mcmeta = McMeta.fromJSON(location.resolve("pack.mcmeta"))
        }
    }

    fun createFolder() {
        assert(location.mkdir())
    }
}
