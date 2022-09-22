sealed interface Attachment {
    val type: String
}

data class Photo(
    val id: Int = 0,
    val albumId: Int = 0,
    val ownerId: Int = 0,
    val userId: Int = 0,
    val text: String = ""
)

data class Video(
    val id: Int = 0,
    val ownerId: Int = 0,
    val title: String = "",
    val duration: Int = 0
)

data class Audio(
    val id: Int = 0,
    val ownerId: Int = 0,
    val artist: String = "",
    val title: String = "",
    val duration: Int = 0
)

data class Doc(
    val id: Int = 0,
    val ownerId: Int = 0,
    val title: String = "",
    val size: Int = 0,
    val ext: String = ""
)

data class Link(
    val url: String = "",
    val title: String = "",
    val caption: String = ""
)

data class PhotoAttachment(val photo: Photo = Photo()) : Attachment {
    override val type: String
        get() = "photo"
}

data class VideoAttachment(val video: Video = Video()) : Attachment {
    override val type: String
        get() = "video"
}

data class AudioAttachment(val audio: Audio = Audio()) : Attachment {
    override val type: String
        get() = "audio"
}

data class DocAttachment(val doc: Doc = Doc()) : Attachment {
    override val type: String
        get() = "doc"
}

data class LinkAttachment(val link: Link = Link()) : Attachment {
    override val type: String
        get() = "link"
}

