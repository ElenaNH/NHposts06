data class Likes(
    val count: Int = 0,
    val userLikes: Boolean = false,
    val canLike: Boolean = true,
    val canPublish: Boolean = true
)

data class Views(val count: Int = 0) /*{
    override fun toString(): String {
        return count.toString()
    }
}*/

data class Post(
    val id: Int = -1,
    val ownerId: Int,
    val fromId: Int,
    val date: Int = (System.currentTimeMillis() / 86400000).toInt(),
    val text: String = "",
    val replyOwnerId: Int? = null,
    val replyPostId: Int? = null,
    val friendsOnly: Boolean = true,
    val likes: Likes = Likes(),
    val views: Views = Views(),
    val postType: String = "post",
    val attachments: Array<Attachment> = emptyArray<Attachment>().plusElement(PhotoAttachment()),
    val canPin: Boolean = true,
    val isPinned: Boolean = true
) /*{
    override fun toString(): String {
        val friendsOnlyStr = if (friendsOnly) ", friendsOnly" else ""
        val canPinStr = if (canPin) ", canPin" else ""
        val isPinnedStr = if (isPinned) ", isPinned" else ""
        return """======= POST id=${id} ========
            |ownerID=${ownerId}, fromId=${fromId}, daysOfEpoch=${date},
            |postType=${postType}${friendsOnlyStr}${canPinStr}${isPinnedStr}
            |---
            |${text}
            |---
            |likes=${likes}, views=${views}
            |---------------------------------
            |""".trimMargin()
    }
}*/

object WallService {
    private var posts = emptyArray<Post>()
    private var nextPostId = 0
    private var comments = emptyArray<Comment>()
    private var nextCommentId = 0

    fun clear() {
        posts = emptyArray()
        nextPostId = 0
    }

    fun add(post: Post): Post {
        // Посты на стене могут иметь один из 5 типов
        val postTypeNew = when (post.postType) {
            "post", "copy", "reply", "postpone", "suggest" -> post.postType
            else -> "post"
        }
        posts += post.copy(id = nextPostId++, postType = postTypeNew)
        return posts.last()
    }

    fun update(post: Post): Boolean {
        var postFound = false
        for ((index, storedPost) in posts.withIndex()) {
            if (storedPost.id == post.id) {
                postFound = true
                // id так и так одинаковый
                // скопируем весь новый пост, но при этом владельца и дату создания возьмем из старого
                // считаю нелогичным менять тип поста, так что тоже возьмем его из старого
                posts[index] = post.copy(
                    ownerId = storedPost.ownerId,
                    date = storedPost.date,
                    postType = storedPost.postType
                )
            }
        }
        return postFound
    }

    fun createComment(postId: Int, comment: Comment): Comment {
        var postFound = false
        for ((index, storedPost) in posts.withIndex()) {
            if (storedPost.id == postId) {
                comments += comment.copy(id = nextCommentId++, postId = postId)
                postFound = true
            }
        }
        if (!postFound) {
            // Исключение!!!
            throw PostNotFoundException("No post with id ${postId}")
        }
        return comments.last()
    }




    /*

        // Жалко было удалять код из лекции, так что чуточку его подправила
        fun likeById(id: Int) {
            for ((index, post) in posts.withIndex()) {
                if (post.id == id) {
                    posts[index] = post.copy(likes = post.likes.copy(post.likes.count + 1))
                }
            }
        }

        override fun toString(): String {
            var wallDisplay = ""
            for (post in posts) {
                wallDisplay += post.toString()
            }

            return wallDisplay
        }
    */

}
