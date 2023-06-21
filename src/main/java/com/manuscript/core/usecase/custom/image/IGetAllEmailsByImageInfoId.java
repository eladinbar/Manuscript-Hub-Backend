package com.manuscript.core.usecase.custom.image;

import java.util.List;
import java.util.UUID;

public interface IGetAllEmailsByImageInfoId {
    List<String> getAllEmailsByImageInfoIdImpl(UUID imageInfoId, String userId);
}
