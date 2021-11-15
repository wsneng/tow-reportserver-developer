package com.acraftsman.h2ocloud.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.acraftsman.h2ocloud.service.FileUploadService;
import com.acraftsman.h2ocloud.utils.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
@CacheConfig(cacheNames = "report")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class FileUploadServiceImpl implements FileUploadService {

    private long maxSize = 100;
    @Override
    public String upload(MultipartFile multipartFile) {
        FileUtil.checkSize(maxSize, multipartFile.getSize());
        File file = FileUtil.upload(multipartFile);
        String fileName = file.getName();
        if(ObjectUtil.isNull(file)){
            return null;
        }
        return fileName;
    }
}
