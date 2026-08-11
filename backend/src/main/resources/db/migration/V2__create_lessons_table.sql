CREATE SEQUENCE lesson_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE lessons (
    id BIGINT PRIMARY KEY DEFAULT nextval('lesson_seq'),
    course_id BIGINT NOT NULL,
    CONSTRAINT fk_course_course FOREIGN KEY (course_id) REFERENCES courses(id),

    title VARCHAR(100) NOT NULL,
    description VARCHAR(500) NOT NULL,
    content VARCHAR(500) NOT NULL,
    lesson_order INTEGER NOT NULL
);