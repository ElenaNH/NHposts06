import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

class NoteServiceTest {

    @Before
    fun clearNoteServiceBeforeTest() {
        NoteService.clear()
    }

    @Test
    fun clear() {
        val myNote = Note( title = "Моя заметка", text = "Утром был туман")
        NoteService.add(myNote)
        val result1 = NoteService.add(myNote)
        NoteService.clear()
        val result2 = NoteService.add(myNote)

        assertTrue((result1 == 1) && (result2 == 0))
    }

    @Test
    fun add() {
        val myNote = Note( title = "Моя заметка днем", text = "Днем светило солнце")
        val result = NoteService.add(myNote)

        assertEquals(0, result)
    }

    @Test
    fun createComment_forLiveNote() {
        val myNote = Note(title = "Первая заметка", text = "Кошки - милые создания")
        val myNoteId = NoteService.add(myNote)
        val addedNote = NoteService.getById(myNoteId ?: -1)
        val result = NoteService.createComment(noteId = (addedNote?.id ?: -1), comment = Comment(text="У меня есть кошка"))

        assertEquals(0, result)
    }

    @Test(expected = NoteNotFoundException::class)
    fun createComment_forNoNote() {
        val result = NoteService.createComment(noteId = -1, comment = Comment(text="У меня есть кошка"))

        assertEquals(null, result)
    }

    @Test
    fun getById_forLiveNote() {
        val myNote = Note(title = "Первая заметка", text = "Кошки - милые создания")
        val myNoteId = NoteService.add(myNote)
        val result = NoteService.getById(myNoteId ?: -1)

        assertEquals(myNote.copy(id = (myNoteId ?: -1), ownerId = result?.ownerId), result)
    }

    @Test(expected = NoteNotFoundException::class)
    fun getById_forNoNote() {
        val result = NoteService.getById(-1)

        assertEquals(null, result)
    }

    @Test
    fun get() {
        NoteService.add(Note(title = "Первая заметка", text = "Кошки - милые создания"))
        NoteService.add(Note(title = "Вторая заметка", text = "Кошки могут быть и злыми"))
        val result = NoteService.get().count()

        assertEquals(2, result)
    }

    @Test
    fun getComments() {
        val myNoteId = NoteService.add(Note(title = "Первая заметка", text = "Кошки - милые создания"))
        val addedNote = NoteService.getById(myNoteId ?: -1)
        val addedNoteId = addedNote?.id ?: -1
        NoteService.createComment(noteId = addedNoteId, comment = Comment(text="У меня есть кошка"))
        NoteService.createComment(noteId = addedNoteId, comment = Comment(text="И собака тоже есть"))
        NoteService.createComment(noteId = addedNoteId, comment = Comment(text="И попугай"))
        val result = NoteService.getComments(addedNoteId).count()

        assertEquals(3, result)
    }

    @Test
    fun edit_forLiveNote() {
        val myNote = Note(title = "Первая заметка", text = "Кошки - милые создания")
        val myNoteId = NoteService.add(myNote)
        val addedNote = NoteService.getById(myNoteId ?: -1)
        val result = NoteService.edit(noteId = (addedNote?.id) ?: -1, title = "Измененная заметка", text = "Кошки очень милы")

        assertTrue(result)
    }

    @Test(expected = NoteNotFoundException::class)
    fun edit_forNoNote() {
        val result = NoteService.edit(noteId = -1, title = "Измененная заметка", text = "Кошки очень милы")

        assertFalse(result)
    }

    @Test
    fun editComment_forLiveComment() {
        val myNote = Note(title = "Первая заметка", text = "Кошки - милые создания")
        val myNoteId = NoteService.add(myNote)
        val addedNote = NoteService.getById(myNoteId ?: -1)
        val myCommentId = NoteService.createComment(noteId = (addedNote?.id ?: -1), comment = Comment(text="У меня есть кошка"))
        val result = NoteService.editComment(commentId = (myCommentId ?: -1), text = "У меня есть кошка!!!!!")

        assertTrue(result)
    }

    @Test(expected = CommentNotFoundException::class)
    fun editComment_forNoComment() {
        val result = NoteService.editComment(commentId = -1, text = "У меня есть кошка!!!!!")

        assertFalse(result)
    }

    @Test
    fun delete_forSuccess() {
        NoteService.add(Note( title = "Утро", text = "Утром был туман"))
        NoteService.add(Note( title = "День", text = "Днем светило солнце"))
        NoteService.add(Note( title = "Вечер", text = "Вечером прилетели комары"))
        val count1 = NoteService.get().count()
        var result = NoteService.delete(1)
        val count2 = NoteService.get().count()
        result = result && (count1 == 3) && (count2 == 2)

        assertTrue(result)
    }

    @Test(expected = OperationFailedException::class)
    fun delete_forFailure() {
        NoteService.add(Note( title = "Утро", text = "Утром был туман"))
        val result = NoteService.delete(-1)

        assertFalse(result)
    }

    @Test
    fun deleteComment_forSuccess() {
        val myNote = Note(title = "Первая заметка", text = "Кошки - милые создания")
        val myNoteId = NoteService.add(myNote)
        val addedNote = NoteService.getById(myNoteId ?: -1)
        NoteService.createComment(noteId = (addedNote?.id ?: -1), comment = Comment(text="У меня есть кошка"))
        val result = NoteService.deleteComment(0)

        assertTrue(result)
    }

    @Test(expected = OperationFailedException::class)
    fun deleteComment_forFailure() {
        val myNote = Note(title = "Первая заметка", text = "Кошки - милые создания")
        val myNoteId = NoteService.add(myNote)
        val addedNote = NoteService.getById(myNoteId ?: -1)
        NoteService.createComment(noteId = (addedNote?.id ?: -1), comment = Comment(text="У меня есть кошка"))
        val result = NoteService.deleteComment(-1)

        assertFalse(result)
    }

    @Test
    fun restoreComment_forSuccess() {
        val myNote = Note(title = "Первая заметка", text = "Кошки - милые создания")
        val myNoteId = NoteService.add(myNote)
        val addedNote = NoteService.getById(myNoteId ?: -1)
        NoteService.createComment(noteId = (addedNote?.id ?: -1), comment = Comment(text="У меня есть кошка"))
        NoteService.createComment(noteId = (addedNote?.id ?: -1), comment = Comment(text="У меня есть еще и собака"))
        NoteService.deleteComment(0)
        val result = NoteService.restoreComment(0)

        assertTrue(result)
    }

    @Test(expected = OperationFailedException::class)
    fun restoreComment_forFailure() {
        val myNote = Note(title = "Первая заметка", text = "Кошки - милые создания")
        val myNoteId = NoteService.add(myNote)
        val addedNote = NoteService.getById(myNoteId ?: -1)
        NoteService.createComment(noteId = (addedNote?.id ?: -1), comment = Comment(text="У меня есть кошка"))
        NoteService.createComment(noteId = (addedNote?.id ?: -1), comment = Comment(text="У меня есть еще и собака"))
        NoteService.deleteComment(0)
        val result = NoteService.restoreComment(1)  // Пытаемся восстановить не удаленный ранее комментарий

        assertFalse(result)
    }

}