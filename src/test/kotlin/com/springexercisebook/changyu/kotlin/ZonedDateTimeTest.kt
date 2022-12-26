package com.springexercisebook.changyu.kotlin

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime

class ZonedDateTimeTest{
    @Test
    fun mongoDB_데이터_읽어온경우_종료일_계산하기(){
        //given
        val mongoDbTime: ZonedDateTime = ZonedDateTime.parse("2022-12-31T18:00:00.000+00:00")
        val period :Long = 10
        //when
        val asiaSeoulTime = mongoDbTime.withZoneSameInstant(ZoneId.of("Asia/Seoul")).plusDays(period).with(LocalTime.MAX)
        //then
        val expectZonedDateTime = ZonedDateTime
            .parse("2023-01-11T00:00:00+09:00")
            .withZoneSameInstant(ZoneId.of("Asia/Seoul"))
            .with(LocalTime.MAX)

        Assertions.assertEquals(asiaSeoulTime, expectZonedDateTime)
    }

    @Test
    fun ZonedDateTime_UTC_same_KST (){
        //given
        val utc = ZonedDateTime.parse("2022-12-31T10:00:00.000+00:00")
        val kst = ZonedDateTime.parse("2022-12-31T19:00:00.000+09:00")
        //when
        val utcToKst = utc.withZoneSameInstant(ZoneId.of("Asia/Seoul")).toLocalDateTime()
        //then
        Assertions.assertEquals(utcToKst, kst.toLocalDateTime())
    }
}