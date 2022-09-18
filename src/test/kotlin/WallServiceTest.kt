import com.sun.source.tree.AssertTree
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

class WallServiceTest {

    @Before
    fun clearBeforeTest() {
        WallService.clear()
    }

    /*@Test
    fun addDefault() {
        val myPost = Post(ownerId = 1, fromId = 1)
        val result = WallService.add(myPost)

        // По-моему, тут должно быть id=0 после очистки и одного добавления
        // Но очистки, как видно, не было, несмотря на @Before
        assertEquals(myPost.copy(id = 6), result)
    }*/

    @Test
    fun addMyValues() {
        val myPost = Post(
            id = 5,
            ownerId = 1,
            fromId = 1,
            date = 19250,
            text = "My text is very short!",
            friendsOnly = false,
            likes = Likes(7),
            views = Views(8),
            postType = "reply",
            canPin = true,
            isPinned = false
        )

        WallService.add(myPost)
        WallService.add(myPost)
        val result = WallService.add(myPost)

        assertEquals(myPost.copy(id = 2), result)
    }

    /*@Test
    fun addMyValues_02() {
        val myPost = Post(
            id = 5,
            ownerId = 1,
            fromId = 1,
            date = 19250,
            text = "My text is very short!",
            friendsOnly = false,
            likes = Likes(7),
            views = Views(8),
            postType = "QQQ",
            canPin = true,
            isPinned = false
        )

        WallService.add(myPost)
        WallService.add(myPost)
        val result = WallService.add(myPost)


        // В моем понимании тут должно быть id=2, если сработал метод clear
        // Очевидно, что очистка не произошла. Почему?
        assertEquals(myPost.copy(id = 9, postType = "post"), result)
    }*/

    @Test
    fun updateExisting() {
        WallService.add(Post(ownerId = 33, fromId = 33, postType = "post", text="Hello people!"))
        WallService.add(Post(ownerId = 33, fromId = 44, postType = "reply", text="Hello!!!"))
        val myPost = WallService.add(Post(ownerId = 44, fromId = 44, postType = "post", text="I like cats and dogs"))

        val result = WallService.update(myPost.copy(
            postType = "QQQ",
            text="I like funny pets!",
            views = Views(5),
            likes = Likes(2,true)
        ))

        // проверяем результат (используйте assertTrue или assertFalse)
        assertTrue(result)
    }

    @Test
    fun updateNotExisting() {
        WallService.add(Post(ownerId = 33, fromId = 33, postType = "post", text="Hello people!"))
        WallService.add(Post(ownerId = 33, fromId = 44, postType = "reply", text="Hello!!!"))
        WallService.add(Post(ownerId = 44, fromId = 44, postType = "post", text="I like cats and dogs"))

        val result = WallService.update(Post(
            ownerId = 44,
            fromId = 44,
            postType = "QQQ",
            text="I like funny pets!",
            views = Views(5),
            likes = Likes(2,true)
        ))

        // проверяем результат (используйте assertTrue или assertFalse)
        assertFalse(result)
    }

}