package com.study.board.repository;

import com.study.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {

    Page<Board> findByTitleContaining(String searchKeyword, Pageable pageable);
    //findBy 컬럼이름 Containing() : 컬럼에서 검색한 키워드가 포함된 것을 찾겠다는 의미. - 키워드가 포함된 모든 데이터 검색

//    Page<Board> findByUserContaining(String searchUser, Pageable pageable); // User 검색기능 구현 연습 - 안되네
    //java.lang.IllegalArgumentException: Parameter value [%kyw%] did not match expected type [com.study.board.entity.User (n/a)]
}
