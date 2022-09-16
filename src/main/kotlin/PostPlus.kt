
data class Likes(
    val count: Int = 0,
    val userLikes: Boolean = false,
    val canLike: Boolean = true,
    val canPublish: Boolean = true
)

data class Views(val count: Int = 0) {
    override fun toString(): String {
        return count.toString()
    }
}

data class Post(
    val id: Int,
    val owner_id: Int,
    val fromId: Int,
    val date: Int = (System.currentTimeMillis() / 86400000).toInt(),
    val text: String = "",
    val friendsOnly: Boolean = true, /* Наверное, это тоже должно быть Boolean */
    val likes: Likes = Likes(),
    val views: Views = Views(),
    val postType: String = "post",
    val canPin: Boolean = true,
    val isPinned: Boolean = true
)

object WallService {
    private var posts = emptyArray<Post>()

    fun add(post: Post): Post {
        posts += post
        return posts.last()
    }

    // Жалко было удалять код из лекции, так что чуточку его подправила
    fun likeById(id: Int) {
        for ((index, post) in posts.withIndex()) {
            if (post.id == id) {
                posts[index] = post.copy(likes = post.likes.copy(post.likes.count + 1))
            }
        }
    }
}
