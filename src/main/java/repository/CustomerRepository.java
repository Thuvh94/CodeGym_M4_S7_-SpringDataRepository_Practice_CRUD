package repository;

import model.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

public interface CustomerRepository extends PagingAndSortingRepository<Customer,Long> {
}
