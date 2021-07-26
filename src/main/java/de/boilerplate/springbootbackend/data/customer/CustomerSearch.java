package de.boilerplate.springbootbackend.data.customer;

import de.boilerplate.springbootbackend.global.Operator;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class CustomerSearch implements Specification<CustomerEntity> {

  private static final long serialVersionUID = 1L;

  private String attribute;
  private Object value;
  Operator operator;

  public CustomerSearch(String attribute, Object value, Operator operator) {
    this.attribute = attribute;
    this.value = value;
    this.operator = operator;
  }

  @Override
  public Predicate toPredicate(Root<CustomerEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
    if (operator == Operator.LIKE) {
      return query.where(builder.like(root.<String>get(this.attribute), "%" + this.value + "%")).getRestriction();
    } else {
      return query.where(builder.equal(root.<String>get(this.attribute), this.value)).getRestriction();
    }
  }
}