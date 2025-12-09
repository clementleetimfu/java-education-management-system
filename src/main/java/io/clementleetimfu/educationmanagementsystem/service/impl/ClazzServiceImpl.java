package io.clementleetimfu.educationmanagementsystem.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.clementleetimfu.educationmanagementsystem.exception.BusinessException;
import io.clementleetimfu.educationmanagementsystem.exception.ErrorCodeEnum;
import io.clementleetimfu.educationmanagementsystem.mapper.ClazzMapper;
import io.clementleetimfu.educationmanagementsystem.pojo.PageResult;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.clazz.*;
import io.clementleetimfu.educationmanagementsystem.pojo.entity.Clazz;
import io.clementleetimfu.educationmanagementsystem.service.ClazzService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class ClazzServiceImpl implements ClazzService {

    @Autowired
    private ClazzMapper clazzMapper;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PageResult<ClazzSearchResponseDTO> searchClazz(ClazzSearchRequestDTO clazzSearchRequestDTO) {

        PageHelper.startPage(clazzSearchRequestDTO.getPage(), clazzSearchRequestDTO.getPageSize());

        List<ClazzSearchResponseDTO> clazzSearchResponseDTOList = clazzMapper.searchClazz(clazzSearchRequestDTO);

        if (clazzSearchResponseDTOList.isEmpty()) {
            log.warn("Clazz list is empty");
            throw new BusinessException(ErrorCodeEnum.CLAZZ_NOT_FOUND);
        }

        Page<ClazzSearchResponseDTO> page = (Page<ClazzSearchResponseDTO>) clazzSearchResponseDTOList;
        return new PageResult<>(page.getTotal(), page.getResult());
    }

    @Override
    public Boolean addClazz(ClazzAddDTO clazzAddDTO) {
        Clazz clazz = modelMapper.map(clazzAddDTO, Clazz.class);
        clazz.setCreateTime(LocalDateTime.now());
        clazz.setUpdateTime(LocalDateTime.now());
        clazz.setIsDeleted(Boolean.FALSE);
        Integer rowsAffected = clazzMapper.insertClazz(clazz);
        if (rowsAffected == 0) {
            log.warn("Failed to add clazz:{}", clazz);
            throw new BusinessException(ErrorCodeEnum.CLAZZ_ADD_FAILED);
        }
        return Boolean.TRUE;
    }

    @Override
    public ClazzFindByIdDTO findClazzById(Integer id) {
        ClazzFindByIdDTO clazzFindByIdDTO = clazzMapper.selectClazzById(id);
        if (clazzFindByIdDTO == null) {
            log.warn("Clazz with id:{} not found", id);
            throw new BusinessException(ErrorCodeEnum.CLAZZ_NOT_FOUND);
        }
        return clazzFindByIdDTO;
    }

    @Override
    public Boolean updateClazzName(ClazzUpdateDTO clazzUpdateDTO) {
        Clazz clazz = modelMapper.map(clazzUpdateDTO, Clazz.class);
        clazz.setUpdateTime(LocalDateTime.now());
        Integer rowsAffected = clazzMapper.updateClazz(clazz);
        if (rowsAffected == 0) {
            log.warn("Failed to update clazz:{}", clazz);
            throw new BusinessException(ErrorCodeEnum.CLAZZ_UPDATE_FAILED);
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean deleteClazzById(Integer id) {
        Integer rowsAffected = clazzMapper.deleteClazzById(id);
        if (rowsAffected == 0) {
            log.warn("Failed to delete clazz with id:{}", id);
            throw new BusinessException(ErrorCodeEnum.CLAZZ_DELETE_FAILED);
        }
        return Boolean.TRUE;
    }

    @Override
    public List<ClazzFindAllDTO> findAllClazz() {
        List<ClazzFindAllDTO> clazzFindAllDTOList = clazzMapper.selectAllClazz();
        if (clazzFindAllDTOList.isEmpty()) {
            log.warn("Clazz list is empty");
            throw new BusinessException(ErrorCodeEnum.CLAZZ_NOT_FOUND);
        }
        return clazzFindAllDTOList;
    }

}