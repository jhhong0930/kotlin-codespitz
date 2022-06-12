package week02

import kotlin.reflect.KProperty

/**
 * KClass - 코틀린 클래스 정의
 * 클래스::class
 * 인스턴스::class
 *
 * KClass.members - 속성, 메소드 일체
 * Collection<KCallable<*>>
 *
 * KCallable - 모든 호출가능 요소
 * KProperty - 속성
 * KFunction - 함수, 메소드
 *
 * "$변수명"
 * "${식}"
 * -> 내부적으로 toString() 자동 호출
 */
fun <T> T.stringify() = StringBuilder().run{
    jsonValue(this@stringify)
    toString()
}
fun StringBuilder.jsonValue(value:Any?){
    when(value){
        null -> append("null")
        is String-> jsonString(value)
        is Boolean, is Number-> append("$value")
        is List<*>-> jsonList(value)
        else-> jsonObject(value)
    }
}
private fun StringBuilder.jsonString(v:String){ append(""""${v.replace("\"", "\\\"")}"""")}
private fun StringBuilder.jsonObject(target:Any){
    wrap('{', '}') {
        target::class.members.filterIsInstance<KProperty<*>>().joinTo(::comma) {
            jsonValue(it.name)
            append(':')
            jsonValue(it.getter.call(target))
        }
    }
}
private fun StringBuilder.jsonList(target:List<*>){
    wrap('[', ']') {
        target.joinTo(::comma) {
            jsonValue(it)
        }
    }
}
/**
 * 확장함수
 * fun 형.함수명(인자): 반환형{
 *   ...
 * }
 */
fun <T> Iterable<T>.joinTo(sep:()->Unit, transform:(T)->Unit){
    forEachIndexed { count, element ->
        if(count != 0) sep()
        transform(element)
    }
}
fun StringBuilder.wrap(begin:Char, end:Char, block:StringBuilder.()->Unit){
    append(begin)
    block()
    append(end)
}
fun StringBuilder.comma(){
    append(',')
}

class Json0(val a: Int, val b: String)

class Json1(
    val a:Int, val b:String, val c:List<String>
)

fun main(args: Array<String>) {
    println(Json1(3, "abc", listOf("a", "b", "c")).stringify())
}

