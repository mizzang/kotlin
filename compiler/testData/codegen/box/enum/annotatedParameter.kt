// FILE: Foo.java

public class Foo {

    static String test() {
        return KEnum.OK.name();
    }
}



// FILE: KEnum.kt
@Retention(AnnotationRetention.RUNTIME)
annotation class A

enum class KEnum(@A val xx: Any) {
    OK("123")
}

fun box(): String {
    return Foo.test()  + "12";
}