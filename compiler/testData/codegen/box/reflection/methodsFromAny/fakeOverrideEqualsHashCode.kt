// IGNORE_BACKEND: JVM_IR, JS_IR, JS, NATIVE
// WITH_REFLECT

import kotlin.test.assertEquals

open class A<T> {
    fun foo(t: T) {}
}

open class B<U> : A<U>()

class C : B<String>()

fun checkEqual(x: Any, y: Any) {
    assertEquals(x, y)
    assertEquals(x.hashCode(), y.hashCode(), "Elements are equal but their hash codes are not: ${x.hashCode()} != ${y.hashCode()}")
}

fun box(): String {
    val afoo = A::class.members.single { it.name == "foo" }
    val bfoo = B::class.members.single { it.name == "foo" }
    val cfoo = C::class.members.single { it.name == "foo" }

    checkEqual(afoo, bfoo)
    checkEqual(bfoo, cfoo)

    return "OK"
}
