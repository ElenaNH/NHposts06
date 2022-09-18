import com.sun.source.tree.AssertTree
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

class WallServiceTest {

    @Before
    fun clearBeforeTest() {
        WallService.clear()
    }

    @Test
    fun clearTesting() {
        val myPost = Post(ownerId = 1, fromId = 1)
        val postId = WallService.add(myPost).id
        val result1 = WallService.update(myPost.copy(id = postId, text = "First updating"))
        WallService.clear()
        val result2 = WallService.update(myPost.copy(id = postId, text = "Second updating"))

        assertTrue(result1 && !result2)
    }

    @Test
    fun addDefault() {
        val myPost = Post(ownerId = 1, fromId = 1)
        val result = WallService.add(myPost)

        assertEquals(myPost.copy(id = 0), result)
    }

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

    @Test
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

        assertEquals(myPost.copy(id = 2, postType = "post"), result)
    }

    @Test
    fun addMyValues_03() {
        val myPost = Post(
            id = 5,
            ownerId = 1,
            fromId = 1,
            date = 19250,
            text = "My text is very short!",
            friendsOnly = false,
            likes = Likes(7),
            views = Views(8),
            postType = "suggest",
            canPin = true,
            isPinned = false
        )

        WallService.add(myPost)
        WallService.add(myPost)
        val result = WallService.add(myPost)

        assertEquals(myPost.copy(id = 2), result)
    }

    @Test
    fun addMyValues_04() {
        val myPost = Post(
            id = 5,
            ownerId = 1,
            fromId = 1,
            //date = 19250,
            text = "My text is very short!",
            //friendsOnly = false,
            //likes = Likes(7),
            //views = Views(8),
            postType = "postpone",
            //canPin = true,
            //isPinned = false
        )

        WallService.add(myPost)
        WallService.add(myPost)
        val result = WallService.add(myPost)

        assertEquals(myPost.copy(id = 2), result)
    }
    @Test
    fun addMyValues_05() {
        val myPost = Post(
            id = 5,
            ownerId = 1,
            fromId = 1,
            //date = 19250,
            text = "My text is very short!",
            //friendsOnly = false,
            //likes = Likes(7),
            //views = Views(8),
            postType = "copy",
            //canPin = true,
            //isPinned = false
        )

        WallService.add(myPost)
        WallService.add(myPost)
        val result = WallService.add(myPost)

        assertEquals(myPost.copy(id = 2), result)
    }

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