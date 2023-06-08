package com.nhanph.demo.specification;

import com.nhanph.demo.entity.SalesProduct;
import com.nhanph.demo.entity.SalesProduct_;
import com.nhanph.demo.payload.response.SalesProductResponse;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class SalesProductSpecification {
    private final EntityManager entityManager;

    public SalesProductSpecification(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<SalesProductResponse> getData(String orderBy, String orderType) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<SalesProductResponse> query = cb.createQuery(SalesProductResponse.class);
        Root<SalesProduct> root = query.from(SalesProduct.class);

        query.multiselect(
                root.get(SalesProduct_.storeName),
                root.get(SalesProduct_.productName),
                cb.sum(root.get(SalesProduct_.salesUnit)),
                cb.sum(root.get(SalesProduct_.salesRevenue)));

        //not limited product and store fields
        query.groupBy(root.get(SalesProduct_.storeName), root.get(SalesProduct_.productName));
        if (orderType.equalsIgnoreCase("asc")) {
            query.orderBy(cb.asc(root.get(orderBy)));
        } else {
            query.orderBy(cb.desc(root.get(orderBy)));
        }

        List<SalesProductResponse> result = entityManager.createQuery(query).getResultList();
        entityManager.close();

        return result;

    }
}
