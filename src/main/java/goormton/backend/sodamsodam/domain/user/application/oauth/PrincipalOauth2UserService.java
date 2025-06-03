package goormton.backend.sodamsodam.domain.user.application.oauth;

import goormton.backend.sodamsodam.domain.user.domain.User;
import goormton.backend.sodamsodam.domain.user.domain.UserPrincipal;
import goormton.backend.sodamsodam.domain.user.domain.UserRole;
import goormton.backend.sodamsodam.domain.user.domain.oauth.KakaoUserInfo;
import goormton.backend.sodamsodam.domain.user.domain.oauth.OAuth2UserInfo;
import goormton.backend.sodamsodam.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;

    // 구글로 부터 받은 userRequest에 대한 후처리 함수
    // 함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어짐.
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("userRequest.clientRegistration : {}",userRequest.getClientRegistration());
        log.info("userRequest.access_Token : {}",userRequest.getAccessToken());
        log.info("userRequest.access_Token.Token_value  : {}",userRequest.getAccessToken().getTokenValue());

        OAuth2User oAuth2User = super.loadUser(userRequest);
        OAuth2UserInfo oAuth2UserInfo = null;

        if (userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        } else {
            log.info("지원하지 않는 소셜입니다.");
        }

        Optional<User> userEntity = userRepository.findByProviderAndProviderId(oAuth2UserInfo.getProvider(), oAuth2UserInfo.getProviderId());

        User user;
        if (userEntity.isPresent()) {
            user = userEntity.get();
            user.toBuilder()
                    .email(oAuth2UserInfo.getEmail())
                    .name(oAuth2UserInfo.getName())
                    .build();
            userRepository.save(user);
        } else {
            user = User.builder()
                    .username(oAuth2UserInfo.getProvider() + "_" + oAuth2UserInfo.getProviderId())
                    .email(oAuth2UserInfo.getEmail())
                    .name(oAuth2UserInfo.getName())
                    .provider(oAuth2UserInfo.getProvider())
                    .providerId(oAuth2UserInfo.getProviderId())
                    .role(UserRole.USER)
                    .build();
            userRepository.save(user);
        }

        return new UserPrincipal(user, oAuth2User.getAttributes());
    }
}
