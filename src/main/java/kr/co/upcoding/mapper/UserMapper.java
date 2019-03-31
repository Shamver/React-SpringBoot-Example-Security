package kr.co.upcoding.mapper;

import kr.co.upcoding.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    UserVO userSelect(UserVO vo);
}
