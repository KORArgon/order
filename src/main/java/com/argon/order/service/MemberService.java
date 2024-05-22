package com.argon.order.service;

import com.argon.order.util.DateUtil;
import com.argon.order.domain.Member;
import com.argon.order.repository.MemberRepository;
import com.argon.order.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByMemberId(username);
        if(member != null){
            return User.builder()
                    .username(member.getMemberId())
                    .password(member.getPassword())
                    .roles(member.getGrade())
                    .build();
        }
        throw new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다.");
    }

    /**
     * 전체 조회
     * @param pageable
     * @return
     */
    public Page<Member> findAll(Pageable pageable) {
        return memberRepository.findAll(pageable);
    }

    /**
     * 상세 조회
     * @param id
     * @return
     */
    public Member findById(String id){
        return memberRepository.findByMemberId(id);
    }

    /**
     * 등록 및 수정 처리
     * @param member
     */
    @Transactional
    public boolean save(Member member, Principal principal, String insertAndUpdateAt) {
        if(insertAndUpdateAt.equals("insert")){
            if (memberRepository.findByMemberId(member.getMemberId()) != null){
                return false;
            }

            String registId = principal.getName() != null ? principal.getName() : member.getMemberId();

            Member insertMember = Member.builder()
                    .memberId(member.getMemberId())
                    .password(passwordEncoder.encode(member.getPassword()))
                    .name(member.getName())
                    .grade(member.getGrade())
                    .registDate(DateUtil.getTodateTime())
                    .registId(registId)
                    .build();

            memberRepository.save(insertMember);
        }
        if(insertAndUpdateAt.equals("update")){
            Member checkUser = memberRepository.findByMemberId(member.getMemberId());
            String password = member.getPassword().isEmpty() ? checkUser.getPassword() : passwordEncoder.encode(member.getPassword());

            Member updateMember = Member.builder()
                    .memberId(member.getMemberId())
                    .password(password)
                    .name(member.getName())
                    .grade(member.getGrade())
                    .updateDate(DateUtil.getTodateTime())
                    .updateId(LoginUtil.getLoingId())
                    .build();

            memberRepository.save(updateMember);
        }


        return true;
    }

    /**
     * 삭제 처리
     * @param member
     */
    public void memberDelete(Member member) {
        memberRepository.delete(member);
    }

    public boolean memberCheck(String memberId, String password) {
        Member member = memberRepository.findByMemberId(memberId);
        return passwordEncoder.matches(password, member.getPassword());
    }
}
