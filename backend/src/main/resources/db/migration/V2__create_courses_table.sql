CREATE SEQUENCE course_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE courses (
    id BIGINT PRIMARY KEY DEFAULT nextval('course_seq'),
    instructor_id BIGINT NOT NULL,
    CONSTRAINT fk_course_instructor FOREIGN KEY (instructor_id) REFERENCES users(id),

    name VARCHAR(100) NOT NULL,
    description VARCHAR(500) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    duration INTEGER NOT NULL
);

CREATE SEQUENCE category_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE categories (
    id BIGINT PRIMARY KEY DEFAULT nextval('category_seq'),
    name VARCHAR(100) NOT NULL
);

CREATE TABLE course_category (
    course_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    CONSTRAINT fk_course_category FOREIGN KEY (course_id) REFERENCES courses(id),
    CONSTRAINT fk_category_category FOREIGN KEY (category_id) REFERENCES categories(id),
    PRIMARY KEY (course_id, category_id)
);