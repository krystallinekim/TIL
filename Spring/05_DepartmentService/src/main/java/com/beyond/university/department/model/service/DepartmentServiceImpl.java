package com.beyond.university.department.model.service;

import com.beyond.university.department.model.mapper.DepartmentMapper;
import com.beyond.university.department.model.vo.Department;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentMapper departmentMapper;

    @Override
    public int getDepartmentsCount() {
        return departmentMapper.selectDepartmentCount();
    }

    @Override
    public List<Department> getDepartments(int page, int numOfRows) {
    /*
        Mybatis에서 페이징 처리
          - Mybatis에서는 페이징 처리를 위해 RowBounds라는 클래스를 제공한다.
          - RowBounds의 객체를 생성할 때 offset과 limit 값을 전달해서 페이징 처리를 구현한다.
            (offset 만큼 건너뛰고 limit 만큼 가져온다.)
          - 생성된 RowBounds 객체를 매퍼 인터페이스의 추상 메소드로 전달하면 된다.
          - 예시
            offset = 0, limit = 10
              - 0개를 건너뛰고 10개를 가져온다. (1 ~ 10)

            offset = 10, limit = 10
              - 10개를 건너뛰고 10개를 가져온다. (11 ~ 20)

            offset = 20, limit = 10
              - 20개를 건너뛰고 10개를 가져온다. (21 ~ 30)

    */

        // RowBounds(offset, limit) - offset만큼 건너뛰고, limit만큼 가져옴
        int offset = (page - 1) * numOfRows;

        RowBounds rowBounds = new RowBounds(offset, numOfRows);


        return departmentMapper.selectAll(rowBounds);
    }


}
