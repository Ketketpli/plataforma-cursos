CREATE SEQUENCE progress_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE progress (
    id BIGINT PRIMARY KEY DEFAULT nextval('progress_seq'),
    enrollment_id BIGINT NOT NULL,
    lesson_id BIGINT NOT NULL,
    CONSTRAINT fk_progress_enrollment FOREIGN KEY (enrollment_id) REFERENCES enrollments(id),
    CONSTRAINT fk_progress_lesson FOREIGN KEY (lesson_id) REFERENCES lessons(id),
    completed BOOLEAN NOT NULL DEFAULT false,
    CONSTRAINT uq_enrollment_lesson UNIQUE (enrollment_id, lesson_id)
);