// Реализовать точно, как написано, невозможно чисто по времени
// Поэтому надо что-то отсечь. Я отсекаю почти все, оставляю самый минимум

data class Note(
    val id: Int = -1,
    val ownerId: Int? = null,
    val title: String = "",
    val text: String = "",
    val date: Int = (System.currentTimeMillis() / 86400000).toInt()
)


object NoteService {
    private var nextNoteId = 0
    private var listNotes = mutableListOf<Note>()
    private var nextCommentId = 0
    private var listComments = mutableListOf<Comment>()
    private var delNotes = mutableListOf<Note>()
    private var delComments = mutableListOf<Comment>()

    // сброс синглтона
    fun clear() {
        nextNoteId = 0
        listNotes.clear()
        nextCommentId = 0
        listComments.clear()
        delNotes.clear()
        delComments.clear()
    }

    // работа с заметками
    fun add(note: Note): Int? {
        val newNote =
            note.copy(id = nextNoteId++, ownerId = (0..2).random())   // Ну допустим, что мы знаем текущего пользователя
        val actionResult: Boolean = listNotes.add(newNote)
        return if (actionResult) newNote.id else null
    }


    // работа с комментариями
    fun createComment(noteId: Int, comment: Comment): Int? {
        val newComment = comment.copy(id = nextCommentId++, parentId = noteId)
        val actionResult: Boolean = listComments.add(newComment)
        return if (actionResult) newComment.id else null
    }

    fun getById(noteId: Int): Note? {
        var isFound = false
        var noteFound: Note? = null
        for (note in listNotes) {
            if (note.id == noteId) {
                isFound = true
                noteFound = note
                break
            }
        }
        if (!isFound) {
            // Исключение!!!
            throw NoteNotFoundException("No note with id ${noteId}")
        }

        return noteFound
    }

    fun get(): List<Note> {
        return listNotes.toList()   // Возвращаем неизменяемую копию списка
    }

    fun getComments(noteId: Int): Array<Comment> {
        var comments = emptyArray<Comment>()
        for (comment in listComments) {
            if (comment.parentId == noteId) comments += comment.copy()  // вернем копии комментариев, отвязав их от списка
        }
        return comments
    }

    fun edit(noteId: Int, title: String, text: String): Boolean {
        var isEdited = false
        for ((ind, note) in listNotes.withIndex()) {
            if (note.id == noteId) {
                listNotes[ind] = note.copy(title = title, text = text)
                isEdited = true
                break
            }
        }
        if (!isEdited) {
            // Исключение!!!
            throw NoteNotFoundException("No note with id ${noteId}")
        }
        return true
    }

    fun editComment(commentId: Int, text: String): Boolean {
        var isEdited = false
        for ((ind, comment) in listComments.withIndex()) {
            if (comment.id == commentId) {
                listComments[ind] = comment.copy(text = text)
                isEdited = true
                break
            }
        }
        if (!isEdited) {
            // Исключение!!!
            throw CommentNotFoundException("No comment with id $commentId")
        }
        return true
    }

    // Если бы мы ставили флаг "Удалено", то вынуждены были бы его обрабатывать абсолютно во всех методах
    // А вдруг забудем?
    // Кардинальное решение - убирать все в отдельную коллекцию
    fun delete(noteId: Int): Boolean {
        var isDone = false
        for ((ind, note) in listNotes.withIndex()) {
            if (note.id == noteId) {
                val liveComments = getComments(noteId)  // Запомним неудаленные комментарии
                if (delNotes.add(note)) {
                    for (comment in liveComments) {
                        deleteComment(comment.id)
                    }
                    listNotes.removeAt(ind)
                    isDone = true    // Если попали сюда, то все удалено
                }
                break   // дальнейший поиск смысла не имеет
            }
        }
        if (!isDone) {
            // Исключение!!!
            throw OperationFailedException("Cannot delete note with id $noteId")
        }
        return true
    }

    fun deleteComment(commentId: Int): Boolean {
        var isDone = false
        for ((ind, comment) in listComments.withIndex()) {
            if (comment.id == commentId) {
                if (delComments.add(comment)) {
                    listComments.removeAt(ind)
                    isDone = true    // Если попали сюда, то все удалено
                }
                break   // дальнейший поиск смысла не имеет
            }
        }
        if (!isDone) {
            // Исключение!!!
            throw OperationFailedException("Cannot delete comment with id $commentId")
        }
        return true
    }

    fun restoreComment(commentId: Int): Boolean {
        var isDone = false
        for ((ind, comment) in delComments.withIndex()) {
            if (comment.id == commentId) {
                if (getById(comment.parentId ?: -1) is Note) {  // заметка "живая"
                    if (listComments.add(comment)) {
                        delComments.removeAt(ind)
                        isDone = true    // Если попали сюда, то все удалено
                    }
                } else {
                    // Заметки такой нет совсем, либо она удалена
                    throw OperationFailedException("Cannot restore comment with id $commentId because parent note is not found")
                }
                break   // дальнейший поиск смысла не имеет
            }
        }
        if (!isDone) {
            // Исключение!!!
            throw OperationFailedException("Cannot restore comment with id $commentId")
        }
        return true
    }

}
