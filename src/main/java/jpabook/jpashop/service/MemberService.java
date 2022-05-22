package jpabook.jpashop.service;


import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;


    //회원가입
    @Transactional
    public Long join(Member member){

        validateDuplicateMember(member);
         memberRepository.save(member);
         return member.getId();

    }

    //중복회원 검증
    private void validateDuplicateMember(Member member){
        //Exception
        List<Member> findMembers =  memberRepository.findByName(member.getUsername());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }

    }

    //회원 전체 조회
    @Transactional(readOnly = true) //조회 시 성능 최적화
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    @Transactional(readOnly = true) //조회 시 성능 최적화
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }




}
