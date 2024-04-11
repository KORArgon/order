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

    private MessageService messageService;

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
        Member checkUser = new Member();
        if(insertAndUpdateAt.equals("insert")){
            checkUser.setMemberId(member.getMemberId());

            if (memberRepository.findByMemberId(member.getMemberId()) != null){
                return false;
            }
            Member insertMember = new Member();
            insertMember.setMemberId(member.getMemberId());
            insertMember.setPassword(passwordEncoder.encode(member.getPassword()));
            insertMember.setName(member.getName());
            insertMember.setGrade(member.getGrade());
            insertMember.setRegistDate(DateUtil.getTodateTime());
            if(principal.getName() == null) insertMember.setRegistId(member.getMemberId());
            else insertMember.setRegistId(principal.getName());
            memberRepository.save(insertMember);
        }
        if(insertAndUpdateAt.equals("update")){
            checkUser = memberRepository.findByMemberId(member.getMemberId());
            if(member.getPassword().isEmpty()) member.setPassword(checkUser.getPassword());
            else member.setPassword(passwordEncoder.encode(member.getPassword()));
            member.setUpdateDate(DateUtil.getTodateTime());
            member.setUpdateId(LoginUtil.getLoingId());
            memberRepository.save(member);
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

}
