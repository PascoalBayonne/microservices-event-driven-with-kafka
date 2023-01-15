package pt.bayonne.sensei.customer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import pt.bayonne.sensei.customer.domain.Customer;
import pt.bayonne.sensei.customer.domain.EmailAddress;
import pt.bayonne.sensei.customer.messaging.CustomerMessaging;
import pt.bayonne.sensei.customer.repository.CustomerRepository;
import reactor.core.publisher.Sinks;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
   private final CustomerRepository customerRepository;

   private final Sinks.Many<Customer> customerProducer;

    @Override
    public Customer create(final Customer customer) {
        Customer customerCreated = customerRepository.save(customer);
        customerProducer.tryEmitNext(customerCreated);
        return customerCreated;
    }

    @Override
    public void changeEmail(final Long customerId, final EmailAddress emailAddress) {
        Customer customer = this.customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Couldn't find a customer by id: %s", customerId)));

        customer.changeEmail(emailAddress);
        this.customerRepository.save(customer);
    }


}
