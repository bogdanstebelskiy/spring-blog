package com.group.blog_project.repository.Category;

import com.group.blog_project.model.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CategoryCustomRepositoryImpl implements CategoryCustomRepository {
    private final EntityManager entityManager;

    @Override
    public Optional<Category> findByName(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Category> criteriaQuery = criteriaBuilder.createQuery(Category.class);
        Root<Category> categoryRoot = criteriaQuery.from(Category.class);

        criteriaQuery.select(categoryRoot)
                .where(criteriaBuilder.equal(categoryRoot.get("name"), name));

        List<Category> categories = entityManager.createQuery(criteriaQuery).getResultList();

        // Return an Optional with the first category, or Optional.empty() if not found
        return categories.isEmpty() ? Optional.empty() : Optional.ofNullable(categories.getFirst());
    }

}

