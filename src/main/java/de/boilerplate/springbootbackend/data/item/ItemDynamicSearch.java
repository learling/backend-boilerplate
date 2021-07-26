package de.boilerplate.springbootbackend.data.item;

import de.boilerplate.springbootbackend.data.item.search.DateRange;
import de.boilerplate.springbootbackend.data.item.search.IdRange;
import de.boilerplate.springbootbackend.data.item.search.PriceRange;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemDynamicSearch implements Specification<ItemEntity> {

  private static final long serialVersionUID = 1L;

  private final Map<String, String[]> filters;
  private final List<Predicate> predicateList;

  public ItemDynamicSearch(Map<String, String[]> filters) {
    this.filters = filters;
    this.predicateList = new ArrayList<>();
  }

  @Override
  public Predicate toPredicate(Root<ItemEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
    filters.forEach((key, values) -> {
      switch (key) {
        case "id":
          filterId(root, builder, key, values);
          break;
        case "title":
        case "description":
          filterText(root, builder, key, values);
          break;
        case "price":
          filterPrice(root, builder, key, values);
          break;
        case "created":
        case "updated":
          filterDate(root, builder, key, values);
          break;
      }
    });
    Predicate[] predicates = predicateList.toArray(Predicate[]::new);
    return query.where(builder.and(predicates)).getRestriction();
  }

  private void filterId(Root<ItemEntity> root, CriteriaBuilder builder, String key, String[] values) {
    List<Predicate> idPredicates = new ArrayList<>();
    for (String value : values) {
      IdRange idRange = new IdRange(value);
      idPredicates.add(builder.between(root.get(key), idRange.from, idRange.to));
    }
    predicateList.add(builder.or(idPredicates.toArray(Predicate[]::new)));
  }

  private void filterText(Root<ItemEntity> root, CriteriaBuilder builder, String key, String[] values) {
    List<Predicate> titlePredicates = new ArrayList<>();
    for (String value : values) {
      titlePredicates.add(builder.like(root.get(key), "%" + value + "%"));
    }
    predicateList.add(builder.or(titlePredicates.toArray(Predicate[]::new)));
  }

  private void filterPrice(Root<ItemEntity> root, CriteriaBuilder builder, String key, String[] values) {
    List<Predicate> pricePredicates = new ArrayList<>();
    for (String value : values) {
      PriceRange priceRange = new PriceRange(value);
      pricePredicates.add(builder.between(root.get(key), priceRange.from, priceRange.to));
    }
    predicateList.add(builder.or(pricePredicates.toArray(Predicate[]::new)));
  }

  private void filterDate(Root<ItemEntity> root, CriteriaBuilder builder, String key, String[] values) {
    List<Predicate> datePredicates = new ArrayList<>();
    for (String value : values) {
      DateRange dateRange = new DateRange(value);
      datePredicates.add(builder.between(root.get(key), dateRange.from, dateRange.to));
    }
    predicateList.add(builder.or(datePredicates.toArray(Predicate[]::new)));
  }
}
