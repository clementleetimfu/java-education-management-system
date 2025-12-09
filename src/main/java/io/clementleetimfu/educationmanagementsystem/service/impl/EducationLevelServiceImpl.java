package io.clementleetimfu.educationmanagementsystem.service.impl;

import io.clementleetimfu.educationmanagementsystem.exception.BusinessException;
import io.clementleetimfu.educationmanagementsystem.exception.ErrorCodeEnum;
import io.clementleetimfu.educationmanagementsystem.mapper.EducationLevelMapper;
import io.clementleetimfu.educationmanagementsystem.pojo.educationLevel.EduLevelFindAllDTO;
import io.clementleetimfu.educationmanagementsystem.service.EducationLevelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EducationLevelServiceImpl implements EducationLevelService {

    @Autowired
    private EducationLevelMapper educationLevelMapper;

    @Override
    public List<EduLevelFindAllDTO> findAllEduLevel() {
        List<EduLevelFindAllDTO> eduLevelFindAllDTOList = educationLevelMapper.selectAllEduLevel();
        if (eduLevelFindAllDTOList.isEmpty()) {
            log.warn("Education level list is empty");
            throw new BusinessException(ErrorCodeEnum.EDUCATION_LEVEL_NOT_FOUND);
        }
        return eduLevelFindAllDTOList;
    }

}
