CREATE SEQUENCE favorite_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE favorites (
    id BIGINT PRIMARY KEY DEFAULT nextval('favorite_seq'),
    student_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    CONSTRAINT fk_favorite_student FOREIGN KEY (student_id) REFERENCES users(id),
    CONSTRAINT fk_favorite_course FOREIGN KEY (course_id) REFERENCES courses(id),
    CONSTRAINT uq_favorite_student_course UNIQUE (student_id, course_id)
);