import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class McMetaTest {
    @Test
    @DisplayName("McMeta from file name and description")
    fun testMcMetaFileLoad() {
        val mcMeta = McMeta.fromJSON(this::class.java.classLoader.getResourceAsStream("testpack/pack.mcmeta")!!)
        assertEquals(9, mcMeta.pack_format)
        assertEquals("test pack", mcMeta.description)
    }

    @Test
    @DisplayName("McMeta create and save")
    fun testMcMetaSave() {
        val mcMeta = McMeta("test pack", 9)
        assertEquals("""{"pack":{"pack_format":9,"description":"test pack"}}""", mcMeta.saveToString())
    }
}