package com.springexercisebook.changyu

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Scope
import org.springframework.test.context.ContextConfiguration

class SingletonBean {
    var state = false
}

class ProtoBean {
    var state = false
}

class SingletonTestBean(
    private val singletonBean: SingletonBean,
) {
    fun getValue(): Boolean {
        return singletonBean.state
    }

    fun setValue(value: Boolean) {
        singletonBean.state = value
    }
}

class ProtoTestBean1(
    private val protoBean: ProtoBean,
) {
    fun getValue(): Boolean {
        return protoBean.state
    }

    fun setValue(value: Boolean) {
        protoBean.state = value
    }
}

class ProtoTestBean2(
    private val protoBean: ProtoBean,
) {
    fun getValue(): Boolean {
        return protoBean.state
    }

    fun setValue(value: Boolean) {
        protoBean.state = value
    }
}

@TestConfiguration
class TestClass() {
    @Bean
    fun singletonBean(): SingletonBean {
        return SingletonBean()
    }

    @Bean
    @Scope("prototype")
    fun protoBean(): ProtoBean {
        return ProtoBean()
    }

    @Bean
    fun singletonTestBean(
        singletonBean: SingletonBean,
    ): SingletonTestBean {
        return SingletonTestBean(singletonBean)
    }

    @Bean
    fun protoTestBean1(
        protoBean: ProtoBean,
    ): ProtoTestBean1 {
        return ProtoTestBean1(protoBean)
    }

    @Bean
    fun protoTestBean2(
        protoBean: ProtoBean,
    ): ProtoTestBean2 {
        return ProtoTestBean2(protoBean)
    }
}

@ContextConfiguration()
@Import(TestClass::class)
class SpringBeanScopeStudyTest {
    @Test
    fun 스프링_싱글톤_학습_테스트(){
        val ac = AnnotationConfigApplicationContext(TestClass::class.java)
        // given
        val singletonTestBean1 = ac.getBean("singletonTestBean", SingletonTestBean::class) as SingletonTestBean
        val singletonTestBean2 = ac.getBean("singletonTestBean", SingletonTestBean::class) as SingletonTestBean

        // when
        singletonTestBean2.setValue(singletonTestBean1.getValue())
        singletonTestBean1.setValue(!singletonTestBean1.getValue())

        // then
        println("singletonTestBean1 = $singletonTestBean1")
        println("singletonTestBean2 = $singletonTestBean2")
        assertEquals(singletonTestBean1.getValue(), singletonTestBean2.getValue())
    }

    @Test
    fun 스프링_프로토타입_학습_테스트(){
        // given
        val ac = AnnotationConfigApplicationContext(TestClass::class.java)
        val protoTestBean1 = ac.getBean("protoTestBean1", ProtoTestBean1::class) as ProtoTestBean1
        val protoTestBean1Copy = ac.getBean("protoTestBean1", ProtoTestBean1::class) as ProtoTestBean1
        val protoTestBean2 = ac.getBean("protoTestBean2", ProtoTestBean2::class) as ProtoTestBean2

        //when 1
        protoTestBean2.setValue(protoTestBean1.getValue())
        protoTestBean1.setValue(!protoTestBean1.getValue())

        //then 1
        println("protoTestBean1 = $protoTestBean1")
        println("protoTestBean2 = $protoTestBean2")
        assertNotEquals(protoTestBean1.getValue(), protoTestBean2.getValue())

        //when 2
        protoTestBean2.setValue(protoTestBean1.getValue())
        protoTestBean1.setValue(!protoTestBean1.getValue())

        //then 2
        println("protoTestBean1 = $protoTestBean1")
        println("protoTestBean1Copy = $protoTestBean1Copy")
        assertEquals(protoTestBean1.getValue() , protoTestBean1Copy.getValue())
    }
}