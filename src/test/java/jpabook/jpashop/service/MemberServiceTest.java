package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class MemberServiceTest {


    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
    public void memberJoin() throws Exception{
        //given
        Member member =new Member();
        member.setUsername("kim");

        //when
        Long saveId = memberService.join(member);

        //then
        em.flush(); //insert 쿼리문을 확인 할 수 있다.
        Assertions.assertEquals(member,memberRepository.findOne(saveId));
    }

    @Test
    public void duplicateMember() throws Exception{
        //given
        Member member1=new Member();
        member1.setUsername("kim1");

        Member member2=new Member();
        member2.setUsername("kim1");

        //when
        memberService.join(member1);
        try{
            memberService.join(member2); //예외가 발생해야 한다.

        }catch (IllegalStateException e){
            return;
        }

        //then
        Assertions.fail("예외가 발생해야 한다."); //여기까지 실행되면 안된다. 여기까지 실행되면 tdd에 문제가 있는 것이다.

    }


}