package com.group.blog_project.repository.User;

import com.group.blog_project.model.Tag;
import com.group.blog_project.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserCustomRepositoryImpl implements UserCustomRepository{
    private final EntityManager entityManager;

    @Override
    public Optional<User> findByUsername(String username) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);

        Predicate namePredicate = criteriaBuilder.equal(userRoot.get("username"), username);

        criteriaQuery.where(namePredicate);

        return Optional.ofNullable(entityManager.createQuery(criteriaQuery).getSingleResult());
    }
}
