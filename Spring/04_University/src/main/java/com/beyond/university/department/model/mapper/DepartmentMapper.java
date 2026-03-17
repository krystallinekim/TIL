package com.beyond.university.department.model.mapper;

import com.beyond.university.department.model.vo.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DepartmentMapper {

    // 원하는 쿼리의 ID와 동일한 추상 메소드를 작성, 반환형도 적어놓은 대로(쿼리에서 한 행을 객체 하나로 받으므로 List로)
    List<Department> selectAll();

    Department selectDepartmentByNo(@Param("departmentNo") String departmentNo);
}
