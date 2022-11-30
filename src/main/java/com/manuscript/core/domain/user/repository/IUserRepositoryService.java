package com.manuscript.core.domain.user.repository;


import com.manuscript.core.domain.common.repository.IBaseRepositoryService;
import com.manuscript.core.domain.user.models.UserModel;

import java.util.List;

public interface IUserRepositoryService extends IBaseRepositoryService<UserModel> {
    List<UserModel> getAllActiveVideosInfo();

}
