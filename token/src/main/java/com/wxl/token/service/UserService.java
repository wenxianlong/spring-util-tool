package com.wxl.token.service;

import com.wxl.token.boot.exception.BizException;
import com.wxl.token.boot.jwt.JwtTokenBuilder;
import com.wxl.token.boot.jwt.TokenManager;
import com.wxl.token.entity.LoginInfo;
import com.wxl.token.entity.User;
import com.wxl.token.enums.CommonConstant;
import com.wxl.token.mapper.UserMapper;
import com.wxl.token.utils.PasswordUtil4CcsFin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author wenxianlong
 * @date 2021/12/27 7:50 下午
 */
@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private JwtTokenBuilder jwtTokenBuilder;

    public LoginInfo login(String loginName, String password) {
        User user = userMapper.getByLoginName(loginName);
        if (user == null) {
            throw new BizException("登陆用户不存在！");
        }
        if (Objects.equals(user.getStatus(), CommonConstant.USER_FREEZE)) {
            throw new BizException("用户已经冻结！");
        }

        int errorCount = user.getErrorCount() == null ? 0 : user.getErrorCount();
        LocalDateTime lastErrorDate = user.getLastErrorDate();
        LocalDateTime time = LocalDateTime.now();
        boolean sameDay = lastErrorDate != null && lastErrorDate.toLocalDate().equals(time.toLocalDate());
        if (sameDay && errorCount >= 5) {
            throw new BizException("今天密码已输错5次，请重置账号密码");
        }

        if (!PasswordUtil4CcsFin.validatePassword(password, user.getPassword())) {
            errorCount = sameDay ? errorCount + 1 : 1;
            userMapper.updateErrorCount(user.getId(), errorCount, time);
            throw new BizException("账号或密码错误");
        }

        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUser(user);

        String accessToken = jwtTokenBuilder.buildToken(user.getId() + "");
        tokenManager.createRelationship(CommonConstant.USER_TYPE_PERSONAL, user.getId() + "", accessToken);
        loginInfo.setToken(accessToken);
        return loginInfo;
    }
}
