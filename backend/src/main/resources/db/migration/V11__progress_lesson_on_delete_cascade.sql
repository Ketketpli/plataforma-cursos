ALTER TABLE progress
    DROP CONSTRAINT fk_progress_lesson;

ALTER TABLE progress
    ADD CONSTRAINT fk_progress_lesson
    FOREIGN KEY (lesson_id) REFERENCES lessons(id)
    ON DELETE CASCADE;