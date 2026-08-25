CREATE SEQUENCE review_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE reviews (
    id BIGINT PRIMARY KEY DEFAULT nextval('review_seq'),
    enrollment_id BIGINT NOT NULL,
    rating INTEGER NOT NULL,
    comment VARCHAR(1000) NOT NULL,
    CONSTRAINT fk_review_enrollment FOREIGN KEY (enrollment_id) REFERENCES enrollments(id),
    CONSTRAINT chk_review_rating CHECK (rating BETWEEN 1 AND 5)
);