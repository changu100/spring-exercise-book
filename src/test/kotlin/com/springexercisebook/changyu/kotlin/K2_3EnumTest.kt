package com.springexercisebook.changyu.kotlin

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class K2_3EnumTest {
    @Test
    fun enum_테스트(){
        // when
        assertEquals(Color.VIOLET.rgb(), (238 * 256 + 130) * 256 + 238)
    }
}

enum class Color(val r : Int , val g:Int, val b:Int){
    RED(255,0,0),
    ORANGE(255,265,0),
    YELLOW(255,255,0),
    GREEN(0,255,0),
    BLUE(0,0,255),
    INDIGO(75,0,130),
    VIOLET(238,130,238);// function 과 함께 사용하려면 여기 ; 필수

    fun rgb() = ( r * 256 + g ) * 256 + b
}
