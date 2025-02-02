package com.group.blog_project.repository.Tag;

import com.group.blog_project.model.Tag;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class TagCustomRepositoryImpl implements TagCustomRepository {
    private final EntityManager entityManager;

    @Override
    public Optional<Tag> findFirstByName(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> tagRoot = criteriaQuery.from(Tag.class);

        Predicate namePredicate = criteriaBuilder.equal(tagRoot.get("name"), name);

        criteriaQuery.where(namePredicate);

        List<Tag> tags = entityManager.createQuery(criteriaQuery).getResultList();

        return tags.isEmpty() ? Optional.empty() : Optional.ofNullable(tags.getFirst());
    }
}

