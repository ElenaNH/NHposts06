// Два поля не созданы по следующим причинам:
// 1) Описание объекта donut в комментарии не соответствует описанию такого же объекта в посте - какое верное?
// 2) thread - просто не понимаю, что такое вложенная ветка комментариев с точки зрения текущего комментария
// допустим, что коды комментариев в треде текущего комментария: 3, 5, 11, 18
// тогда для комментария с кодом 3 тред будет состоять из 5, 11, 18 - так?
// а как тогда реализовать синхронизацию, чтобы именно коды 5, 11, 18 были - и только они?

// Добавлено неописанное в api поле postId для связи с постом
// Просто эти входные данные некуда было деть, и в то же время нужна какая-то связь коммента с постом

data class Comment(
    val id: Int = 0,
    val postId: Int? = null,
    val fromId: Int,
    val date: Int = (System.currentTimeMillis() / 86400000).toInt(),
    val text: String = "",
    val replyToUser: Int? = null,
    val replyToComment: Int? = null,
    val attachments: Array<Attachment> = emptyArray<Attachment>().plusElement(PhotoAttachment()),
    val parentsStack: Array<Int> = emptyArray()
)


