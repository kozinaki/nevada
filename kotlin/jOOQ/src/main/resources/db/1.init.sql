CREATE TABLE language (
  id              INTEGER     NOT NULL PRIMARY KEY,
  cd              CHAR(2)       NOT NULL,
  description     TEXT
);

CREATE TABLE author (
  id              INTEGER     NOT NULL PRIMARY KEY,
  first_name      TEXT,
  last_name       TEXT  NOT NULL,
  date_of_birth   DATE,
  year_of_birth   INTEGER,
  distinguished   INTEGER
);

CREATE TABLE book (
  id              INTEGER     NOT NULL PRIMARY KEY,
  author_id       INTEGER     NOT NULL,
  title           TEXT NOT NULL,
  published_in    INTEGER     NOT NULL,
  language_id     INTEGER     NOT NULL,

  CONSTRAINT fk_book_author     FOREIGN KEY (author_id)   REFERENCES author(id),
  CONSTRAINT fk_book_language   FOREIGN KEY (language_id) REFERENCES language(id)
);

CREATE TABLE book_store (
  name            TEXT NOT NULL UNIQUE
);

CREATE TABLE book_to_book_store (
  name            TEXT NOT NULL,
  book_id         INTEGER       NOT NULL,
  stock           INTEGER,

  PRIMARY KEY(name, book_id),
  CONSTRAINT fk_b2bs_book_store FOREIGN KEY (name)        REFERENCES book_store (name) ON DELETE CASCADE,
  CONSTRAINT fk_b2bs_book       FOREIGN KEY (book_id)     REFERENCES book (id)         ON DELETE CASCADE
);