package org.backend.controller;

import org.backend.entity.Review;
import org.backend.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sargweb/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    // GET all reviews (admin)
    @GetMapping("/all")
    public ResponseEntity<List<Review>> getAllReviews() {
        return ResponseEntity.ok(reviewRepository.findAll());
    }

    // GET only visible reviews (for website frontend)
    @GetMapping("/visible")
    public ResponseEntity<List<Review>> getVisibleReviews() {
        return ResponseEntity.ok(reviewRepository.findByIsVisibleTrue());
    }

    // POST create review
    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewRepository.save(review));
    }

    // POST create review (alias for /add)
    @PostMapping("/add")
    public ResponseEntity<Review> addReview(@RequestBody Review review) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewRepository.save(review));
    }

    // PUT update review
    @PutMapping("/{id}")
    public ResponseEntity<?> updateReview(@PathVariable Long id, @RequestBody Review updatedReview) {
        return reviewRepository.findById(id).map(review -> {
            review.setClientName(updatedReview.getClientName());
            review.setClientDesignation(updatedReview.getClientDesignation());
            review.setClientCompany(updatedReview.getClientCompany());
            review.setClientWebsite(updatedReview.getClientWebsite());
            review.setClientLogo(updatedReview.getClientLogo());
            review.setFeedback(updatedReview.getFeedback());
            review.setRating(updatedReview.getRating());
            review.setVisible(updatedReview.isVisible());
            return ResponseEntity.ok(reviewRepository.save(review));
        }).orElse(ResponseEntity.notFound().build());
    }

    // PATCH toggle visibility
    @PatchMapping("/{id}/toggle-visibility")
    public ResponseEntity<?> toggleVisibility(@PathVariable Long id) {
        return reviewRepository.findById(id).map(review -> {
            review.setVisible(!review.isVisible());
            return ResponseEntity.ok(reviewRepository.save(review));
        }).orElse(ResponseEntity.notFound().build());
    }

    // PUT toggle visibility (alias for /toggle/{id})
    @PutMapping("/toggle/{id}")
    public ResponseEntity<?> toggleVisibilityAlt(@PathVariable Long id) {
        return reviewRepository.findById(id).map(review -> {
            review.setVisible(!review.isVisible());
            return ResponseEntity.ok(reviewRepository.save(review));
        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE review
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Long id) {
        if (!reviewRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        reviewRepository.deleteById(id);
        return ResponseEntity.ok("Review deleted successfully");
    }
}
