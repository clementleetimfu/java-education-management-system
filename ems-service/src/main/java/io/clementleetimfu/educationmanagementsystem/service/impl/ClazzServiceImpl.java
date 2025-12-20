package io.clementleetimfu.educationmanagementsystem.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.clementleetimfu.educationmanagementsystem.exception.BusinessException;
import io.clementleetimfu.educationmanagementsystem.exception.ErrorCodeEnum;
import io.clementleetimfu.educationmanagementsystem.mapper.ClazzMapper;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.clazz.ClazzFindAllVO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.clazz.ClazzFindByIdVO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.clazz.ClazzSearchVO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.result.PageResult;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.clazz.*;
import io.clementleetimfu.educationmanagementsystem.pojo.entity.Clazz;
import io.clementleetimfu.educationmanagementsystem.service.ClazzService;
import io.clementleetimfu.educationmanagementsystem.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class ClazzServiceImpl implements ClazzService {

    @Autowired
    private ClazzMapper clazzMapper;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private StudentService studentService;

    @Override
    public PageResult<ClazzSearchVO> searchClazz(ClazzSearchDTO clazzSearchDTO) {

        PageHelper.startPage(clazzSearchDTO.getPage(), clazzSearchDTO.getPageSize());

        List<ClazzSearchVO> clazzSearchVOList = clazzMapper.searchClazz(clazzSearchDTO);

        if (clazzSearchVOList.isEmpty()) {
            log.warn("Clazz list is empty");
            throw new BusinessException(ErrorCodeEnum.CLAZZ_NOT_FOUND);
        }

        Page<ClazzSearchVO> page = (Page<ClazzSearchVO>) clazzSearchVOList;
        return new PageResult<>(page.getTotal(), page.getResult());
    }

    @Transactional(rollbackFor = Exception.class)
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ClazzFindByIdVO findClazzById(Integer id) {

        ClazzFindByIdVO clazzFindByIdVO = clazzMapper.selectClazzById(id);
        if (clazzFindByIdVO == null) {
            log.warn("Clazz with id:{} not found", id);
            throw new BusinessException(ErrorCodeEnum.CLAZZ_NOT_FOUND);
        }

        return clazzFindByIdVO;
    }

    @Transactional(rollbackFor = Exception.class)
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean deleteClazzById(Integer id) {

        if (studentService.isStudentExistsInClazz(id)) {
            log.warn("Clazz with id:{} still has students", id);
            throw new BusinessException(ErrorCodeEnum.CLAZZ_DELETE_NOT_ALLOWED);
        }

        Integer rowsAffected = clazzMapper.deleteClazzById(id);
        if (rowsAffected == 0) {
            log.warn("Failed to delete clazz with id:{}", id);
            throw new BusinessException(ErrorCodeEnum.CLAZZ_DELETE_FAILED);
        }

        return Boolean.TRUE;
    }

    @Override
    public List<ClazzFindAllVO> findAllClazz() {

        List<ClazzFindAllVO> clazzFindAllVOList = clazzMapper.selectAllClazz();
        if (clazzFindAllVOList.isEmpty()) {
            log.warn("Clazz list is empty");
            throw new BusinessException(ErrorCodeEnum.CLAZZ_NOT_FOUND);
        }

        return clazzFindAllVOList;
    }

}