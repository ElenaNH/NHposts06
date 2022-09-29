// Добавлено неописанное в api поле parentId для связи с постом или другим родительским объектом
// Поскольку объект Comment уже был принят в предыдущей домашке, нет препятствий к его дальнейшему использованию

data class Comment(
    val id: Int = -1,
    val parentId: Int? = null,
    val fromId: Int = 0,
    val date: Int = (System.currentTimeMillis() / 86400000).toInt(),
    val text: String = "",
    val replyToUser: Int? = null,
    val replyToComment: Int? = null,
    val attachments: Array<Attachment> = emptyArray<Attachment>().plusElement(PhotoAttachment()),
    val parentsStack: Array<Int> = emptyArray()
)


