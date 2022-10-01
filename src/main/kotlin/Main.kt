fun main(args: Array<String>) {
    println("New posts and notes forever!")

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


    // Заметки и комментарии

    val myNote = Note(title = "Первая заметка", text = "Кошки - милые создания")
    val myNoteId = NoteService.add(myNote)
    val addedNote = NoteService.getById(myNoteId ?: -1)

    if (addedNote is Note) {
        println("Добавлена заметка $addedNote")
        val addedNoteId = addedNote.id
        val cid = NoteService.createComment(noteId = addedNoteId, comment = Comment(text="У меня есть кошка"))
        if (cid is Int) {
            println("Добавлен комментарий с id = $cid")
//            val comments = NoteService.getComments(addedNoteId)
            println("Количество комментариев: ${NoteService.getComments(addedNoteId).size}")
            for (icomment in NoteService.getComments(addedNoteId)) println(icomment)
            NoteService.editComment(cid, "У меня есть кошка и собака")
            println("После изменения комментарии такие:")
            for (icomment in NoteService.getComments(addedNoteId)) println(icomment)
            NoteService.deleteComment(cid)
            println("После удаления количество комментариев: ${NoteService.getComments(addedNoteId).size}")
            NoteService.restoreComment(cid)
            println("После восстановления количество комментариев: ${NoteService.getComments(addedNoteId).size}")
        }
        println("Список заметок ${NoteService.get()}")
        NoteService.edit(noteId = addedNoteId, title = addedNote.title, text = "Кошки - милейшие создания")
        println("После изменения список заметок ${NoteService.get()}")
        NoteService.delete(addedNoteId)
        println("После удаления заметки список заметок ${NoteService.get()}")
    }

    NoteService.clear()

}
