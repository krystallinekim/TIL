package com.beyond.university.department.model.mapper;

import com.beyond.university.department.model.vo.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface DepartmentMapper {

    List<Department> selectAll(RowBounds rowBounds);

    int selectDepartmentCount();
}
