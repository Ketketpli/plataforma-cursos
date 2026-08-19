CREATE SEQUENCE certificate_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE certificates (
    id BIGINT PRIMARY KEY DEFAULT nextval('certificate_seq'),
    enrollment_id BIGINT NOT NULL,
    issued_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_certificate_enrollment FOREIGN KEY (enrollment_id) REFERENCES enrollments(id)
);