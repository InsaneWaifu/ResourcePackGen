import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.io.File
import java.nio.file.Paths

internal class ResourcePackTest {
    @Test
    @DisplayName("Initialize resource pack from testpack folder")
    fun testInitializeResourcePack() {
        val resourcePack = ResourcePack(Paths.get(javaClass.classLoader.getResource("testpack")!!.toURI()).toFile())
        assertEquals("test pack", resourcePack.mcmeta?.description)
    }

    @Test
    @DisplayName("Create resource pack from scratch and save to resourcePackFromScratchTest")
    fun testCreateResourcePack() {
        File("resourcePackFromScratchTest").deleteRecursively()
        val resourcePack = ResourcePack(File("resourcePackFromScratchTest"))
        resourcePack.mcmeta = McMeta("test pack from scratch", 9, resourcePack)
        resourcePack.createFolder()
        resourcePack.mcmeta!!.save()
        val namespace = Namespace("minecraft", resourcePack)
        namespace.createFolder()
        val texture = ItemTexture(namespace, "gaming_hoe.png")
        texture.copyImage(File("gaming.png"))
        val itemModel = ItemModel(namespace, "wooden_hoe", "item/handheld", "item/gaming_hoe", mutableListOf())
        itemModel.saveItemModel()
    }
}