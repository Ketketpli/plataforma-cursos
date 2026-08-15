CREATE SEQUENCE enrollment_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE enrollments (
    id BIGINT PRIMARY KEY DEFAULT nextval('enrollment_seq'),
    student_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    enrolled_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_enrollment_student FOREIGN KEY (student_id) REFERENCES users(id),
    CONSTRAINT fk_enrollment_course FOREIGN KEY (course_id) REFERENCES courses(id),
    CONSTRAINT uq_student_course UNIQUE (student_id, course_id)
);