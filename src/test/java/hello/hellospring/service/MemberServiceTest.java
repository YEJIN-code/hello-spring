package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository; // * MemverService 의 memberRepository 와 테스트에서의 memberRepository 가 서로 다르게 만들어진다는 문제가 있음

    @BeforeEach
    public void beforeEach() { // 테스트 실행 전에 시작됨
        // 레포지를 만들고, 서비스를 만들어서 위의 변수에
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() { // 테스트가 메소드별로 실행됐을 때 각각이 끝날때마다 실행
        memberRepository.clearStore(); // 저장소를 정리해줌
    }

    @Test
    void 회원가입() {
        // given
        Member member = new Member();
        member.setName("spring");

        // when
        Long saveId = memberService.join(member);

        // then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        // when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));// 익셉션이 발생해야 하고, 람다로 memberservice.join(member2)를 할건데 이때 발생해야 한다고 지정

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다."); // 에러 메시지가 같은거로 뜨는지 검증

//        try {
//            memberService.join(member2);
//            fail();
//        } catch (IllegalStateException e) {
//            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//        }
        // then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}