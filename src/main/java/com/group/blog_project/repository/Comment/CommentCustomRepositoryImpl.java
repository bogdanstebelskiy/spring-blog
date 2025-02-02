package com.group.blog_project.repository.Comment;

import com.group.blog_project.model.Comment;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommentCustomRepositoryImpl implements CommentCustomRepository {

    private final EntityManager entityManager;

    @Override
    public List<Comment> findByPostId(UUID postId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Comment> criteriaQuery = criteriaBuilder.createQuery(Comment.class);

        Root<Comment> commentRoot = criteriaQuery.from(Comment.class);

        Predicate postPredicate = criteriaBuilder.equal(commentRoot.get("post").get("id"), postId);

        criteriaQuery.select(commentRoot).where(postPredicate);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
