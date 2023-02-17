package com.example.mvvmwithdaggerhilt.api

import com.example.mvvmwithdaggerhilt.utils.Utils.setLog
import java.math.BigInteger
import kotlin.reflect.KProperty

class Test {

    fun printResult(){
       /* val list = listOf(5,2,10,4)
        val result = list.fold(5){s,t-> s+t}

        println(result)*/
    pff("Pro", ::printName, ::printName)
    }

    private inline fun pff(name:String, func1:(String)->Unit, noinline func2:(String) -> Unit){
        func1(name + " Parmar")
        func2("Hello world")
    }

    private fun printName(name:String){
        println("Hello $name")
    }

    val printValue = {value:String, num:Int -> println("$value $num") }
}


 open class Animal{
    fun printAnimal(){
        println("In animal class")
    }
     val a = "public"
     internal val b = "Internal"
     private val c = "Private"
     protected val d = "Protacted"
}

class Example{
    val a = 50;
    companion object{
        const val c = 200
    }
    inner class Inner{
        fun printA(){
            val b = 110
            println(a)

        }
    }

}

@JvmInline
value class Age(val age:Int){
    init {
        require(age > 25)
    }
}
data class Age2(val age: Int, val pincode:Int){
    init {
        require(age > 25)
    }
}

fun main(){
    Test().printResult()
    Test().printValue("Lamda function", 10)

    Animal().printAnimal()

    val example = Example().Inner()
    example.printA()

    loop@ for (i in 1..5) {
        for (j in 1..5) {
            println("Main")
            if (i == j) {
                println("Inner")
                println(i)
                println(j)
                break
            }
            println("Outer")
            println(i)
            println(j)
        }
        println("Main end")
    }
    println("Next linr")

    val age = Age(27)
    val age2 = Age2(27, 382027)
    println(age)
    println(age2)
    val ages = list(28, 1111)
    printAge(ages)

    println(getFibonacciNumber(10000, BigInteger("0"), BigInteger("1")))
}
fun printAge(age:list){
    println(age.pincode)
}

typealias list = Age2

class Delegates(){
    operator fun setValue(
        thisRef:Any?,
        property:KProperty<*>,
        value:String
    ){

    }
}

tailrec fun getFibonacciNumber(n:Int, a:BigInteger, b:BigInteger): BigInteger{
    return if (n == 0){
        b
    }else{
        getFibonacciNumber(n-1, a+b, a)
    }
}