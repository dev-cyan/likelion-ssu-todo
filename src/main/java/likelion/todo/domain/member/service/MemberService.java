package likelion.todo.domain.member.service;

import likelion.todo.domain.member.dto.MemberLoginRequestDTO;
import likelion.todo.domain.member.dto.MemberLoginResponseDTO;
import likelion.todo.domain.member.dto.MemberRegisterRequestDTO;
import likelion.todo.domain.member.dto.MemberRegisterResponseDTO;
import likelion.todo.domain.member.dto.MemberResponseDTO;
import likelion.todo.domain.member.entity.Member;
import likelion.todo.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public MemberRegisterResponseDTO registerMember(MemberRegisterRequestDTO req) {
        String encodedPassword = passwordEncoder.encode(req.password());

        Member member = Member.builder()
                .username(req.username())
                .password(encodedPassword)
                .build();

        memberRepository.save(member);

        return new MemberRegisterResponseDTO(member.getId());
    }

    public MemberLoginResponseDTO login(MemberLoginRequestDTO req) {
        Member member = memberRepository.findByUsername(req.username())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(req.password(), member.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }

        return new MemberLoginResponseDTO(member.getId());
    }

    public MemberResponseDTO getMember(Long memberId) {

        Member member = memberRepository.findById(memberId).orElseThrow
                (() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "멤버를 찾을 수 없습니다."));

        return new MemberResponseDTO(member.getUsername());
    }

}
