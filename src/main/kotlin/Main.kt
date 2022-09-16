

fun main(args: Array<String>) {
    println("Hello World!")

    val views = Views(3)
    println("views: ${views}")

    val mill = System.currentTimeMillis()
    println("System.currentTimeMillis()/86400000=${mill/86400000}")

    val post01 = Post(10, 20, 30, postType = "post")
    println(post01)
}