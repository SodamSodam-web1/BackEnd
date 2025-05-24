package goormton.backend.sodamsodam.domain.user.application.oauth;

import goormton.backend.sodamsodam.domain.user.domain.User;
import goormton.backend.sodamsodam.domain.user.domain.UserPrincipal;
import goormton.backend.sodamsodam.domain.user.domain.UserRole;
import goormton.backend.sodamsodam.domain.user.domain.repository.UserRepository;
import goormton.backend.sodamsodam.global.error.DefaultExeption;
import goormton.backend.sodamsodam.global.payload.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 구글로 부터 받은 userRequest에 대한 후처리 함수
    // 함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어짐.
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("userRequest.clientRegistration : {}",userRequest.getClientRegistration());
        log.info("userRequest.access_Token : {}",userRequest.getAccessToken());
        log.info("userRequest.access_Token.Token_value  : {}",userRequest.getAccessToken().getTokenValue());

        OAuth2User oAuth2User = super.loadUser(userRequest);
        // 위 request 정보를 토대로 강제 회원가입 진행
        // 구글로그인버튼 -> 구글로그인 창 -> 로그인 완료 -> 사용자 정보 code를 리턴 받음(userRequest에 담김) -> code를 토대로 Access Token 요청
        //
        // userRequest 정보를 토대로
        // -> 회원 프로필을 받아야함 (loadUser 함수) -> 구글로부터 회원 프로필을 받아줌
        log.info("userRequest.getAttributes : {}", super.loadUser(userRequest).getAttributes());

        // kakao
        String provider = userRequest.getClientRegistration().getRegistrationId();
        String provider_id = oAuth2User.getAttribute("id"); // provid

        String email = oAuth2User.getAttribute("email");
        String username = provider + provider_id; // google_sub
        String password = passwordEncoder.encode("getInthere");
        UserRole role = UserRole.USER;

        User userEntity = userRepository.findByEmail(email).orElseThrow(() -> new DefaultExeption(ErrorCode.USER_NOT_FOUND_ERROR));

        if (userEntity == null) {
            userEntity = User.builder()
                    .username(username)
                    .email(email)
                    .password(password)
                    .provider(provider)
                    .provider_id(provider_id)
                    .role(role)
                    .build();
            userRepository.save(userEntity);
        } else {
            log.info("최초 로그인이 아닙니다.");
        }
        return new UserPrincipal(userEntity, oAuth2User.getAttributes());
    }

}
