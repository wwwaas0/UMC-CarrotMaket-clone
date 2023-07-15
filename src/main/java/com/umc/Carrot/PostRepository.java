package com.umc.Carrot;

import com.umc.Carrot.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PostRepository {
    private final JdbcTemplate template;

    //게시물 생성할 때 게시물 정보 저장
    public Post save(Post post) {
        String sql = "insert into post (user_id, category_id, title, contents, price, image_url) values (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        //sql 쿼리 실행
        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, post.getUser_id());
            ps.setLong(2, post.getCategory_id());
            ps.setString(3, post.getTitle());
            ps.setString(4, post.getContents());
            ps.setString(5, post.getPrice());
            ps.setString(6, post.getImage_url());
            return ps;
        }, keyHolder);
        return post;
    }

    //입력받은 단어로 게시물 검색
    public Optional<Post> findByWord(String searchWord) {
        String sql = "select * from post where title LIKE ? or contents LIKE ?";
        String titlePattern = "%" + searchWord + "%";
        String contentsPattern = "%" + searchWord + "%";
        try {
            Post post = template.queryForObject(sql, postRowMapper(), titlePattern, contentsPattern);
            return Optional.of(post);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    //제목 수정
    public void updateTitle(Long postId, PostTitleReqDto titleReqDto){
        String sql = "update post set title=? where post_id=?";
        template.update(sql, titleReqDto.getTitle(), postId);
    }

    //가격, 내용, 사진 정보 수정
    public void update(Long postId, PostUpdateReqDto updateDto) {
        String sql = "update post set contents=?, price=?, image_url=? where user_id=?";
        template.update(sql, updateDto.getContents(),updateDto.getPrice(), updateDto.getImage_url(), postId);
    }

    //입력받은 postId로 해당 게시물 정보 찾기
    public Optional<Post> findById(Long id) {
        String sql = "select * from post where post_id=?";
        try {
            Post post = template.queryForObject(sql, postRowMapper(), id);
            return Optional.of(post);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    //입력된 id의 게시물 삭제
    public void delete(Long postId) {
        String sql = "delete from post where post_id=?";
        template.update(sql, postId);
    }

    //SQL 쿼리 결과를 Java 객체로 매핑
    private RowMapper<Post> postRowMapper() {
        return (rs, rowNum) -> {
            Post post = new Post();
            post.setPost_id(rs.getLong("post_id"));
            post.setImage_url(rs.getString("image_url"));
            post.setTitle(rs.getString("title"));
            post.setContents(rs.getString("contents"));
            post.setPrice(rs.getString("price"));
            post.setReportedCount(rs.getLong("reportCount"));
            post.setUser_id(rs.getLong("user_id"));
            post.setCreatedAt(rs.getTimestamp("createdAt"));
            post.setStatus(rs.getString("status"));
            post.setUpdatedAt(rs.getTimestamp("updatedAt"));
            return post;
        };
    }
}