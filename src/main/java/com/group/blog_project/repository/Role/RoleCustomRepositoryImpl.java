package com.group.blog_project.repository.Role;

import com.group.blog_project.model.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class RoleCustomRepositoryImpl implements RoleCustomRepository {
    private final EntityManager entityManager;

    @Override
    public Optional<Role> findFirstByName(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Role> criteriaQuery = criteriaBuilder.createQuery(Role.class);
        Root<Role> roleRoot = criteriaQuery.from(Role.class);

        Predicate namePredicate = criteriaBuilder.equal(roleRoot.get("name"), name);

        criteriaQuery.where(namePredicate);

        List<Role> roles = entityManager.createQuery(criteriaQuery).getResultList();

        return roles.isEmpty() ? Optional.empty() : Optional.ofNullable(roles.getFirst());
    }
}
