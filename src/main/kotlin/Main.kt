

fun main(args: Array<String>) {
    println("New posts forever!")

    val post01 = Post(ownerId = 20, fromId = 30, postType = "post", text="I like horses")
    val post02 = Post(ownerId = 30, fromId = 55, postType = "reply", text="Me gustan los caballos")

    WallService.add(post01)
    val post03 = WallService.add(post02).copy(text="Me gustan mucho los caballos")
    //println(WallService.toString())

    WallService.update(post03)
    //println(WallService.toString())

    // ПЕЧАТЬ ПОСТОВ ОТКЛЮЧЕНА, чтобы не делать тестов больше, чем указано в задании

    println("Посты добавлены, текст ответного поста изменен")
}