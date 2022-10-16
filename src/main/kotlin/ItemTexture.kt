import java.io.File

class ItemTexture(val namespace: Namespace, val name: String) {
    fun copyImage(image: File) {
        namespace.dir.resolve("textures").resolve("item").mkdirs()
        val target = namespace.dir.resolve("textures").resolve("item").resolve(name)
        image.copyTo(target, true)
    }
}