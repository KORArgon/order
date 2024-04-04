package com.argon.order.service;

import com.argon.order.Util.DateUtil;
import com.argon.order.domain.Member;
import com.argon.order.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = new Member();
        member.setMemberId(username);
        member = memberRepository.findByMemberId(member.getMemberId());
        if(member != null){
            List<GrantedAuthority> authorities = new ArrayList();
            System.out.println("password1 : "+member.getPassword());
            System.out.println("password2 : "+passwordEncoder.encode(member.getPassword()));
            return new User(member.getMemberId(), passwordEncoder.encode(member.getPassword()), authorities);
        }
        return null;
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
    public Member findById(Long id){
        return memberRepository.findById(id).orElseThrow();
    }

    /**
     * 등록 및 수정 처리
     * @param member
     */
    @Transactional
    public boolean save(Member member, Principal principal) {
        Member checkUser = new Member();
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
