package com.umc.Carrot;

import com.umc.Carrot.model.User;
import com.umc.Carrot.model.UserUpdateReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepository {
    //리포지토리 -> 데이터베이스
    private final JdbcTemplate template;

    //회원 가입할 때 회원 정보 저장
    public User save(User user) {
        String sql = "insert into User (email, password, phoneNum, profileImage_url, nickname) values (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        //sql 쿼리 실행
        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getPhoneNum());
            ps.setString(4, user.getProfileImage_url());
            ps.setString(5, user.getNickname());
            return ps;
        }, keyHolder);
        return user;
    }

    public boolean isNicknameDuplicate(String nickname) {
        String sql = "SELECT COUNT(*) FROM User WHERE nickname = ?";
        int count = template.queryForObject(sql, Integer.class, nickname);
        return count > 0;
    }

    //입력받은 id로 회원 정보 찾기
    public Optional<User> findById(Long id) {
        String sql = "select * from User where user_id=?";
        try {
            User user = template.queryForObject(sql, userRowMapper(), id);
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    //모든 회원의 정보 찾기
    public List<User> findAll() {
        String sql = "select * from User";
        return template.query(sql, userRowMapper());
    }

    //입력된 정보에 대해 회원 정보 수정(현재 nickname만 수정 가능)
    public void update(Long userId, UserUpdateReqDto updateDto) {
        String sql = "update User set nickname=? where user_id=?";
        template.update(sql, updateDto.getNickname(), userId);
    }

    //입력된 id의 회원 정보 삭제
    public void delete(Long userId) {
        String sql = "delete from User where user_id=?";
        template.update(sql, userId);
    }

    //SQL 쿼리 결과를 Java 객체로 매핑
    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            User user = new User();
            user.setUser_id(rs.getLong("user_id"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setPhoneNum(rs.getString("phoneNum"));
            user.setProfileImage_url(rs.getString("profileImage_url"));
            user.setMannerTemperature(rs.getDouble("mannerTemperature"));
            user.setNickname(rs.getString("nickname"));
            user.setCreatedAt(rs.getTimestamp("createdAt"));
            user.setStatus(rs.getString("status"));
            user.setUpdatedAt(rs.getTimestamp("updatedAt"));
            return user;
        };
    }
}
