package kr.co.upcoding.auth.config;

import kr.co.upcoding.mapper.UserMapper;
import kr.co.upcoding.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UserAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UserMapper userMapper; // 빨간줄은 인텔리제이의 오류로 무시가능.

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        String email = auth.getName();
        String password = (String) auth.getCredentials();

        UserVO vo = new UserVO();
        vo.setEmail(email);
        vo.setPassword(password);

        UserVO userVO = userMapper.userSelect(vo);
        if(userVO == null){
            throw new BadCredentialsException("Login Error !!");
        }
        userVO.setPassword(null);

        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new UsernamePasswordAuthenticationToken(userVO, null, authorities);
    }

    @Override
    public boolean supports(Class authentication){
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
