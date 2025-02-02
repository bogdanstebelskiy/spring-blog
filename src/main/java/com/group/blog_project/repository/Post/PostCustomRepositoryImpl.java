package com.group.blog_project.repository.Post;

import com.group.blog_project.model.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PostCustomRepositoryImpl implements PostCustomRepository{
    private final EntityManager entityManager;

    @Override
    public List<Post> findByKeyword(String keyword) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Post> criteriaQuery = criteriaBuilder.createQuery(Post.class);
        Root<Post> postRoot = criteriaQuery.from(Post.class);

        Predicate titlePredicate = criteriaBuilder.like(
                criteriaBuilder.lower(postRoot.get("title")),
                "%" + keyword.toLowerCase() + "%");

        Predicate contentPredicate = criteriaBuilder.like(
                criteriaBuilder.lower(postRoot.get("content")),
                "%" + keyword.toLowerCase() + "%");

        Predicate authorPredicate = criteriaBuilder.like(
                criteriaBuilder.lower(postRoot.get("author").get("username")),
                "%" + keyword.toLowerCase() + "%");

        Predicate finalPredicate = criteriaBuilder.or(titlePredicate, contentPredicate, authorPredicate);

        criteriaQuery.select(postRoot).where(finalPredicate);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}

