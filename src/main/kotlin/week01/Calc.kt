package week01

class Calc {
}

fun main(args: Array<String>) {
    println(calc("-2 * -3 + 0.4 / -0.2"))
}

/**
 * val : 값(Value)
 * var : 변수(Variable)
 * const val : 상수(constant)
 * val a:Int = 3
 */
val trim = """[^.\d-+*/]""".toRegex()

/**
 * '...' : Char literal
 * "..." : String literal
 * """...""" : no escaping, newlines
 */
val groupMD = """((?:\+|\+-)?[.\d]+)([*/])((?:\+|\+-)?[.\d]+)""".toRegex()

/**
 * fun 함수명(인자):반환형 {
 *   몸체
 *   return 반환값
 * }
 */
fun trim(v: String): String {
    return v.replace(trim, "")
}
// fun 함수명(인자):반환형 = 반환식
fun repMtoPM(v: String): String = v.replace("-", "+-")

/**
 * {a1:Type, a2:Type -> Type
 *   return value
 * }
 * val sum = {a:Int, b:Int -> Int
 *   a + b
 * }
 *
 * Sequence<T>.fold<R>(초기값R){이전까지 합산한 값R, 현재요소T ->
 *   ...
 *   다음 요소에 넘길 합산값R
 * } = R
 */
fun foldGroup(v: String): Double = groupMD.findAll(v).fold(0.0) { acc, curr ->
    // Destructuring
    // 내부에 N번째 반환할 값을 정의, (..)를 통해 얻고 필요없는 값은 _로 처리
    val (_, left, op, right) = curr.groupValues
    val leftValue = left.replace("+", "").toDouble()
    val rightValue = right.replace("+", "").toDouble()
    // when 값들의 리턴 타입이 다르면 Any!
    val result = when (op) {
        "*" -> leftValue * rightValue
        "/" -> leftValue / rightValue
        else -> throw Throwable("invalid operator $op")
    }
    acc + result
}
fun calc(v: String) = foldGroup(repMtoPM(trim(v)))