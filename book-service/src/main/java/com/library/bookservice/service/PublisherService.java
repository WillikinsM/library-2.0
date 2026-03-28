package com.library.bookservice.service;

import com.library.bookservice.dto.PublisherDetails;
import com.library.bookservice.dto.PublisherRequest;
import com.library.bookservice.dto.PublisherResponse;
import com.library.bookservice.dto.PublisherUpdate;
import com.library.bookservice.exceptions.NotFoundException;
import com.library.bookservice.model.Publisher;
import com.library.bookservice.repository.PublisherRepository;
import com.mongodb.MongoWriteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = {"publishers"})
public class PublisherService {

    private static final Logger log = LoggerFactory.getLogger(PublisherService.class);

    private final PublisherRepository publisherRepository;
    private final int DUPLICATE_ERROR_CODE = 11000;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @CacheEvict(cacheNames = "publishers", allEntries = true)
    public PublisherDetails save(PublisherRequest publisherRequest) {
        Publisher publisher = new Publisher(publisherRequest);
        PublisherDetails publisherDetails = null;

        try {
            publisherDetails = new PublisherDetails(publisherRepository.save(publisher));
            log.info("Publisher saved: {} - {}", publisherDetails._id(), publisherDetails.name());
        } catch (MongoWriteException exception) {
            if (exception.getError().getCode() == DUPLICATE_ERROR_CODE) {
                throw new DuplicateKeyException("Duplicate Field: " + exception.getMessage(), exception);
            }
        }
        return publisherDetails;
    }

    @Cacheable(keyGenerator = "customKeyGenerator")
    public List<PublisherResponse> findAll() {
        List<Publisher> publishers = publisherRepository.findAll();
        log.info("Publisher's list returned");
        return publishers.stream().map(PublisherResponse::new).toList();
    }

    @CacheEvict(cacheNames = "publishers", allEntries = true)
    public void delete(String id) {
        findById(id);
        publisherRepository.deleteById(id);
        log.warn("Genre deleted, id: " + id);
    }

    @Cacheable(keyGenerator = "customKeyGenerator")
    public PublisherDetails findById(String id) {
        Publisher publisher = publisherRepository.findById(id).orElseThrow(() -> new NotFoundException("Object not Found: " + id + " , type: " + Publisher.class.getName()));
        log.info("Publisher returned: {} - {}", publisher.get_id(), publisher.getName());
        return new PublisherDetails(publisher);
    }

    @CacheEvict(cacheNames = "publishers", allEntries = true)
    public PublisherDetails update(PublisherUpdate publisherUpdate) {
        Publisher publisher = publisherRepository.findById(publisherUpdate._id()).orElseThrow(() -> new NotFoundException("Object not Found: " + publisherUpdate._id() + " , type: " + PublisherUpdate.class.getName()));
        publisher.update(publisherUpdate);
        try {
            publisherRepository.save(publisher);
            log.info("Publisher updated, id: " + publisher.get_id());
        } catch (MongoWriteException exception) {
            if (exception.getError().getCode() == DUPLICATE_ERROR_CODE) {
                throw new DuplicateKeyException("Duplicate Field: " + exception.getMessage(), exception);
            }
        }
        return new PublisherDetails(publisher);
    }
}
