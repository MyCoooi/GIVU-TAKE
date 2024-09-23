package com.accepted.givutake.global.service;

import com.accepted.givutake.global.entity.UserViewLogs;
import com.accepted.givutake.global.enumType.ExceptionEnum;
import com.accepted.givutake.global.exception.ApiException;
import com.accepted.givutake.global.model.CreateLogDto;
import com.accepted.givutake.global.repository.UserViewLogRepository;
import com.accepted.givutake.user.common.entity.Users;
import com.accepted.givutake.user.common.repository.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserViewLogService {

    private final UserViewLogRepository userViewLogRepository;
    private final UsersRepository usersRepository;

    public void createLog(String email, CreateLogDto request){
        Users user = usersRepository.findByEmail(email).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_USER_WITH_EMAIL_EXCEPTION));
        UserViewLogs log = UserViewLogs.builder()
                .users(user)
                .contentType(request.getContentType())
                .actEnum(request.getAct())
                .contentIdx(request.getContentIdx())
                .build();
        userViewLogRepository.save(log);
    }
}
