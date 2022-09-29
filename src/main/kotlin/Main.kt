fun main(args: Array<String>) {
    println("New posts forever!")

    val post01 = Post(
        ownerId = 20, fromId = 30, postType = "post", text = "I like horses",
        attachments = emptyArray<Attachment>()
            .plusElement(PhotoAttachment(Photo(1, 1, 1, 1, "My Photo")))
            .plusElement(AudioAttachment())
    )
    val post02 = Post(
        ownerId = 30, fromId = 55, postType = "reply", text = "Me gustan los caballos",
        attachments = emptyArray<Attachment>()
            .plusElement(VideoAttachment(Video(2, 1, "Very good video", 25)))
            .plusElement(DocAttachment())
            .plusElement(LinkAttachment())
    )


    WallService.add(post01)
    val post03 = WallService.add(post02).copy(text = "Me gustan mucho los caballos")
    //println(WallService.toString())

    WallService.update(post03)
    //println(WallService.toString())

    // ПЕЧАТЬ ПОСТОВ ОТКЛЮЧЕНА, чтобы не делать тестов больше, чем указано в задании

    println("Посты добавлены, текст ответного поста изменен")

    val commentToPost03 = Comment(fromId = 0, text = "I do not think about horses")
    try {
        WallService.createComment(post03.id, commentToPost03)
        println("Комментарий добавлен")
    } catch (e: PostNotFoundException) {
        println("Не найден пост с идентификатором ${post03.id}")
    }

    val myNote = Note(title = "Первая заметка", text = "Кошки - милые создания")
    val myNoteId = NoteService.add(myNote)
    val addedNote = NoteService.getById(myNoteId ?: -1)

    if (addedNote is Note) {
        println("Добавлена заметка $addedNote")
        val cid = NoteService.createComment(noteId = addedNote.id, comment = Comment(text="У меня есть кошка"))
        if (cid is Int) {
            println("Добавлен комментарий с id = $cid")
            val comments = NoteService.getComments(addedNote.id)
            println("Количество комментариев: ${comments.size}")
            NoteService.deleteComment(cid)
            println("Теперь количество комментариев: ${NoteService.getComments(addedNote.id).size}")
        }
    }
    println("Список заметок ${NoteService.get()}")
    NoteService.clear()

}
